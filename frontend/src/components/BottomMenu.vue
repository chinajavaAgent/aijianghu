<template>
  <nav class="fixed bottom-0 left-0 right-0 bg-white/95 backdrop-blur-md border-t border-gray-200 shadow-lg z-50">
    <div class="max-w-screen-xl mx-auto">
      <div class="flex justify-around items-center h-16">
        <router-link to="/" class="nav-item" :class="{ 'active': isActive('home') }">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
          </svg>
          <span class="menu-text">首页</span>
        </router-link>

        <router-link to="/opportunities" class="nav-item" :class="{ 'active': isActive('opportunities') }">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"></path>
          </svg>
          <span class="menu-text">机缘</span>
        </router-link>

        <router-link to="/orders" class="nav-item" :class="{ 'active': isActive('orders') }">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"></path>
          </svg>
          <span class="menu-text">升境</span>
        </router-link>

        <router-link to="/big-opportunities" class="nav-item" :class="{ 'active': isActive('big-opportunities') }">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"></path>
          </svg>
          <span class="menu-text">考核</span>
        </router-link>

        <router-link to="/profile" class="nav-item" :class="{ 'active': isActive('profile') }">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
          </svg>
          <span class="menu-text">我的</span>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'

const route = useRoute()

const isActive = (name: string) => {
  if (name === 'home' && route.path === '/') return true
  if (name === 'opportunities' && route.path === '/opportunities') return true
  if (name === 'orders' && route.path === '/orders') return true
  if (name === 'big-opportunities' && route.path === '/big-opportunities') return true
  if (name === 'profile' && route.path === '/profile') return true
  return false
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.nav-item {
  @apply flex flex-col items-center space-y-1 text-gray-600 transition-colors duration-200;
}

.nav-item.active {
  @apply text-primary;
}

.nav-item.active svg {
  @apply text-primary;
  filter: drop-shadow(0 0 2px rgba(79, 70, 229, 0.3));
  transform: scale(1.1);
  transition: all 0.3s ease;
}

.nav-item svg {
  transition: all 0.3s ease;
}

/* 菜单文字样式 */
.menu-text {
  font-family: 'Ma Shan Zheng', cursive !important;
  font-size: 1rem;
  line-height: 1.2;
  margin-top: 4px;
  transition: all 0.3s ease;
}

.nav-item.active .menu-text {
  @apply text-primary;
  transform: scale(1.05);
}

/* 磨砂玻璃效果 */
.backdrop-blur-md {
  backdrop-filter: blur(12px);
}

.bg-white\/95 {
  background-color: rgba(255, 255, 255, 0.95);
}

:root {
  --primary-color: #4F46E5;
}

.text-primary {
  color: var(--primary-color);
}
</style> 