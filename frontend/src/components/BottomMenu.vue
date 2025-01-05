<template>
  <div class="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 pb-safe">
    <div class="grid grid-cols-5 h-14">
      <router-link v-for="item in menuItems" :key="item.route"
        :to="item.route" 
        class="flex flex-col items-center justify-center" 
        :class="{ 'text-blue-600': isActive(item.route) }">
        <component :is="icons[item.icon]" />
        <span class="text-xs mt-1">{{ item.text }}</span>
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
    text: 'AI锦囊',
    route: '/opportunity-manage'
  },
  {
    icon: 'order' as IconType,
    text: '订单',
    route: '/orders'
  },
  {
    icon: 'operation' as IconType,
    text: '运营物料',
    route: '/operation-materials'
  },
  {
    icon: 'profile' as IconType,
    text: '我的',
    route: '/profile'
  }
]
</script>

<style scoped>
.pb-safe {
  padding-bottom: env(safe-area-inset-bottom);
}
</style> 