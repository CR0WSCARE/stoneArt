import { createWebHistory, createRouter } from 'vue-router'

import Index from '@/views/Index.vue'
import adminLogin from '@/views/adminLogin.vue'

const routes = [
  { path: '/', component: Index },
  { path: '/adminLogin/adminLogin', component: adminLogin },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})
export default router