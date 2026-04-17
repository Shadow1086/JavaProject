<!--主页新闻展示区-->

<template>
    <div class="container">
        <div class="headline-shell">
            <div class="headline-toolbar">
                <div class="toolbar-copy">
                    <p class="toolbar-tag">HEADLINE BOARD</p>
                    <h2 class="toolbar-title">最新头条</h2>
                    <p class="toolbar-subtitle">
                        当前共 {{ pageInfo?.totalSize ?? 0 }} 条内容，按分类和关键词筛选后可直接翻页查看。
                    </p>
                </div>

                <div class="toolbar-meta">
                    <span class="meta-pill">第 {{ query.pageNum }} 页</span>
                    <span class="meta-pill">每页 {{ query.pageSize }} 条</span>
                </div>
            </div>

            <div v-if="list.length > 0" class="item-list">
                <article class="item" v-for="item in list" :key="item.hid">
                    <div class="item-main">
                        <h3 class="title">{{ item.title }}</h3>
                        <div class="span-list">
                            <span class="type">分类 {{ item.type }}</span>
                            <span class="page-view">浏览 {{ item.pageViews }}</span>
                            <span class="past-hours">{{ item.pastHours }}小时前更新</span>
                        </div>
                    </div>
                    <button class="detail-btn" @click="showDetail(item.hid)">查看全文</button>
                </article>
            </div>

            <div v-else class="empty-state">
                <p class="empty-title">暂时没有符合条件的头条</p>
                <p class="empty-subtitle">换个关键词，或者点击其他分类试试。</p>
            </div>

            <div class="pageSelect">
                <button class="pager-btn" @click="prevPage()">上一页</button>

                <div class="pageIndex">
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

                <button class="pager-btn" @click="nextPage()">下一页</button>

                <label class="page-size-box" for="pageSize">
                    <span>每页</span>
                    <select
                            id="pageSize"
                            name="pageSize"
                            :value="query.pageSize"
                            @change="changePageSize(Number(($event.target as HTMLSelectElement).value))"
                    >
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                </label>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

import {computed, ref, watch} from "vue";
import instance from "../../axios";
import {useRoute, useRouter} from "vue-router";
import {hasToken} from "../../utils/token-auth";

// 定义对象
interface news {
    hid: number;
    title: string;
    type: string;
    pageViews: number;
    pastHours: string;
    publisher: number
}

interface PageInfo<T> {
    pageNum: number;
    pageSize: number;
    totalSize: number;
    totalPage: number;
    pageData: T[]
}

// 定义变量
const route = useRoute();
const router = useRouter();

const list = ref<news[]>([]);
const pageInfo = ref<PageInfo<news> | null>(null);

const pageNumbers = computed(() => {
    if (!pageInfo.value || pageInfo.value.totalPage <= 0) {
        return []
    }
    return Array.from({length: pageInfo.value.totalPage}, (_, index) => index + 1)
})

const query = computed(() => ({
    keyWords: String(route.query.keyWords ?? ""),
    type: Number(route.query.type ?? 0),
    pageNum: Number(route.query.pageNum ?? 1),
    pageSize: Number(route.query.pageSize ?? 5)
}))


async function loadPage() {
    if(hasToken()){
        try {
            let {data} = await instance.post("/portal/findPage", query.value);
            list.value = data.data.pageData ?? [];
            pageInfo.value = data.data
        } catch (error) {
            console.log("加载新闻列表失败：" + error);
        }
    }else{
        router.push("login");
    }

}

// 监听器
watch(
        () => route.query,
        () => {
            loadPage();
        }, {
            immediate: true
        }
)

function updateQuery(patch: Partial<{
    keyWords: string;
    type: number;
    pageSize: number;
    pageNum: number;
}>) {
    router.push({
        path: "/headlinenews",
        query: {
            keyWords: String(patch.keyWords ?? query.value.keyWords),
            type: Number(patch.type ?? query.value.type),
            pageSize: Number(patch.pageSize ?? query.value.pageSize),
            pageNum: Number(patch.pageNum ?? query.value.pageNum)
        }
    });
}

function showDetail(hid: number) {
    if(hasToken()){
        router.push({
            path: "/detail",
            query: {
                hid: Number(hid)
            }
        })
    }else{
        router.push("login");
    }

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
    updateQuery({pageNum: query.value.pageNum + 1})
}

function goPage(page: number) {
    if (!pageInfo.value) {
        return;
    }
    if (page < 1 || page > pageInfo.value.totalPage) {
        return;
    }
    updateQuery({pageNum: page})
}

function changePageSize(pageSize: number) {
    updateQuery({
        pageSize,
        pageNum: 1
    });
}

</script>

<style scoped>
.container {
    width: min(100%, 1040px);
    padding: 0 20px 48px;
}

