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
                    :rules="rules"
                    label-width="80px"
                >
                    <el-form-item label="" class="login-title">
                        <h2>管理员登录</h2>
                    </el-form-item>
                    <el-form-item label="用户名" prop="username">
                        <el-input 
                            v-model="loginForm.username" 
                            :prefix-icon="User"
                            placeholder="请输入用户名"
                            style="width: 210px;">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input 
                            v-model="loginForm.password" 
                            :prefix-icon="Lock"
                            type="password"
                            placeholder="请输入密码"
                            style="width: 210px;">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="captcha">
                        <div style="display: flex;height: 32px; width: 210px;">
                            <el-input style="flex: 1;" v-model="loginForm.captcha"></el-input>
                            <div style="flex: 1;">
                                <valid-code />
                            </div>
                        </div>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button type="primary" @click="handleSubmit" style="width: 60%">
                            登  录
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/ValidCode.vue'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

export default {
    name: 'AdminLogin',
    components: {
        ValidCode
    },
    setup() {
        return {
            User,
            Lock
        }
    },
    data() {
        return {
            loginForm: {
                username: '',
                password: ''
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                captcha: [
                    { required: true, message: '请输入验证码', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        handleSubmit() {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    // 这里添加登录逻辑
                    console.log('登录表单:', this.loginForm)
                    // 示例：调用登录API
                    this.$axios.post('/api/login', this.loginForm)
                        .then(response => {
                            if (response.data.success) {
                                ElMessage.success('登录成功')
                                this.$router.push('/admin/dashboard')
                            } else {
                                ElMessage.error(response.data.message)
                            }
                        })
                        .catch(error => {
                            ElMessage.error('密码或用户名错误')
                            console.error('登录错误:', error)
                        })
                }
            })
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
