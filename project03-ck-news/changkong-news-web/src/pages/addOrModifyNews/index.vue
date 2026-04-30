<template>
    <div class="page">
        <section class="hero-card">
            <div class="hero-main">
                <div class="hero-copy">
                    <p class="hero-tag">{{ editorVisible ? "CONTENT EDITOR" : "CONTENT STUDIO" }}</p>
                    <h1 class="hero-title">{{ editorVisible ? editorTitle : "文章管理台" }}</h1>
                    <p class="hero-subtitle">
                        {{ editorVisible ? editorSubtitle : "查看、检索并管理你发布的文章。" }}
                    </p>
                </div>

                <div v-if="!editorVisible" class="hero-stats">
                    <div class="stat-pill">
                        <span class="stat-label">当前页</span>
                        <strong>{{ query.pageNum }}</strong>
                    </div>
                    <div class="stat-pill">
                        <span class="stat-label">每页</span>
                        <strong>{{ query.pageSize }} 条</strong>
                    </div>
                    <div class="stat-pill">
                        <span class="stat-label">总文章数</span>
                        <strong>{{ pageInfo?.totalSize ?? 0 }}</strong>
                    </div>
                    <div class="stat-pill">
                        <span class="stat-label">总页数</span>
                        <strong>{{ pageInfo?.totalPage ?? 0 }}</strong>
                    </div>
                </div>
            </div>

            <div class="hero-actions">
                <button class="ghost-btn" @click="goback()">返回首页</button>
                <button v-if="editorVisible" class="primary-btn" type="button" @click="closeEditor()">返回列表</button>
                <button v-else class="primary-btn" type="button" @click="addHeadline()">写文章</button>
            </div>
        </section>

        <section v-if="!editorVisible" class="toolbar-card">
            <div class="search-panel">
                <label class="search-label" for="searchInput">搜索文章</label>
                <div class="search-box">
                    <input
                        id="searchInput"
                        v-model="searchKeyWords"
                        type="text"
                        placeholder="输入标题关键词"
                        @keyup.enter="search()"
                    >
                    <button class="primary-btn search-btn" @click="search()">搜索</button>
                </div>
            </div>
        </section>

        <section v-if="editorVisible" class="editor-card">
            <div class="editor-header">
                <div>
                    <p class="section-tag">{{ editorMode === "create" ? "WRITE HEADLINE" : "EDIT HEADLINE" }}</p>
                    <h2>{{ editorTitle }}</h2>
                    <p class="editor-subtitle">{{ editorSubtitle }}</p>
                </div>
                <button class="ghost-btn" type="button" @click="closeEditor()">关闭</button>
            </div>

            <div v-if="loadingEdit" class="editor-loading">
                正在加载文章
            </div>

            <form v-else class="editor-form" @submit.prevent="submitEditor()">
                <label class="field-block" for="headlineTitle">
                    <span>文章标题</span>
                    <input
                        id="headlineTitle"
                        v-model="articleForm.title"
                        type="text"
                        maxlength="50"
                        placeholder="输入文章标题"
                    >
                </label>

                <div class="form-row">
                    <label class="field-block" for="headlineType">
                        <span>文章分类</span>
                        <select id="headlineType" v-model.number="articleForm.type">
                            <option :value="0">请选择分类</option>
                            <option v-for="item in newsTypes" :key="item.tid" :value="item.tid">
                                {{ item.tname }}
                            </option>
                        </select>
                    </label>

                    <div class="field-counter">
                        <span>标题 {{ titleLength }}/50</span>
                        <span>正文 {{ articleLength }}/5000</span>
                    </div>
                </div>

                <label class="field-block" for="headlineArticle">
                    <span>正文内容</span>
                    <textarea
                        id="headlineArticle"
                        v-model="articleForm.article"
                        maxlength="5000"
                        rows="12"
                        placeholder="输入正文内容"
                    ></textarea>
                </label>

                <p v-if="formError" class="form-error">{{ formError }}</p>

                <div class="editor-actions">
                    <button class="ghost-btn" type="button" @click="resetEditorForm()">清空表单</button>
                    <button class="primary-btn" type="submit" :disabled="submitting">
                        {{ submitButtonText }}
                    </button>
                </div>
            </form>
        </section>

        <section v-if="!editorVisible" class="content-card">
            <div class="list-header">
                <div>
                    <p class="section-tag">MY HEADLINES</p>
                    <h2>文章列表</h2>
                </div>

                <label class="page-size-box" for="pageSize">
                    <span>每页显示</span>
                    <select
                        id="pageSize"
                        :value="query.pageSize"
                        @change="changePageSize(Number(($event.target as HTMLSelectElement).value))"
                    >
                        <option value="5">5 条</option>
                        <option value="10">10 条</option>
                        <option value="15">15 条</option>
                        <option value="20">20 条</option>
                    </select>
                </label>
            </div>

            <div v-if="list.length > 0" class="headline-list">
                <article class="headline-card" v-for="item in list" :key="item.hid">
                    <div class="headline-main">
                        <div class="headline-meta">
                            <span class="type-chip">分类 {{ item.type }}</span>
                            <span class="meta-text">编号 {{ item.hid }}</span>
                        </div>

                        <h3 class="headline-title">{{ item.title }}</h3>

                        <div class="headline-info">
                            <span>作者 {{ item.nickName || "未知" }}</span>
                            <span>浏览 {{ item.pageViews }}</span>
                            <span>{{ formatPastHour(item.pastHour) }}更新</span>
                        </div>
                    </div>

                    <div class="headline-actions">
                        <button class="detail-btn" @click="showDetail(item.hid)">查看详情</button>
                        <button class="edit-btn" @click="editHeadline(item.hid)">修改文章</button>
                        <button class="danger-btn" @click="deleteHeadline(item.hid)">删除文章</button>
                    </div>
                </article>
            </div>

            <div v-else class="empty-state">
                <p class="empty-title">没有找到符合条件的文章</p>
                <p class="empty-subtitle">你可以换个关键词重新搜索，或者先去发布一篇内容。</p>
            </div>

            <div class="pager" v-if="pageInfo && pageInfo.totalPage > 0">
                <button
                    class="pager-btn"
                    :disabled="query.pageNum <= 1"
                    @click="prevPage()"
                >
                    上一页
                </button>

                <div class="page-number-list">
                    <button
                        v-for="page in pageNumbers"
                        :key="page"
                        class="page-number"
                        :class="{ active: page === query.pageNum }"
                        @click="goPage(page)"
                    >
                        {{ page }}
                    </button>
                </div>

                <button
                    class="pager-btn"
                    :disabled="query.pageNum >= (pageInfo?.totalPage ?? 0)"
                    @click="nextPage()"
                >
                    下一页
                </button>
            </div>
        </section>
    </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref, watch} from "vue";
