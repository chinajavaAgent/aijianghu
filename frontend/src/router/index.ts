import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import OperationMaterials from '../views/OperationMaterials.vue'
import Tutorials from '../views/Tutorials.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/Home.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue')
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('@/views/Profile.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/orders',
      name: 'orders',
      component: () => import('@/views/Orders.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/opportunities',
      name: 'opportunities',
      component: () => import('@/views/Opportunities.vue')
    },
    {
      path: '/project/:id',
      name: 'project-detail',
      component: () => import('@/views/ProjectDetail.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/opportunity-manage',
      name: 'opportunity-manage',
      component: () => import('@/views/OpportunityManage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin-manage',
      name: 'admin-manage',
      component: () => import('@/views/AdminManage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/operation-materials',
      name: 'OperationMaterials',
      component: OperationMaterials,
      meta: {
        title: '运营物料',
        requiresAuth: true
      }
    },
    {
      path: '/poster-generator',
      name: 'PosterGenerator',
      component: () => import('../views/PosterGenerator.vue'),
      meta: {
        title: '海报生成',
        requiresAuth: true
      }
    },
    {
      path: '/qrcode-generator',
      name: 'QrcodeGenerator',
      component: () => import('../views/QrcodeGenerator.vue'),
      meta: {
        title: '二维码生成',
        requiresAuth: true
      }
    },
    {
      path: '/copywriting-templates',
      name: 'CopywritingTemplates',
      component: () => import('../views/CopywritingTemplates.vue'),
      meta: {
        title: '文案模板',
        requiresAuth: true
      }
    },
    {
      path: '/tutorials',
      name: 'tutorials',
      component: Tutorials
    }
  ]
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  // 获取本地存储的token
  const token = localStorage.getItem('token')
  
  // 检查该路由是否需要登录权限
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 如果需要登录权限且未登录，则跳转到登录页
    if (!token) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      // 已登录，初始化用户状态并继续
      const userStore = useUserStore()
      userStore.initUserState()
      next()
    }
  } else {
    // 不需要登录权限的路由
    if (to.path === '/login' && token) {
      // 如果已登录且要访问登录页，重定向到首页
      next('/')
    } else {
      next()
    }
  }
})

export default router 