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
                    @submit.prevent="login"
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
                            show-password
                            placeholder="请输入密码"
                            style="width: 210px;">
                        </el-input>
                    </el-form-item>

                    <el-form-item label="验证码" prop="captcha">
                        <div style="display: flex;height: 32px; width: 210px;">
                            <el-input 
                                style="flex: 1;" 
                                placeholder="请输入验证码"
                                v-model="loginForm.captcha"
                                @input="handleCaptchaInput">
                            </el-input>
                            <div style="flex: 1;">
                                <valid-code 
                                ref="validCode"
                                @update:value="getCode"/>
                            </div>
                        </div>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button type="primary" native-type="submit" style="width: 60%">
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
import router from '@/route'
import { User, Lock } from '@element-plus/icons-vue'

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

        //验证码校验
        const validateCaptcha = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请输入验证码'));
                return;
            }
            if (!this.captcha) {
                callback(new Error('请刷新重试'));
                return;
            }
            if (value.toLowerCase() !== this.captcha) {
                callback(new Error('验证码错误'));
                this.$refs.validCode.refreshCode()
                return;
            }
            callback();
        }

        return {
            captcha: '',// 验证码组件传递
            loginForm: {
                username: '',
                password: '',
                captcha: ''
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                captcha: [
                    { validator: validateCaptcha, trigger: ['submit'] }
                ]
            }
        }
    },
    created() {
        // 可以在这里进行一些初始化操作
    },
    methods: {
        getCode(code) {
            this.captcha = code.toLowerCase(); // 获取验证码
            this.loginForm.captcha = ''; // 清空输入框
            this.$refs.loginForm.clearValidate('captcha'); // 清除验证码的验证状态
        },
        login() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.$request.post('/admin/login', this.loginForm).then(res => {
                        if(res.code === 200){
                            router.push('/home')
                            this.$message.success('登录成功')
                            localStorage.setItem("honey-user",JSON.stringify(res.data))  //储存用户登录数据
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }
            });
        },
        handleCaptchaInput() {
            // 在输入时清除验证状态
            this.$refs.loginForm.clearValidate('captcha');
        }
    },
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
