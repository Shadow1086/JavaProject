<template>
    <div class="page">
        <div class="container">
            <div class="panel">
                <div class="panel-header">
                    <p class="panel-tag">CHANGKONG NEWS</p>
                    <h2 class="panel-title">账号注册</h2>
                    <p class="panel-subtitle">创建你的长空头条账号，补全基础信息后即可登录。</p>
                </div>

                <form class="register-form">
                    <label class="field-label" for="username">用户名</label>
                    <div class="field">
                        <input
                                id="username"
                                v-model="userRegister.username"
                                class="field-input"
                                type="text"
                                placeholder="请输入用户名"
                                @blur="verityUsername()"
                        >
                        <span class="field-message">{{ infoUsername }}</span>
                    </div>

                    <label class="field-label" for="nickname">笔名</label>
                    <div class="field">
                        <input
                                id="nickname"
                                v-model="userRegister.nickName"
                                class="field-input"
                                type="text"
                                placeholder="请输入笔名（可选）"
                        >
                        <span class="field-message">笔名会展示在内容发布信息中</span>
                    </div>

                    <label class="field-label" for="password">密码</label>
                    <div class="field">
                        <input
                                id="password"
                                v-model="userRegister.userPwd"
                                class="field-input"
                                type="password"
                                placeholder="请输入密码"
                                @blur="verityUserPwd()"
                        >
                        <span class="field-message">{{ infoUserPwd }}</span>
                    </div>

                    <label class="field-label" for="password-verify">确认密码</label>
                    <div class="field">
                        <input
                                id="password-verify"
                                v-model="userPwdVerify"
                                class="field-input"
                                type="password"
                                placeholder="请再次输入密码"
                                @blur="verityPwdCon()"
                        >
                        <span class="field-message">{{ infoUserPwdCon }}</span>
                    </div>
                </form>

                <div class="btnlist">
                    <button class="primary-btn" @click="register()">注册</button>
                    <button class="ghost-btn" @click="login()">去登录</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import instance from "../../axios";
import router from "../../routers/router";
import {reactive, ref} from "vue";

const userRegister = reactive({
    username: "",
    nickName: "",
    userPwd: ""
});

const userPwdVerify = ref("");

let infoUsername = ref("用户名支持中文、字母、数字、下划线，长度 2-20 位");
let infoUserPwd = ref("密码建议包含字母、数字和下划线");
let infoUserPwdCon = ref("两次输入需要保持一致");

const usernamePattern = /^[\u4e00-\u9fa5A-Za-z0-9_]{2,20}$/;

// 验证输入格式

function verityUsername() {
    if (usernamePattern.test(userRegister.username)) {
        // 符合格式
        infoUsername.value = "用户名合法"
        return true;
    } else {
        infoUsername.value = "用户名支持中文、字母、数字、下划线，长度 2-20 位"
        return false;
    }
}

function verityUserPwd() {
    let pattern = /^(?=.*_)[a-zA-Z0-9_]{5,10}$/;
    if (pattern.test(userRegister.userPwd)) {
        infoUserPwd.value = "密码合法"
        return true;
    } else {
        infoUserPwd.value = "密码不合法"
        return false;
    }
}

function verityPwdCon() {
    if (userPwdVerify.value === userRegister.userPwd && userRegister.userPwd!=="") {
        infoUserPwdCon.value = "密码与上次输入一致,可继续"
        return true;
    } else {
        infoUserPwdCon.value = "密码与上次输入不一致!"
        return false;
    }
}


async function register() {
    const usernameValid = verityUsername();
    const passwordValid = verityUserPwd();
    const passwordConfirmValid = verityPwdCon();

    if (!usernameValid || !passwordValid || !passwordConfirmValid) {
        return;
    }

    const {data} = await instance.post("/user/register", {
        username: userRegister.username,
        userPwd: userRegister.userPwd,
        nickName: userRegister.nickName
    });
    if (data.code === 200) {
        await router.push("login");
    }
}

function login() {
    router.push("login");
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
    background: radial-gradient(circle at top left, rgba(255, 200, 8, 0.18), transparent 30%),
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

.register-form {
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
    justify-content: flex-end;
    flex-wrap: wrap;
    width: 100%;
    gap: 12px;
    margin-top: 28px;
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

.ghost-btn {
    border-color: rgba(49, 64, 78, 0.16);
    background: rgba(255, 255, 255, 0.8);
    color: #31404e;
}

@media (max-width: 768px) {
    .container {
        padding: 22px 18px;
    }

    .register-form {
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
