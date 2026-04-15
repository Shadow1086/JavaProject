<template>
    <div class="page">
        <div class="container">
            <form>
                <label for="">用户名：</label>
                <div class="field">
                    <input type="text" id="username" placeholder="请输入用户名" v-model="userRegister.username">
                    <span></span>
                </div>
                <div class="field">
                    <input type="text" id="nickname" placeholder="请输入笔名(可选)" v-model="userRegister.nickName">
                    <span></span>
                </div>
                <div class="field">
                    <input type="password" id="password" placeholder="请输入密码" v-model="userRegister.userPwd">
                    <span></span>
                </div>
                <div class="field">
                    <input type="text" id="password-verify" placeholder="请确认密码" v-model="userPwdVerify">
                    <span></span>
                </div>
            </form>
            <button @click="register()"></button>
        </div>
    </div>
</template>

<script setup lang="ts">

import instance from "../../axios";
import router from "../../routers/router";
import {reactive, ref} from "vue";

const userRegister = reactive({
    username: "",
    nickName:"",
    userPwd: ""
})
let userPwdVerify = ref();

async function register() {
    let {data} = await instance.post("/user/register", {
        username: userRegister.username,
        userPwd: userRegister.userPwd,
        nickName:userRegister.nickName
    });
    if (data.code === 200) {
        await router.push("login");
    }
}
</script>

<style scoped>

</style>
