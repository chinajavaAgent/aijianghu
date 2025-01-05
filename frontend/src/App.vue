<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <!-- 主要内容区域 -->
    <router-view></router-view>

    <!-- 底部导航栏 -->
    <nav class="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 shadow-lg z-50">
      <div class="max-w-screen-xl mx-auto px-4">
        <div class="flex justify-around items-center h-16">
          <router-link to="/" class="flex flex-col items-center space-y-1">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
            </svg>
            <span class="text-xs">首页</span>
          </router-link>

          <router-link to="/opportunities" class="flex flex-col items-center space-y-1">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"></path>
            </svg>
            <span class="text-xs">升级</span>
          </router-link>

          <router-link to="/profile" class="flex flex-col items-center space-y-1">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
            </svg>
            <span class="text-xs">我的</span>
          </router-link>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'

const route = useRoute()
</script>

<style>
.router-link-active {
  color: #3B82F6; /* 蓝色，表示激活状态 */
}

.router-link-active svg {
  color: #3B82F6;
}
</style>
