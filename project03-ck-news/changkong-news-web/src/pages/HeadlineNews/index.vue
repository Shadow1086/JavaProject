<!--主页新闻展示区-->

<template>
    <div class="container">
        <div class="item-list">
            <div class="item">
                <span class="title">测试数据</span>
                <div class="span-list">
                    <span>测试新闻</span>
                    <span>浏览量</span>
                    <span>发布时间</span>
                </div>
                <button @click="showDetail()">查看全文</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
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
</style>
