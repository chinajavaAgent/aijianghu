import { createRouter, createWebHistory, RouteLocationNormalized, NavigationGuardNext, RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/store/user'
import OperationMaterials from '../views/OperationMaterials.vue'
import Tutorials from '../views/Tutorials.vue'

const routes: Array<RouteRecordRaw> = [
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
    component: () => import('@/views/Opportunities.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/projects/:id',
    name: 'projects',
    component: () => import('@/views/ProjectList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/project/:id',
    name: 'project-detail',
    component: () => import('@/views/ProjectDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/opportunity-manage',
    name: 'OpportunityManage',
    component: () => import('@/views/OpportunityManage.vue'),
    meta: {
      requiresAuth: true,
      title: 'AI机缘管理'
    }
  },
  {
    path: '/project-manage/:tipId/:tipTitle',
    name: 'ProjectManage',
    component: () => import('@/views/ProjectManage.vue'),
    meta: {
      requiresAuth: true,
      title: '项目管理'
    }
  },
  {
    path: '/admin-manage',
    name: 'admin-manage',
    component: () => import('@/views/AdminManage.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/big-opportunities',
    name: 'big-opportunities',
    component: () => import('@/views/BigOpportunities.vue'),
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
  },
  {
    path: '/tips',
    name: 'AiTipsList',
    component: () => import('@/views/AiTipsList.vue'),
    meta: {
      title: 'AI锦囊',
      requiresAuth: true
    }
  },
  {
    path: '/tips/:id',
    name: 'AiTipsDetail',
    component: () => import('@/views/AiTipsDetail.vue'),
    meta: {
      title: 'AI锦囊详情',
      requiresAuth: true
    }
  }
]

const router = createRouter({
  history: createWebHistory('/aijianghu/'),
  routes
})

// 全局前置守卫
router.beforeEach(async (to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
  const userStore = useUserStore()
  
  // 如果用户已登录，则更新用户信息
  if (userStore.isLoggedIn) {
    await userStore.updateUserInfo()
  }
  
  // 处理分享码
  if (to.query.shareCode) {
    localStorage.setItem('shareCode', to.query.shareCode as string)
  }
  
  // 检查该路由是否需要登录权限
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})

export default router 