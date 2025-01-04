import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/Home.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/Register.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/opportunities',
      name: 'opportunities',
      component: () => import('../views/Opportunities.vue')
    },
    {
      path: '/project/:id',
      name: 'project-detail',
      component: () => import('../views/ProjectDetail.vue')
    }
  ]
})

export default router 