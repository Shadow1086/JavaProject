<template>
    <div class="page">
        <div class="container">
            <div class="panel">
                <div class="panel-header">
                    <p class="panel-tag">CHANGKONG NEWS</p>
                    <h2 class="panel-title">账号登录</h2>
                    <p class="panel-subtitle">输入用户名和密码，进入长空头条管理页。</p>
                </div>

                <form class="login-form">
                    <label class="field-label" for="username">用户名</label>
                    <div class="field">
                        <input
                            id="username"
                            v-model="newsUser.username"
                            class="field-input"
                            type="text"
                            placeholder="请输入用户名"
                            @blur="verityUsername()"
                        >
                        <span class="field-message">{{ infoUsername }}</span>
                    </div>

                    <label class="field-label" for="password">密码</label>
                    <div class="field">
                        <input
                            id="password"
                            v-model="newsUser.password"
                            class="field-input"
                            type="password"
                            placeholder="请输入密码"
                            @blur="verityUserPwd()"
                        >
                        <span class="field-message">{{ infoUserPwd }}</span>
                    </div>
                </form>

                <div class="btnlist">
                    <button class="primary-btn" @click="login()">登录</button>
                    <button class="secondary-btn" @click="goback()">返回首页</button>
                    <button class="ghost-btn" @click="register()">去注册</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import instance from "../../axios";
import {reactive, ref} from "vue";
import router from "../../routers/router";
import {setToken} from "../../utils/token-auth";

type newsUser = {
    username: string;
    password: string;
}

let newsUser: newsUser = reactive({
    username: "",
    password: ""
});

let infoUsername = ref("用户名支持中文、字母、数字、下划线，长度 2-20 位");
let infoUserPwd = ref();

const usernamePattern = /^[\u4e00-\u9fa5A-Za-z0-9_]{2,20}$/;

async function login() {
    if (await verityUserPwd() && await verityUsername()) {
        let {data} = await instance.post("/user/login", {
            username: newsUser.username,
            userPwd: newsUser.password
        });
        if (data.code === 200) {
            const token = data.data;
            setToken(token);
            await router.push("/headlinenews")
        } else if (data.code === 501) {
            alert("用户名错误");
        } else if (data.code === 503) {
            alert("密码错误");
        } else {
            alert("未知错误");
        }
    } else {
        alert("用户名密码有误");
    }

}

function goback() {
    router.push("headlineNews");
}

function register() {
    router.push("register");
}

// 验证输入格式

async function verityUsername() {
    if (usernamePattern.test(newsUser.username)) {
        // 符合格式
        infoUsername.value = "用户名合法"
        return true;
    } else {
        infoUsername.value = "用户名支持中文、字母、数字、下划线，长度 2-20 位"
        return false;
    }
}

async function verityUserPwd() {
    let pattern = /^(?=.*_)[a-zA-Z0-9_]{5,10}$/;
    if (pattern.test(newsUser.password)) {
        infoUserPwd.value = "密码合法"
        return true;
    } else {
        infoUserPwd.value = "密码不合法"
        return false;
    }
}


</script>

<style scoped>
.page {
    min-height: calc(100vh - 120px);
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 32px 20px 48px;
    background:
        radial-gradient(circle at top left, rgba(255, 200, 8, 0.18), transparent 30%),
        radial-gradient(circle at right center, rgba(54, 87, 116, 0.2), transparent 28%),
        linear-gradient(135deg, #f7f3eb 0%, #eef3f8 55%, #e4edf5 100%);
}

.container {
    width: min(100%, 760px);
    display: flex;
    justify-content: center;
    padding: 28px;
    border: 1px solid rgba(33, 37, 41, 0.08);
    border-radius: 28px;
    background: rgba(255, 255, 255, 0.72);
    box-shadow: 0 24px 60px rgba(28, 43, 58, 0.12);
    backdrop-filter: blur(8px);
}

.panel {
    flex: 1;
    width: 100%;
}

.panel-header {
    margin-bottom: 28px;
}

.panel-tag {
    margin: 0 0 8px;
    color: #9b7b1a;
    font-size: 12px;
    font-weight: 700;
    letter-spacing: 0.24em;
}

.panel-title {
    margin: 0;
    color: #18232f;
    font-size: 34px;
    font-weight: 700;
    line-height: 1.15;
}

.panel-subtitle {
    margin: 10px 0 0;
    color: #607080;
    font-size: 15px;
    line-height: 1.7;
}

.login-form {
    display: grid;
    grid-template-columns: 92px minmax(0, 1fr);
    align-items: start;
    gap: 18px 18px;
}

.field-label {
    padding-top: 14px;
    color: #31404e;
    font-size: 15px;
    font-weight: 600;
}

.field {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.field-input {
    width: 90%;
    height: 48px;
    padding: 0 16px;
    border: 1px solid rgba(49, 64, 78, 0.14);
    border-radius: 14px;
    background: rgba(255, 255, 255, 0.95);
    color: #18232f;
    font-size: 15px;
    transition: border-color 0.2s ease, box-shadow 0.2s ease, transform 0.2s ease;
}

.field-input::placeholder {
    color: #9aa8b6;
}

.field-input:focus {
    outline: none;
    border-color: #f0b70a;
    box-shadow: 0 0 0 4px rgba(255, 192, 8, 0.18);
    transform: translateY(-1px);
}

.field-message {
    min-height: 22px;
    color: #6e7f90;
    font-size: 13px;
    line-height: 1.6;
}

.btnlist {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    width: 100%;
    gap: 12px;
}

.btnlist button {
    width: 124px;
    min-height: 46px;
    border: 1px solid transparent;
    border-radius: 14px;
    font-size: 15px;
    font-weight: 600;
    cursor: pointer;
    transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease, background-color 0.2s ease;
}

.btnlist button:hover {
    transform: translateY(-1px);
}

.primary-btn {
    background: linear-gradient(135deg, #212529 0%, #364758 100%);
    color: #fffdf7;
    box-shadow: 0 12px 24px rgba(33, 37, 41, 0.2);
}

.secondary-btn {
    background: #ffc008;
    color: #2f2710;
    box-shadow: 0 10px 20px rgba(255, 192, 8, 0.22);
}

.ghost-btn {
    border-color: rgba(49, 64, 78, 0.16);
    background: rgba(255, 255, 255, 0.8);
    color: #31404e;
}

@media (max-width: 768px) {
    .container {
        padding: 22px 18px;
    }

    .login-form {
        grid-template-columns: 1fr;
        gap: 10px;
    }

    .field-label {
        padding-top: 0;
    }

    .btnlist {
        width: 100%;
        justify-content: stretch;
    }

    .btnlist button {
        width: 100%;
    }
}
</style>
