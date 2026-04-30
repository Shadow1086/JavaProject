<template>
    <div class="header-container">
        <div class="header-shell">
            <div class="brand-block">
                <p class="brand-tag">CHANGKONG NEWS</p>
                <button class="brand-link" @click="searchByType(0)">长空头条</button>
                <p class="brand-subtitle">实时追踪最新头条，按分类筛选，快速定位你关心的内容。</p>
            </div>

            <div class="control-block">
                <div class="type-list">
                    <button
                        class="type-chip"
                        :class="{ active: currentType === 0 }"
                        @click="searchByType(0)"
                    >
                        全部
                    </button>
                    <button
                        v-for="item in newsTypes"
                        :key="item.tid"
                        class="type-chip"
                        :class="{ active: currentType === item.tid }"
                        @click="searchByType(item.tid)"
                    >
                        {{ item.tname }}
                    </button>
                </div>

                <div class="right-header">
                    <div class="search-box">
                        <input
                            v-model="keyWords"
                            class="search-input"
                            type="text"
                            placeholder="搜索最新头条"
                        >
                        <button class="search-btn" @click="search()">搜索</button>
                    </div>

                    <div class="btn-list" v-if="!loginOrNot()">
                        <button class="login-btn" @click="login()">登录</button>
                        <button class="register-btn" @click="register()">注册</button>
                    </div>
                    <div class="btn-list" v-else>
                        <button class="logout-btn" @click="logOut()">退出登录</button>
                        <button class="center-btn" @click="personCenter()">个人中心</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

import instance, {SUCCESS_CODE} from "../axios";
import {computed, onMounted, ref} from "vue";
import router from "../routers/router";
import {useRoute} from "vue-router";
import {hasToken, removeToken} from "../utils/token-auth";

interface NewsType {
    tid: number;
    tname: string;
}

// export let keywords = ref();

const newsTypes = ref<NewsType[]>([]);
let keyWords = ref();

const route = useRoute();
const currentType = computed(() => Number(route.query.type ?? 0));

async function showNewsTypes() {
    try {
        const response = await instance.get("/portal/findAllTypes");
        if (response.data.code === SUCCESS_CODE) {
            newsTypes.value = response.data.data ?? [];
        } else {
            newsTypes.value = [];
        }
    } catch (e) {
        alert("加载新闻分类失败")
        newsTypes.value = [];
    }
}

function search() {
    router.push({
        path: "/headlinenews",
        query: {
            keyWords: keyWords.value,
            type: 0,
            pageSize: Number(route.query.pageSize ?? 5),
            pageNum: 1
        }
    })
}

function searchByType(tid: number) {
    router.push({
        path: "/headlinenews",
        query: {
            type: tid,
            keyWords: "",
            pageSize: Number(route.query.pageSize ?? 5),
            pageNum: 1
        }
    })
    keyWords.value="";
}

onMounted(() => {
    showNewsTypes();
})


function login() {
    router.push("/login");
}

function logOut(){
    removeToken();
    router.push("/login");
}

function register() {
    router.push("/register");
}
function personCenter(){
    router.push("/addormodifynews")
}

function loginOrNot(){
    return hasToken();
}
</script>


<style scoped>
.header-container {
    width: min(100%, 1040px);
    padding: 24px 20px 0;
}

.header-shell {
    display: flex;
    justify-content: space-between;
    gap: 28px;
    padding: 28px 32px;
    border: 1px solid rgba(33, 37, 41, 0.08);
    border-radius: 30px;
    background:
        radial-gradient(circle at top left, rgba(255, 192, 8, 0.18), transparent 26%),
        linear-gradient(135deg, rgba(255, 255, 255, 0.98) 0%, rgba(242, 247, 251, 0.92) 100%);
    box-shadow: 0 24px 54px rgba(28, 43, 58, 0.12);
}

.brand-block {
    display: flex;
    flex-direction: column;
    gap: 10px;
    max-width: 260px;
}

.brand-tag {
    margin: 0;
    color: #9b7b1a;
    font-size: 12px;
    font-weight: 700;
    letter-spacing: 0.24em;
}

.brand-link {
    width: fit-content;
    padding: 0;
    border: none;
    background: transparent;
    color: #18232f;
    font-size: 36px;
    font-weight: 700;
    line-height: 1.05;
    cursor: pointer;
}

.brand-subtitle {
    margin: 0;
    color: #657687;
    font-size: 14px;
    line-height: 1.7;
}

.control-block {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 18px;
}

