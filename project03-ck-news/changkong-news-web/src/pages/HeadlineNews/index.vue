<!--主页新闻展示区-->

<template>
    <div class="container">
        <div class="item-list">
            <div class="item" v-for="(item,index) in list" :key="index">
                <span class="title">{{ item.title }}</span>
                <div class="span-list">
                    <span class="type">{{ item.type }}</span>
                    <span class="pageView">{{ item.pageView }}</span>
                    <span class="pastHours">{{ item.pastHors }}</span>
                </div>
                <button @click="showDetail()">查看全文</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

import {onMounted, ref} from "vue";
import instance from "../../axios";

type news={
    title:string;
    type:string;
    pageView:number;
    pastHors:string
}

onMounted(()=>{
    instance.post("/headline/show")
})

const list = ref<news[]>([]);

function showDetail(){
    alert("待开发")
}
</script>

<style scoped>
    .item-list{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        gap: 3vh;
    }
    .item {
        border: 1px solid #59636b;
    }
</style>