import instance, {HEADLINE_NO_PERMISSION_CODE, SUCCESS_CODE} from "../../axios";
import {useRoute, useRouter} from "vue-router";
import {hasToken} from "../../utils/token-auth";
import {formatPastHour} from "../../utils/time-format";

interface News {
    hid: string;
    title: string;
    type: number;
    nickName: string;
    pageViews: number;
    pastHour: number;
}

interface PageInfo<T> {
    pageNum: number;
    pageSize: number;
    totalSize: number;
    totalPage: number;
    pageData: T[];
}

interface BackendPageInfo<T> {
    currentPage: number;
    pageSize: number;
    total: number;
    pageList: T[];
}

interface NewsType {
    tid: number;
    tname: string;
}

interface HeadlineDetail {
    hid: string;
    title: string;
    article: string;
    type: number;
}

interface ArticleForm {
    title: string;
    type: number;
    article: string;
}

interface QueryParams {
    keyWords: string;
    type: number;
    pageNum: number;
    pageSize: number;
}

type EditorMode = "create" | "edit";

const route = useRoute();
const router = useRouter();

const list = ref<News[]>([]);
const pageInfo = ref<PageInfo<News> | null>(null);
const searchKeyWords = ref("");
const newsTypes = ref<NewsType[]>([]);
const editorVisible = ref(false);
const editorMode = ref<EditorMode>("create");
const editingHid = ref("");
const loadingEdit = ref(false);
const submitting = ref(false);
const formError = ref("");
const articleForm = ref<ArticleForm>({
    title: "",
    type: 0,
    article: ""
});

