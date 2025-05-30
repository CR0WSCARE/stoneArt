import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
import router from './route'

createApp(App).use(ElementPlus, { axios }).use(router).mount('#app')
