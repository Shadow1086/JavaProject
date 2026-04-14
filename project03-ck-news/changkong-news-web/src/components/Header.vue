<template>
    <div class="header-container">

        <div class="left-header">
            <a href="#">长空头条</a>
            <a href="#">新闻</a>
            <a href="#">体育</a>
            <a href="#">娱乐</a>
            <a href="#">科技</a>
            <a href="#">其他</a>
        </div>

        <div class="right-header">
            <input type="text" placeholder="搜索最新头条">
            <div class="btn-list">
                <button class="login-btn" @click="login()">登录</button>
                <button class="register-btn" @click="register()">注册</button>
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
function login(){
    alert('此功能待更新');
}
function register(){
    alert('此功能待更新');
}
</script>


<style scoped>
a {
    color: #59636b;
    text-decoration: none;
    font-size: 16px;
}
a:first-child,a:first-child:hover{
    color: #c0adab;
}
a:hover {
    color: #59636b;
}
.header-container{
    width: 100vw;
    height: 7vh;
    background-color: #212529;
    display: flex;
    justify-content: space-around;
}

.left-header,.right-header,.btn-list{
    display: flex;
    align-items: center;
    gap: 15px;
}
.btn-list>button:last-of-type{
    background-color: #ffc008;
}

</style>