const query = computed<QueryParams>(() => ({
    keyWords: String(route.query.keyWords ?? ""),
    type: Number(route.query.type ?? 0),
    pageNum: Number(route.query.pageNum ?? 1),
    pageSize: Number(route.query.pageSize ?? 5)
}));

const pageNumbers = computed(() => {
    if (!pageInfo.value || pageInfo.value.totalPage <= 0) {
        return [];
    }
    return Array.from({length: pageInfo.value.totalPage}, (_, index) => index + 1);
});

const editorTitle = computed(() => editorMode.value === "create" ? "写文章" : "修改文章");
const editorSubtitle = computed(() => editorMode.value === "create"
    ? "发布后会出现在你的文章列表中。"
    : "提交后会更新原文章内容。");
const submitButtonText = computed(() => {
    if (submitting.value) {
        return editorMode.value === "create" ? "发布中..." : "保存中...";
    }
    return editorMode.value === "create" ? "发布文章" : "保存修改";
});
const titleLength = computed(() => articleForm.value.title.length);
const articleLength = computed(() => articleForm.value.article.length);

onMounted(() => {
    loadNewsTypes();
});

watch(
    () => route.query,
    () => {
        searchKeyWords.value = query.value.keyWords;
        loadPage();
    },
    {
        immediate: true
    }
);

async function loadNewsTypes() {
    try {
        const {data} = await instance.get("/portal/findAllTypes");
        if (data.code === SUCCESS_CODE) {
            newsTypes.value = data.data ?? [];
        } else {
            newsTypes.value = [];
        }
    } catch {
        newsTypes.value = [];
    }
}

async function loadPage() {
    if (!hasToken()) {
        await router.push("/login");
        return;
    }

    try {
        const {data} = await instance.post("/headline/findMyNewsPage", {
            keyWord: query.value.keyWords,
            type: query.value.type,
            currentPage: query.value.pageNum,
            pageSize: query.value.pageSize
        });
        if (data.code !== SUCCESS_CODE) {
            list.value = [];
            pageInfo.value = emptyPageInfo();
            return;
        }

        const backendPage = data.data as BackendPageInfo<News> | null;
        list.value = backendPage?.pageList ?? [];
        pageInfo.value = toPageInfo(backendPage);
    } catch (error) {
        list.value = [];
        pageInfo.value = emptyPageInfo();
        alert("查询文章列表失败：" + error);
    }
}

function updateQuery(patch: Partial<QueryParams>) {
    return router.push({
        path: "/addormodifynews",
        query: {
            keyWords: String(patch.keyWords ?? query.value.keyWords),
            type: Number(patch.type ?? query.value.type),
            pageNum: Number(patch.pageNum ?? query.value.pageNum),
            pageSize: Number(patch.pageSize ?? query.value.pageSize)
        }
    });
}

function search() {
    updateQuery({
        keyWords: searchKeyWords.value.trim(),
        type: 0,
        pageNum: 1
    });
}

function changePageSize(pageSize: number) {
    updateQuery({
        pageSize,
        pageNum: 1
    });
}

function prevPage() {
    if (query.value.pageNum <= 1) {
        return;
    }
    updateQuery({pageNum: query.value.pageNum - 1});
}

function nextPage() {
    if (!pageInfo.value || query.value.pageNum >= pageInfo.value.totalPage) {
        return;
    }
    updateQuery({pageNum: query.value.pageNum + 1});
}

function goPage(page: number) {
    if (!pageInfo.value) {
        return;
    }
    if (page < 1 || page > pageInfo.value.totalPage) {
        return;
    }
    updateQuery({pageNum: page});
}

function showDetail(hid: string) {
    if (!hasToken()) {
        router.push("/login");
        return;
    }
    router.push({
        path: "/detail",
        query: {
            hid: String(hid)
        }
    });
}