.headline-shell {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.headline-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    gap: 20px;
    padding: 28px 32px;
    border: 1px solid rgba(33, 37, 41, 0.08);
    border-radius: 28px;
    background: radial-gradient(circle at top right, rgba(255, 192, 8, 0.2), transparent 28%),
    linear-gradient(135deg, rgba(255, 255, 255, 0.96) 0%, rgba(242, 247, 251, 0.92) 100%);
    box-shadow: 0 24px 48px rgba(28, 43, 58, 0.1);
}

.toolbar-tag {
    margin: 0 0 10px;
    color: #9b7b1a;
    font-size: 12px;
    font-weight: 700;
    letter-spacing: 0.24em;
}

.toolbar-title {
    margin: 0;
    color: #18232f;
    font-size: 34px;
    line-height: 1.1;
}

.toolbar-subtitle {
    margin: 12px 0 0;
    max-width: 620px;
    color: #647586;
    font-size: 15px;
    line-height: 1.7;
}

.toolbar-meta {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-end;
    gap: 10px;
}

.meta-pill {
    padding: 10px 14px;
    border-radius: 999px;
    background: rgba(24, 35, 47, 0.08);
    color: #31404e;
    font-size: 13px;
    font-weight: 600;
}

.item-list {
    display: flex;
    flex-direction: column;
    gap: 18px;
}

.item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 24px;
    padding: 24px 26px;
    border: 1px solid rgba(49, 64, 78, 0.1);
    border-radius: 24px;
    background: rgba(255, 255, 255, 0.9);
    box-shadow: 0 14px 36px rgba(28, 43, 58, 0.08);
}

.item-main {
    display: flex;
    flex-direction: column;
    gap: 14px;
    min-width: 0;
}

.title {
    margin: 0;
    color: #18232f;
    font-size: 24px;
    font-weight: 700;
    line-height: 1.4;
}

.span-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.span-list span {
    padding: 8px 12px;
    border-radius: 999px;
    background: #eef3f8;
    color: #516272;
    font-size: 13px;
    font-weight: 600;
}

.type {
    background: rgba(255, 192, 8, 0.16) !important;
    color: #8c6910 !important;
}

.detail-btn {
    flex-shrink: 0;
    min-width: 112px;
    min-height: 44px;
    padding: 0 18px;
    border: 1px solid transparent;
    border-radius: 14px;
    background: linear-gradient(135deg, #212529 0%, #364758 100%);
    color: #fffdf7;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    box-shadow: 0 12px 24px rgba(33, 37, 41, 0.16);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.detail-btn:hover,
.pager-btn:hover,
.page-number:hover {
    transform: translateY(-1px);
}

.empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 10px;
    padding: 56px 24px;
    border: 1px dashed rgba(49, 64, 78, 0.18);
    border-radius: 24px;
    background: rgba(255, 255, 255, 0.75);
}

.empty-title {
    margin: 0;
    color: #1f2e3b;
    font-size: 22px;
    font-weight: 700;
}

.empty-subtitle {
    margin: 0;
    color: #6f8091;
    font-size: 14px;
}

.pageSelect {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-wrap: wrap;
    gap: 12px;
    padding: 20px 24px;
    border-radius: 22px;
    background: rgba(255, 255, 255, 0.82);
    box-shadow: 0 12px 28px rgba(28, 43, 58, 0.08);
}

.pager-btn,
.page-number {
    min-width: 42px;
    height: 42px;
    padding: 0 14px;
    border: 1px solid rgba(49, 64, 78, 0.14);
    border-radius: 12px;
    background: #ffffff;
    color: #31404e;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s ease, border-color 0.2s ease, background-color 0.2s ease, color 0.2s ease;
}

.pageIndex {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.page-number.active {
    border-color: #212529;
    background: #212529;
    color: #fffdf7;
    box-shadow: 0 10px 22px rgba(33, 37, 41, 0.16);
}

.page-size-box {
    display: inline-flex;
    align-items: center;
    gap: 10px;
    padding: 0 14px;
    height: 42px;
    border: 1px solid rgba(49, 64, 78, 0.14);
    border-radius: 12px;
    background: #ffffff;
    color: #516272;
    font-size: 14px;
    font-weight: 600;
}

.page-size-box select {
    border: none;
    background: transparent;
    color: #18232f;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
}

.page-size-box select:focus {
    outline: none;
}

@media (max-width: 768px) {
    .container {
        padding: 0 16px 40px;
    }

    .headline-toolbar {
        flex-direction: column;
        align-items: flex-start;
        padding: 24px 20px;
    }

    .toolbar-title {
        font-size: 28px;
    }

    .toolbar-meta {
        justify-content: flex-start;
    }

    .item {
        flex-direction: column;
        align-items: stretch;
        padding: 20px;
    }

    .title {
        font-size: 20px;
    }

    .detail-btn {
        width: 100%;
    }

    .pageSelect {
        justify-content: flex-start;
        padding: 18px;
    }

    .pageIndex {
        width: 100%;
    }
}
</style>
