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
                        <h2>永信石雕管理员登录</h2>
                    </el-form-item>
                    <el-form-item label="用户名">
                        <el-input v-model="loginForm.username" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="loginForm.password" type="password" style="width: 60%;"></el-input>
                    </el-form-item>
                </el-form>
                <el-button type="primary" @click="login" style="margin-top: 20px;margin-left: 150px;">登录</el-button>
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