async function deleteHeadline(hid: string) {
    if (!hasToken()) {
        await router.push("/login");
        return;
    }

    try {
        const {data} = await instance.delete("/headline/delete", {
            params: {
                hid
            }
        });

        if (data.code !== SUCCESS_CODE) {
            if (data.code === HEADLINE_NO_PERMISSION_CODE) {
                alert("无权限删除该文章");
            } else {
                alert(data.message || "删除文章失败");
            }
            return;
        }

        if (list.value.length === 1 && query.value.pageNum > 1) {
            updateQuery({pageNum: query.value.pageNum - 1});
            return;
        }

        await loadPage();
    } catch (error) {
        alert("删除文章失败：" + error);
    }
}

function goback() {
    router.push("/headlinenews");
}

function addHeadline() {
    if (!hasToken()) {
        router.push("/login");
        return;
    }
    editorMode.value = "create";
    editingHid.value = "";
    resetEditorForm();
    editorVisible.value = true;
}

async function editHeadline(hid: string) {
    if (!hasToken()) {
        await router.push("/login");
        return;
    }

    editorMode.value = "edit";
    editingHid.value = hid;
    editorVisible.value = true;
    loadingEdit.value = true;
    formError.value = "";
    resetEditorForm();

    try {
        const {data} = await instance.get("/headline/findHeadlineById", {
            params: {
                hid
            }
        });

        if (data.code !== SUCCESS_CODE || !data.data) {
            formError.value = data.message || "文章不存在";
            return;
        }

        const headline = data.data as HeadlineDetail;
        editingHid.value = String(headline.hid ?? hid);
        articleForm.value = {
            title: headline.title ?? "",
            type: Number(headline.type ?? 0),
            article: headline.article ?? ""
        };
    } catch (error) {
        formError.value = "加载文章失败：" + error;
    } finally {
        loadingEdit.value = false;
    }
}

function closeEditor() {
    editorVisible.value = false;
    loadingEdit.value = false;
    submitting.value = false;
    formError.value = "";
}

function resetEditorForm() {
    articleForm.value = {
        title: "",
        type: 0,
        article: ""
    };
    formError.value = "";
}

async function submitEditor() {
    if (!validateEditorForm()) {
        return;
    }

    submitting.value = true;
    formError.value = "";

    const payload = {
        title: articleForm.value.title.trim(),
        type: articleForm.value.type,
        article: articleForm.value.article.trim()
    };

    try {
        const {data} = editorMode.value === "create"
            ? await instance.post("/headline/publish", payload)
            : await instance.post("/headline/update", {
                hid: editingHid.value,
                ...payload
            });

        if (data.code !== SUCCESS_CODE) {
            formError.value = data.message || (editorMode.value === "create" ? "发布文章失败" : "保存修改失败");
            return;
        }

        closeEditor();
        if (editorMode.value === "create") {
            await updateQuery({
                keyWords: "",
                type: 0,
                pageNum: 1
            });
        }
        await loadPage();
    } catch (error) {
        formError.value = (editorMode.value === "create" ? "发布文章失败：" : "保存修改失败：") + error;
    } finally {
        submitting.value = false;
    }
}

function validateEditorForm() {
    const title = articleForm.value.title.trim();
    const article = articleForm.value.article.trim();

    if (!title) {
        formError.value = "请填写文章标题";
        return false;
    }
    if (title.length > 50) {
        formError.value = "文章标题不能超过 50 个字";
        return false;
    }
    if (!articleForm.value.type) {
        formError.value = "请选择文章分类";
        return false;
    }
    if (!article) {
        formError.value = "请填写正文内容";
        return false;
    }
    if (article.length > 5000) {
        formError.value = "正文内容不能超过 5000 个字";
        return false;
    }

    return true;
}

function toPageInfo(backendPage: BackendPageInfo<News> | null): PageInfo<News> {
    const pageSize = backendPage?.pageSize ?? query.value.pageSize;
    const totalSize = backendPage?.total ?? 0;

    return {
        pageNum: backendPage?.currentPage ?? query.value.pageNum,
        pageSize,
        totalSize,
        totalPage: pageSize > 0 ? Math.ceil(totalSize / pageSize) : 0,
        pageData: backendPage?.pageList ?? []
    };
}

