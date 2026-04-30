<template>
<div class="page">
    <div class="container" v-if="detail">
        <div class="title">
            {{detail.title}}
        </div>
        <div class="infoAuthor">
            <span>{{detail.typeName}}</span>
            <span>{{detail.author}}</span>
            <span>{{ formatPastHour(detail.pastHour) }}发布</span>
            <span>{{detail.pageViews}}</span>
        </div>
        <div class="content">
            {{detail.article}}
        </div>
    </div>
    <button @click="goback()">返回首页</button>
</div>
</template>

<script setup lang="ts">

import {computed, onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import instance, {SUCCESS_CODE} from "../../axios";
import {formatPastHour} from "../../utils/time-format";
const router = useRouter()
const route = useRoute();
const hid = computed(()=>String(route.query.hid ?? ""))

interface detailInter{
    hid:string;
    title:string;
    article:string;
    typeName:string;
    pageViews:number;
    pastHour:number;
    version:number;
    username:string;
    author:string;
}

const detail = ref<detailInter|null>(null);

onMounted(()=>{
    showDetail();
})

async function showDetail(){
    if (!hid.value) {
        alert("缺少新闻编号");
        return;
    }

    let {data} = await instance.post("/portal/showHeadlineDetail", null, {
        params: {
            hid: hid.value
        }
    });
    if(data.code === SUCCESS_CODE && data.data){
        detail.value = data.data;
    }else{
        alert("未知错误，请重试")
    }
}

function goback(){
    router.back()
}
</script>

<style scoped>
.page {
    width: min(100%, 1040px);
    min-height: calc(100vh - 120px);
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 22px;
    padding: 8px 20px 48px;
}

.container {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 22px;
    padding: 34px 38px;
    border: 1px solid rgba(33, 37, 41, 0.08);
    border-radius: 30px;
    background:
        radial-gradient(circle at top right, rgba(255, 192, 8, 0.18), transparent 26%),
        linear-gradient(135deg, rgba(255, 255, 255, 0.98) 0%, rgba(242, 247, 251, 0.92) 100%);
    box-shadow: 0 24px 54px rgba(28, 43, 58, 0.12);
}

.title {
    color: #18232f;
    font-size: 38px;
    font-weight: 700;
    line-height: 1.3;
    letter-spacing: -0.02em;
}

.infoAuthor {
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
}

.infoAuthor span {
    padding: 9px 14px;
    border-radius: 999px;
    background: #eef3f8;
    color: #526373;
    font-size: 13px;
    font-weight: 600;
}

.infoAuthor span:first-child {
    background: rgba(255, 192, 8, 0.16);
    color: #8b6810;
}

.content {
    color: #243544;
    font-size: 17px;
    line-height: 2;
    white-space: pre-wrap;
    word-break: break-word;
    padding: 28px 30px;
    border: 1px solid rgba(49, 64, 78, 0.08);
    border-radius: 24px;
    background: rgba(255, 255, 255, 0.82);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.page > button {
    min-width: 132px;
    min-height: 46px;
    padding: 0 18px;
    border: 1px solid rgba(49, 64, 78, 0.14);
    border-radius: 14px;
    background: rgba(255, 255, 255, 0.94);
    color: #31404e;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease, background-color 0.2s ease;
}

.page > button:hover {
    transform: translateY(-1px);
    border-color: rgba(255, 192, 8, 0.4);
    background: #fff8df;
    box-shadow: 0 12px 24px rgba(255, 192, 8, 0.16);
}

@media (max-width: 768px) {
    .page {
        padding: 0 16px 40px;
        gap: 18px;
    }

    .container {
        padding: 26px 20px;
        border-radius: 24px;
    }

    .title {
        font-size: 30px;
    }

    .content {
        padding: 22px 18px;
        font-size: 16px;
        line-height: 1.9;
    }

    .page > button {
        width: 100%;
    }
}

</style>
