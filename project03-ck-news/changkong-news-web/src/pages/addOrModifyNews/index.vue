<template>
    <div class="page">
        <section class="hero-card">
            <div class="hero-main">
                <div class="hero-copy">
                    <p class="hero-tag">CONTENT STUDIO</p>
                    <h1 class="hero-title">文章管理台</h1>
                    <p class="hero-subtitle">
                        查看、检索并管理你发布的文章。
                    </p>
                </div>

                <div class="hero-stats">
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
                <button class="primary-btn" @click="addHeadline()">写文章</button>
            </div>
        </section>

        <section class="toolbar-card">
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

        <section class="content-card">
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
                            <span>作者 {{ item.publisher }}</span>
                            <span>浏览 {{ item.pageViews }}</span>
                            <span>{{ item.pastHours }} 小时前更新</span>
                        </div>
                    </div>

                    <div class="headline-actions">
                        <button class="detail-btn" @click="showDetail(item.hid)">查看详情</button>
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
import {computed, ref, watch} from "vue";
import instance from "../../axios";
import {useRoute, useRouter} from "vue-router";
import {hasToken} from "../../utils/token-auth";

interface News {
    hid: number;
    title: string;
    type: number;
    pageViews: number;
    pastHours: number;
    publisher: number;
}

interface PageInfo<T> {
    pageNum: number;
    pageSize: number;
    totalSize: number;
    totalPage: number;
    pageData: T[];
}

interface QueryParams {
    keyWords: string;
    type: number;
    pageNum: number;
    pageSize: number;
}

const route = useRoute();
const router = useRouter();

const list = ref<News[]>([]);
const pageInfo = ref<PageInfo<News> | null>(null);
const searchKeyWords = ref("");

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

async function loadPage() {
    if (!hasToken()) {
        await router.push("/login");
        return;
    }

    try {
        const {data} = await instance.post("/portal/findPageSelf", query.value);
        list.value = data.data?.pageData ?? [];
        pageInfo.value = data.data ?? {
            pageNum: query.value.pageNum,
            pageSize: query.value.pageSize,
            totalSize: 0,
            totalPage: 0,
            pageData: []
        };
    } catch (error) {
        list.value = [];
        pageInfo.value = {
            pageNum: query.value.pageNum,
            pageSize: query.value.pageSize,
            totalSize: 0,
            totalPage: 0,
            pageData: []
        };
        alert("查询文章列表失败：" + error);
    }
}

function updateQuery(patch: Partial<QueryParams>) {
    router.push({
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

function showDetail(hid: number) {
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

async function deleteHeadline(hid: number) {
    if (!hasToken()) {
        await router.push("/login");
        return;
    }

    try {
        await instance.post("/headline/deleteHeadline", {hid});

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
    alert("写作功能待开发中，敬请期待");
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

.danger-btn {
    border-color: rgba(191, 72, 48, 0.18);
    background: linear-gradient(135deg, #fff6f2 0%, #ffe8df 100%);
    color: #b4472f;
    box-shadow: 0 10px 20px rgba(191, 72, 48, 0.1);
}

.primary-btn:hover,
.ghost-btn:hover,
.detail-btn:hover,
.danger-btn:hover,
.pager-btn:hover,
.page-number:hover {
    transform: translateY(-1px);
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
    .content-card {
        padding: 18px 16px;
        border-radius: 24px;
    }

    .hero-card,
    .headline-card,
    .list-header {
        flex-direction: column;
        align-items: stretch;
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