function emptyPageInfo(): PageInfo<News> {
    return {
        pageNum: query.value.pageNum,
        pageSize: query.value.pageSize,
        totalSize: 0,
        totalPage: 0,
        pageData: []
    };
}
</script>

<style scoped>
.page {
    width: min(100%, 1040px);
    padding: 0 20px 30px;
    display: flex;
    flex-direction: column;
    gap: 14px;
}

.hero-card,
.toolbar-card,
.editor-card,
.content-card {
    border: 1px solid rgba(33, 37, 41, 0.08);
    border-radius: 30px;
    background:
        radial-gradient(circle at top right, rgba(255, 192, 8, 0.16), transparent 28%),
        linear-gradient(135deg, rgba(255, 255, 255, 0.98) 0%, rgba(242, 247, 251, 0.92) 100%);
    box-shadow: 0 24px 54px rgba(28, 43, 58, 0.1);
}

.hero-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 16px;
    padding: 20px 22px;
}

.hero-main {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.hero-copy {
    max-width: 520px;
}

.hero-tag,
.section-tag {
    margin: 0 0 6px;
    color: #9b7b1a;
    font-size: 11px;
    font-weight: 700;
    letter-spacing: 0.2em;
}

.hero-title {
    margin: 0;
    color: #18232f;
    font-size: 30px;
    line-height: 1.04;
}

.hero-subtitle {
    margin: 6px 0 0;
    color: #667789;
    font-size: 13px;
    line-height: 1.5;
}

.hero-stats {
    display: grid;
    grid-template-columns: repeat(4, minmax(0, 1fr));
    gap: 10px;
    max-width: 760px;
}

.stat-pill {
    padding: 10px 12px;
    border: 1px solid rgba(49, 64, 78, 0.08);
    border-radius: 14px;
    background: rgba(255, 255, 255, 0.82);
}

.stat-label {
    display: block;
    margin-bottom: 4px;
    color: #7b8b9a;
    font-size: 11px;
}

.stat-pill strong {
    color: #18232f;
    font-size: 16px;
}

.hero-actions {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 10px;
    flex-shrink: 0;
    align-self: flex-start;
}

.toolbar-card {
    padding: 14px 18px;
}

.editor-card {
    padding: 24px;
    display: flex;
    flex-direction: column;
    gap: 18px;
}

.editor-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 16px;
}

.editor-header h2 {
    margin: 0;
    color: #18232f;
    font-size: 28px;
}

.editor-subtitle {
    margin: 8px 0 0;
    color: #667789;
    font-size: 14px;
    line-height: 1.5;
}

.editor-loading {
    padding: 28px 18px;
    border: 1px dashed rgba(49, 64, 78, 0.18);
    border-radius: 18px;
    background: rgba(255, 255, 255, 0.76);
    color: #526373;
    text-align: center;
    font-size: 14px;
    font-weight: 700;
}

