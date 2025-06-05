import { createApp } from 'vue'
import App from './App.vue'
import router from './route'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import request from './utils/request'
import axios from 'axios'

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.config.globalProperties.$request = request
app.mount('#app')

// 配置 axios
app.config.globalProperties.$axios = axios

axios.get('/config.json').then((res)=>{
  app.config.globalProperties.$config = res.data
})
export const globals = app.config.globalProperties
