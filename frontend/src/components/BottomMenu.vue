<template>
  <div class="fixed bottom-0 left-0 right-0 bg-white/95 backdrop-blur-md border-t border-gray-200 pb-safe">
    <div class="grid grid-cols-5 h-14">
      <router-link v-for="item in menuItems" :key="item.route"
        :to="item.route" 
        class="flex flex-col items-center justify-center" 
        :class="{ 'text-gray-600': isActive(item.route) }">
        <component :is="icons[item.icon]" class="text-lg" />
        <span class="menu-text mt-1" :class="{'text-gray-600 font-bold': isActive(item.route)}">{{ item.text }}</span>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import icons from '@/components/icons'

const route = useRoute()

const isActive = (path: string) => route.path === path

type IconType = keyof typeof icons

const menuItems = [
  {
    icon: 'home' as IconType,
    text: '首页',
    route: '/'
  },
  {
    icon: 'opportunity' as IconType,
    text: '机缘',
    route: '/opportunity-manage'
  },
  {
    icon: 'order' as IconType,
    text: '升境',
    route: '/orders'
  },
  {
    icon: 'opportunity' as IconType,
    text: '考核',
    route: '/big-opportunities'
  },
  {
    icon: 'profile' as IconType,
    text: '我的',
    route: '/profile'
  }
]
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.pb-safe {
  padding-bottom: env(safe-area-inset-bottom);
}

/* 菜单文字样式 */
.menu-text {
  font-family: 'Ma Shan Zheng', cursive !important;
  font-size: 0.9rem;
  line-height: 1.2;
  color: #666;
}

/* 激活状态样式 */
.text-gray-600 {
  color: #4B5563;
}

/* 添加磨砂玻璃效果 */
.backdrop-blur-md {
  backdrop-filter: blur(12px);
}

.bg-white\/95 {
  background-color: rgba(255, 255, 255, 0.95);
}

/* 图标大小 */
.text-lg {
  font-size: 1.25rem;
}
</style> 