.editor-form {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.form-row {
    display: grid;
    grid-template-columns: minmax(0, 1fr) auto;
    gap: 16px;
    align-items: end;
}

.field-block {
    display: flex;
    flex-direction: column;
    gap: 8px;
    color: #31404e;
    font-size: 13px;
    font-weight: 700;
}

.field-block input,
.field-block select,
.field-block textarea {
    width: 100%;
    border: 1px solid rgba(49, 64, 78, 0.12);
    border-radius: 14px;
    background: rgba(255, 255, 255, 0.98);
    color: #18232f;
    font-size: 14px;
    line-height: 1.5;
    transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.field-block input,
.field-block select {
    height: 44px;
    padding: 0 12px;
}

.field-block textarea {
    min-height: 220px;
    padding: 12px;
    resize: vertical;
}

.field-block input:focus,
.field-block select:focus,
.field-block textarea:focus {
    outline: none;
    border-color: #f0b70a;
    box-shadow: 0 0 0 4px rgba(255, 192, 8, 0.15);
}

.field-counter {
    min-width: 180px;
    padding: 12px 14px;
    border: 1px solid rgba(49, 64, 78, 0.08);
    border-radius: 16px;
    background: rgba(255, 255, 255, 0.78);
    display: flex;
    flex-direction: column;
    gap: 6px;
    color: #667789;
    font-size: 12px;
    font-weight: 700;
}

.form-error {
    margin: 0;
    padding: 10px 12px;
    border: 1px solid rgba(191, 72, 48, 0.18);
    border-radius: 12px;
    background: rgba(255, 232, 223, 0.72);
    color: #b4472f;
    font-size: 13px;
    font-weight: 700;
}

.editor-actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    flex-wrap: wrap;
}

.search-panel {
    border-radius: 18px;
    background: rgba(255, 255, 255, 0.82);
    padding: 14px 16px;
}

.search-label {
    display: block;
    margin-bottom: 8px;
    color: #31404e;
    font-size: 13px;
    font-weight: 700;
}

.search-box {
    display: flex;
    align-items: center;
    gap: 10px;
}

.search-box input {
    flex: 1;
    min-width: 0;
    height: 42px;
    padding: 0 12px;
    border: 1px solid rgba(49, 64, 78, 0.12);
    border-radius: 12px;
    background: rgba(255, 255, 255, 0.98);
    color: #18232f;
    font-size: 13px;
    transition: border-color 0.2s ease, box-shadow 0.2s ease, transform 0.2s ease;
}

.search-box input:focus,
.page-size-box select:focus {
    outline: none;
    border-color: #f0b70a;
    box-shadow: 0 0 0 4px rgba(255, 192, 8, 0.15);
}

.content-card {
    padding: 24px;
    display: flex;
    flex-direction: column;
    gap: 18px;
}

.list-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    gap: 18px;
}

.list-header h2 {
    margin: 0;
    color: #18232f;
    font-size: 28px;
}

.page-size-box {
    display: flex;
    align-items: center;
    gap: 10px;
    color: #526373;
    font-size: 14px;
    font-weight: 600;
}

.page-size-box select {
    height: 42px;
    padding: 0 14px;
    border: 1px solid rgba(49, 64, 78, 0.12);
    border-radius: 12px;
    background: rgba(255, 255, 255, 0.96);
    color: #18232f;
    font-size: 14px;
}

.headline-list {
    display: flex;
    flex-direction: column;
    gap: 14px;
}

.headline-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 18px;
    padding: 20px 22px;
    border: 1px solid rgba(49, 64, 78, 0.1);
    border-radius: 24px;
    background: rgba(255, 255, 255, 0.92);
    box-shadow: 0 14px 36px rgba(28, 43, 58, 0.08);
}

.headline-main {
    flex: 1;
    min-width: 0;
}

.headline-meta,
.headline-info {
    display: flex;
    flex-wrap: wrap;
    gap: 10px 14px;
    align-items: center;
}

.type-chip {
    padding: 8px 14px;
    border-radius: 999px;
    background: rgba(255, 192, 8, 0.16);
    color: #8b6810;
    font-size: 13px;
    font-weight: 700;
}

.meta-text,
.headline-info span {
    color: #627383;
    font-size: 14px;
}

.headline-title {
    margin: 12px 0 14px;
    color: #18232f;
    font-size: 20px;
    line-height: 1.4;
    word-break: break-word;
}

.headline-actions {
    display: flex;
    flex-direction: column;
    gap: 12px;
    flex-shrink: 0;
}

.primary-btn,
.ghost-btn,
.detail-btn,
.edit-btn,
.danger-btn,
.pager-btn,
.page-number {
    min-height: 38px;
    padding: 0 14px;
    border: 1px solid transparent;
    border-radius: 12px;
    font-size: 13px;
    font-weight: 700;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease, opacity 0.2s ease, border-color 0.2s ease, background-color 0.2s ease;
}

