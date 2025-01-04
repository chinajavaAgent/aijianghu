<template>
  <div class="min-h-screen bg-gray-100">
    <nav class="bg-white shadow">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex">
            <router-link to="/" class="flex-shrink-0 flex items-center">
              <span class="text-xl font-bold text-gray-800">AI群江湖</span>
            </router-link>
            <div class="hidden sm:ml-6 sm:flex sm:space-x-8">
              <router-link to="/" class="nav-link">首页</router-link>
              <router-link to="/resources" class="nav-link">资源</router-link>
            </div>
          </div>
          <div class="flex items-center">
            <template v-if="userStore.token">
              <span class="text-gray-600 mr-4">{{ userStore.nickname || userStore.username }}</span>
              <button @click="logout" class="nav-link">退出</button>
            </template>
            <template v-else>
              <router-link to="/login" class="nav-link">登录</router-link>
              <router-link to="/register" class="nav-link">注册</router-link>
            </template>
          </div>
        </div>
      </div>
    </nav>

    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from './store/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const logout = () => {
  userStore.clearUser()
  router.push('/login')
}
</script>

<style>
.nav-link {
  @apply inline-flex items-center px-1 pt-1 text-sm font-medium text-gray-500 hover:text-gray-900;
}

.nav-link.router-link-active {
  @apply border-b-2 border-indigo-500 text-gray-900;
}
</style>
