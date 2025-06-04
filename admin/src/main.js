import { createApp } from 'vue'
import App from './App.vue'
import router from './route'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import request from './utils/request'

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.config.globalProperties.$request = request
app.mount('#app')
