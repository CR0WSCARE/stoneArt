import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import axios from 'axios'
import router from './route'
import './assets/styles/common.css'

const app = createApp(App)

// 注册所有图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 配置 Element Plus
app.use(ElementPlus, {
  locale: zhCn, // 使用中文语言包
})

app.use(router)
app.mount('#app')

// 配置 axios
app.config.globalProperties.$axios = axios
