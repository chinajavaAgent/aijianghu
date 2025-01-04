<template>
  <div class="icon" :class="name">
    <svg v-if="name === 'menu'" viewBox="0 0 24 24" class="w-6 h-6">
      <path fill="currentColor" d="M3 6h18v2H3V6m0 5h18v2H3v-2m0 5h18v2H3v-2z"/>
    </svg>
    <svg v-else-if="name === 'user'" viewBox="0 0 24 24" class="w-6 h-6">
      <path fill="currentColor" d="M12 4a4 4 0 014 4 4 4 0 01-4 4 4 4 0 01-4-4 4 4 0 014-4m0 10c4.42 0 8 1.79 8 4v2H4v-2c0-2.21 3.58-4 8-4z"/>
    </svg>
    <svg v-else-if="name === 'document'" viewBox="0 0 24 24" class="w-6 h-6">
      <path fill="currentColor" d="M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zm2 16H8v-2h8v2zm0-4H8v-2h8v2z"/>
    </svg>
    <svg v-else-if="name === 'pen'" viewBox="0 0 24 24" class="w-6 h-6">
      <path fill="currentColor" d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04a.996.996 0 0 0 0-1.41l-2.34-2.34a.996.996 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/>
    </svg>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  name: 'menu' | 'user' | 'document' | 'pen'
}>()
</script>

<style scoped>
.icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
</style> 