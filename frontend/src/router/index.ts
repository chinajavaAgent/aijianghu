import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Register from '../views/Register.vue'
import Login from '../views/Login.vue'
import Opportunities from '../views/Opportunities.vue'
import ProjectDetail from '../views/ProjectDetail.vue'
import Orders from '../views/Orders.vue'
import Profile from '../views/Profile.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/opportunities',
      name: 'opportunities',
      component: Opportunities
    },
    {
      path: '/project/:id',
      name: 'project-detail',
      component: ProjectDetail
    },
    {
      path: '/orders',
      name: 'orders',
      component: Orders
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile
    }
  ]
})

export default router 