.type-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.type-chip {
    min-height: 38px;
    padding: 0 16px;
    border: 1px solid rgba(49, 64, 78, 0.12);
    border-radius: 999px;
    background: rgba(255, 255, 255, 0.92);
    color: #435363;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s ease, border-color 0.2s ease, background-color 0.2s ease, color 0.2s ease;
}

.type-chip:hover {
    transform: translateY(-1px);
    border-color: rgba(255, 192, 8, 0.45);
    background: rgba(255, 192, 8, 0.12);
    color: #8b6810;
}

.type-chip.active {
    border-color: rgba(255, 192, 8, 0.55);
    background: linear-gradient(135deg, #ffd967 0%, #ffc008 100%);
    color: #3b2f0d;
    box-shadow: 0 10px 22px rgba(255, 192, 8, 0.2);
}

.right-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 14px;
}

.search-box {
    display: flex;
    align-items: center;
    gap: 10px;
    flex: 1;
    max-width: 430px;
}

.search-input {
    flex: 1;
    min-width: 0;
    height: 44px;
    padding: 0 14px;
    border: 1px solid rgba(49, 64, 78, 0.12);
    border-radius: 12px;
    background: rgba(255, 255, 255, 0.98);
    color: #18232f;
    font-size: 14px;
    transition: border-color 0.2s ease, box-shadow 0.2s ease, transform 0.2s ease;
}

.search-input::placeholder {
    color: #98a6b4;
}

.search-input:focus {
    outline: none;
    border-color: #f0b70a;
    box-shadow: 0 0 0 4px rgba(255, 192, 8, 0.16);
    transform: translateY(-1px);
}

.search-btn,
.login-btn,
.register-btn,
.logout-btn,
.center-btn {
    min-height: 42px;
    padding: 0 16px;
    border: 1px solid transparent;
    border-radius: 12px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease, background-color 0.2s ease;
}

.search-btn:hover,
.login-btn:hover,
.register-btn:hover {
    transform: translateY(-1px);
}

.search-btn {
    background: linear-gradient(135deg, #212529 0%, #364758 100%);
    color: #fffdf7;
    box-shadow: 0 12px 24px rgba(33, 37, 41, 0.18);
}

.btn-list {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 12px;
}

.login-btn {
    border-color: rgba(49, 64, 78, 0.12);
    background: rgba(255, 255, 255, 0.92);
    color: #31404e;
}

.register-btn {
    background: #ffc008;
    color: #2f2710;
    box-shadow: 0 10px 20px rgba(255, 192, 8, 0.22);
}

.logout-btn {
    border-color: rgba(191, 72, 48, 0.18);
    background: linear-gradient(135deg, #fff6f2 0%, #ffe8df 100%);
    color: #b4472f;
    box-shadow: 0 10px 22px rgba(191, 72, 48, 0.12);
}

.center-btn {
    border-color: rgba(49, 64, 78, 0.12);
    background: linear-gradient(135deg, #f7fafc 0%, #edf3f7 100%);
    color: #31404e;
    box-shadow: 0 10px 22px rgba(49, 64, 78, 0.08);
}

.logout-btn:hover {
    transform: translateY(-1px);
    border-color: rgba(191, 72, 48, 0.3);
    background: linear-gradient(135deg, #ffece2 0%, #ffd9cd 100%);
}

.center-btn:hover {
    transform: translateY(-1px);
    border-color: rgba(49, 64, 78, 0.22);
    background: linear-gradient(135deg, #ffffff 0%, #e7eef3 100%);
}

@media (max-width: 860px) {
    .header-container {
        padding: 18px 16px 0;
    }

    .header-shell {
        flex-direction: column;
        padding: 24px 20px;
    }

    .brand-block {
        max-width: 100%;
    }

    .brand-link {
        font-size: 30px;
    }

    .right-header {
        flex-direction: column;
        align-items: stretch;
    }

    .search-box {
        max-width: none;
    }

    .btn-list {
        flex-wrap: wrap;
    }

    .login-btn,
    .register-btn,
    .logout-btn,
    .center-btn {
        flex: 1;
    }
}

@media (max-width: 640px) {
    .search-box {
        display: grid;
        grid-template-columns: minmax(0, 1fr) auto;
    }
}

@media (max-width: 520px) {
    .search-box {
        grid-template-columns: 1fr;
    }

    .btn-list {
        flex-direction: column;
    }

    .login-btn,
    .register-btn,
    .logout-btn,
    .center-btn,
    .search-btn {
        width: 100%;
    }
}

</style>
