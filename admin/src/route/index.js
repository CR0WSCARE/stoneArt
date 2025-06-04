import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/adminLogin.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/home.vue'),
    children: [
      {
        path: 'admin1',
        name: 'yongxin',
        component: () => import('@/views/admin1.vue'),
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 添加全局前置守卫
router.beforeEach((to, from, next) => {
  const user = localStorage.getItem('honey-user')
  
  // 如果访问的不是登录页且没有登录信息
  if (to.path !== '/login' && !user) {
    next('/login')
  } else {
    next()
  }
})

export default router