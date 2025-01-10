<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <!-- 主要内容区域 -->
    <router-view></router-view>
    <BottomMenu />
  </div>
</template>

<script setup lang="ts">
import BottomMenu from '@/components/BottomMenu.vue'
</script>
