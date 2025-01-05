<!-- AI锦囊列表页面 -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 py-6 sm:py-12">
    <div class="container mx-auto px-4">
      <!-- 页面标题 -->
      <div class="text-center mb-8 sm:mb-12">
        <h1 class="text-3xl sm:text-4xl font-bold text-gray-800 mb-2 sm:mb-4">AI副业项目库</h1>
        <p class="text-lg sm:text-xl text-gray-600">精选AI副业项目，助你快速变现</p>
      </div>

      <!-- 锦囊列表 -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-8">
        <div v-for="tip in tipsList" :key="tip.id"
          class="bg-white rounded-xl sm:rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition-transform duration-300">
          <div class="relative">
            <!-- 金元宝背景 -->
            <div class="h-36 sm:h-48 bg-gradient-to-br relative overflow-hidden"
              :class="getGradientClass(tip)">
              <div class="absolute inset-0 flex items-center justify-center">
                <!-- 光效背景 -->
                <div class="absolute inset-0 opacity-20">
                  <div class="absolute inset-0 animate-pulse" 
                    :class="getGlowClass(tip)"></div>
                </div>
                <!-- 金元宝图片容器 -->
                <div class="relative w-24 h-24 sm:w-32 sm:h-32 
                  transform hover:scale-110 transition-transform duration-500
                  animate-float">
                  <img src="https://canyin-app.oss-cn-guangzhou.aliyuncs.com/images/money.png" 
                    alt="金元宝" 
                    class="w-full h-full object-contain drop-shadow-2xl filter brightness-110"/>
                  <!-- 光晕效果 -->
                  <div class="absolute inset-0 bg-white opacity-0 hover:opacity-20 
                    transition-opacity duration-300 rounded-full"></div>
                </div>
              </div>
            </div>
            <!-- 价格标签 -->
            <div class="absolute top-3 right-3 sm:top-4 sm:right-4 bg-white px-3 sm:px-4 py-1 rounded-full shadow-md">
              <span class="text-base sm:text-lg font-bold" :class="getTitleColorClass(tip)">￥{{ tip.price }}</span>
            </div>
          </div>
          
          <div class="p-4 sm:p-6">
            <h3 class="text-lg sm:text-xl font-bold mb-2 sm:mb-3" :class="getTitleColorClass(tip)">{{ tip.title }}</h3>
            <ul class="space-y-1.5 sm:space-y-2 mb-4 sm:mb-6 text-sm sm:text-base">
              <li class="flex items-start">
                <svg class="w-4 h-4 sm:w-5 sm:h-5 text-green-500 mr-2 mt-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                <span class="text-gray-600">{{ tip.description }}</span>
              </li>
            </ul>
            <button class="w-full py-2.5 sm:py-3 rounded-lg text-white font-semibold text-sm sm:text-base transition-colors"
              :class="getButtonClass(tip)"
              @click="handlePurchase(tip)">
              获取项目
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAiTipsList } from '@/api/tips'
import type { AiTips } from '@/types/tips'
import { showToast } from 'vant'

const router = useRouter()
const tipsList = ref<AiTips[]>([])

// 加载锦囊列表
const loadTipsList = async () => {
  try {
    const response = await getAiTipsList({
      page: 1,
      size: 100
    })
    tipsList.value = response.data.records || []
  } catch (error) {
    console.error('加载锦囊列表失败:', error)
    showToast('加载失败，请重试')
  }
}

// 获取样式类
const getGradientClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const gradients = [
    'from-blue-400 to-blue-600',
    'from-red-400 to-red-600',
    'from-yellow-400 to-yellow-600',
    'from-pink-400 to-pink-600',
    'from-indigo-400 to-indigo-600',
    'from-cyan-400 to-cyan-600',
    'from-teal-400 to-teal-600',
    'from-orange-400 to-orange-600',
    'from-lime-400 to-lime-600',
    'from-emerald-400 to-emerald-600'
  ]
  return gradients[index]
}

const getGlowClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const glows = [
    'bg-blue-300',
    'bg-red-300',
    'bg-yellow-300',
    'bg-pink-300',
    'bg-indigo-300',
    'bg-cyan-300',
    'bg-teal-300',
    'bg-orange-300',
    'bg-lime-300',
    'bg-emerald-300'
  ]
  return glows[index]
}

const getTitleColorClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const colors = [
    'text-blue-600',
    'text-red-600',
    'text-yellow-600',
    'text-pink-600',
    'text-indigo-600',
    'text-cyan-600',
    'text-teal-600',
    'text-orange-600',
    'text-lime-600',
    'text-emerald-600'
  ]
  return colors[index]
}

const getPriceColorClass = getTitleColorClass

const getButtonClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const buttons = [
    'bg-blue-600 hover:bg-blue-700',
    'bg-red-600 hover:bg-red-700',
    'bg-yellow-600 hover:bg-yellow-700',
    'bg-pink-600 hover:bg-pink-700',
    'bg-indigo-600 hover:bg-indigo-700',
    'bg-cyan-600 hover:bg-cyan-700',
    'bg-teal-600 hover:bg-teal-700',
    'bg-orange-600 hover:bg-orange-700',
    'bg-lime-600 hover:bg-lime-700',
    'bg-emerald-600 hover:bg-emerald-700'
  ]
  return buttons[index]
}

// 添加购买处理函数
const handlePurchase = (tip: AiTips) => {
  router.push({
    name: 'AiTipsDetail',
    params: { id: tip.id }
  })
}

// 页面加载时获取数据
onMounted(() => {
  loadTipsList()
})
</script>

<style scoped>
/* 添加移动端滚动优化 */
@media (max-width: 640px) {
  .container {
    max-width: 100%;
    overflow-x: hidden;
  }
}

/* 添加漂浮动画 */
@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-10px) rotate(5deg);
  }
}

.animate-float {
  animation: float 3s ease-in-out infinite;
}
</style> 