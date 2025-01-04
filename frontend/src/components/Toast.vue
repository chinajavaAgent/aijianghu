<template>
  <Transition name="fade">
    <div v-if="visible" class="fixed top-4 left-1/2 transform -translate-x-1/2 z-50">
      <div :class="[
        'px-4 py-2 rounded-lg shadow-lg text-white text-sm',
        type === 'success' ? 'bg-green-500' : 'bg-red-500'
      ]">
        {{ message }}
      </div>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const visible = ref(false)
const message = ref('')
const type = ref<'success' | 'error'>('success')
let timer: number | null = null

const show = (msg: string, t: 'success' | 'error' = 'success', duration = 2000) => {
  // 清除之前的定时器
  if (timer) {
    clearTimeout(timer)
  }
  
  // 设置消息和类型
  message.value = msg
  type.value = t
  visible.value = true
  
  // 设置定时器自动关闭
  timer = window.setTimeout(() => {
    visible.value = false
  }, duration)
}

// 导出方法供其他组件使用
defineExpose({ show })
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style> 