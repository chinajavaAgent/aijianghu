<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <h1 class="text-xl font-bold text-gray-800 py-4">AI锦囊管理</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 管理功能区 -->
      <div class="bg-white rounded-xl shadow-md p-6 mb-6">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-lg font-semibold">锦囊列表</h2>
          <button class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
            添加锦囊
          </button>
        </div>

        <!-- 锦囊列表 -->
        <div class="space-y-4">
          <div v-for="item in opportunities" :key="item.id" 
            class="border rounded-lg p-4 hover:shadow-md transition-shadow">
            <div class="flex justify-between items-start">
              <div>
                <h3 class="font-medium text-lg">{{ item.title }}</h3>
                <p class="text-gray-600 text-sm mt-1">价格：￥{{ item.price }}</p>
              </div>
              <div class="flex space-x-2">
                <button class="px-3 py-1 text-sm text-blue-600 border border-blue-600 rounded hover:bg-blue-50">
                  编辑
                </button>
                <button class="px-3 py-1 text-sm text-red-600 border border-red-600 rounded hover:bg-red-50">
                  删除
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 模拟数据
const opportunities = ref([
  {
    id: 1,
    title: 'AI写作变现',
    price: 10
  },
  {
    id: 2,
    title: 'AI绘画变现',
    price: 20
  },
  {
    id: 3,
    title: 'AI视频创作',
    price: 30
  }
])
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 