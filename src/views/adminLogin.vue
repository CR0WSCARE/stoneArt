<template>
    <div class="login-container">
        <div class="login-box">
            <div class="logo-section">
                <img src="@/assets/logo.png" alt="logo">
            </div>
            <div class="form-section">
                <el-form 
                    ref="loginForm"
                    :model="loginForm"
                    label-width="80px"
                    >
                    <el-form-item label="" class="login-title">
                        <h2>管理员登录</h2>
                    </el-form-item>
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="loginForm.username" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="loginForm.password" show-password style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="captcha">
                        <div style="display: flex;">
                            <el-input v-model="loginForm.captcha" style="flex: 1"></el-input>
                            <div style="flex: 1">
                            </div>
                        </div>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" style="width: 60%;" @click="loginForm">登         录</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AdminLogin',
    data() {
        return {
            loginForm: {
                username: '',
                password: ''
            }
        }
    }
}
</script>

<style scoped>
.login-container {
    height: 100vh;
    width: 100vw;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #0f9876;
}

.login-box {
    display: flex;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    width: 800px;
}

.logo-section {
    flex: 1;
    padding: 20px;
    background: #f5f5f5;
}

.logo-section img {
    width: 100%;
    height: auto;
    object-fit: contain;
}

.form-section {
    flex: 2;
    padding: 40px;
}
</style>
