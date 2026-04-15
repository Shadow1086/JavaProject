<template>
    <div class="page">
        <div class="container">
            <form>
                <label for="username">用户名：</label>
                <div class="field">
                    <input type="text" placeholder="请输入用户名" id="username" v-model="newsUser.username">
                    <span></span>
                </div>
                <label for="password">密码：</label>
                <div class="field">
                    <input type="password" placeholder="请输入密码" id="password" v-model="newsUser.password">
                    <span></span>
                </div>
            </form>
            <div class="btnlist">
                <button @click="login()">登录</button>
                <button @click="goback()">返回首页</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import instance from "../../axios";
import {reactive} from "vue";
import router from "../../routers/router";

type newsUser = {
    username: string;
    password: string;
}

let newsUser: newsUser = reactive({
    username: "",
    password: ""
});

async function login() {
    let {data} = await instance.post("/user/login", {
        username: newsUser.username,
        userPwd: newsUser.password
    });
    if (data.code === 200) {
        await router.push("header")
    } else if (data.code === 501) {
        alert("用户名错误")
    } else if (data.code === 503) {
        alert("密码错误")
    } else {
        alert("未知错误")
    }
}
function goback(){
    router.push("header");
}
</script>

<style scoped>

</style>