.primary-btn,
.detail-btn {
    background: linear-gradient(135deg, #212529 0%, #364758 100%);
    color: #fffdf7;
    box-shadow: 0 12px 24px rgba(33, 37, 41, 0.16);
}

.ghost-btn,
.pager-btn,
.page-number {
    border-color: rgba(49, 64, 78, 0.14);
    background: rgba(255, 255, 255, 0.94);
    color: #31404e;
}

.edit-btn {
    border-color: rgba(38, 120, 104, 0.18);
    background: linear-gradient(135deg, #eef8f4 0%, #dff1ea 100%);
    color: #1f6d5d;
    box-shadow: 0 10px 20px rgba(38, 120, 104, 0.1);
}

.danger-btn {
    border-color: rgba(191, 72, 48, 0.18);
    background: linear-gradient(135deg, #fff6f2 0%, #ffe8df 100%);
    color: #b4472f;
    box-shadow: 0 10px 20px rgba(191, 72, 48, 0.1);
}

.primary-btn:hover,
.ghost-btn:hover,
.detail-btn:hover,
.edit-btn:hover,
.danger-btn:hover,
.pager-btn:hover,
.page-number:hover {
    transform: translateY(-1px);
}

.primary-btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none;
}

.search-btn {
    min-width: 84px;
}

.empty-state {
    padding: 32px 18px;
    border: 1px dashed rgba(49, 64, 78, 0.18);
    border-radius: 24px;
    background: rgba(255, 255, 255, 0.72);
    text-align: center;
}

.empty-title {
    margin: 0;
    color: #1c2b3a;
    font-size: 20px;
    font-weight: 700;
}

.empty-subtitle {
    margin: 12px 0 0;
    color: #6a7b8b;
    font-size: 15px;
}

.pager {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 12px;
    flex-wrap: wrap;
}

.page-number-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: center;
    flex: 1;
}

.page-number.active {
    border-color: rgba(255, 192, 8, 0.55);
    background: linear-gradient(135deg, #ffd967 0%, #ffc008 100%);
    color: #3b2f0d;
    box-shadow: 0 10px 22px rgba(255, 192, 8, 0.2);
}

.pager-btn:disabled,
.page-number:disabled {
    opacity: 0.45;
    cursor: not-allowed;
    transform: none;
    box-shadow: none;
}

@media (max-width: 960px) {
    .page {
        padding: 0 16px 26px;
    }

    .hero-card,
    .toolbar-card,
    .editor-card,
    .content-card {
        padding: 18px 16px;
        border-radius: 24px;
    }

    .hero-card,
    .headline-card,
    .editor-header,
    .list-header {
        flex-direction: column;
        align-items: stretch;
    }

    .form-row {
        grid-template-columns: 1fr;
    }

    .field-counter {
        min-width: 0;
    }

    .hero-stats {
        grid-template-columns: repeat(2, minmax(0, 1fr));
    }

    .search-box {
        display: grid;
        grid-template-columns: minmax(0, 1fr) auto;
    }

    .headline-actions {
        width: 100%;
        flex-direction: row;
    }

    .headline-actions button {
        flex: 1;
    }

    .pager {
        justify-content: center;
    }
}

@media (max-width: 640px) {
    .hero-title {
        font-size: 26px;
    }

    .hero-card {
        gap: 12px;
    }

    .list-header h2 {
        font-size: 24px;
    }

    .hero-stats {
        grid-template-columns: 1fr 1fr;
    }

    .headline-card {
        padding: 20px 18px;
    }

    .headline-title {
        font-size: 19px;
    }

    .headline-actions {
        flex-direction: column;
    }

    .editor-actions {
        flex-direction: column;
    }

    .editor-actions button {
        width: 100%;
    }

    .search-box {
        grid-template-columns: 1fr;
    }

    .search-btn,
    .page-size-box {
        width: 100%;
    }

    .page-size-box {
        justify-content: space-between;
    }
}

@media (max-width: 520px) {
    .hero-stats {
        grid-template-columns: 1fr;
    }

    .hero-actions {
        width: 100%;
        align-self: stretch;
    }

    .hero-actions button {
        width: 100%;
    }
}
</style>
