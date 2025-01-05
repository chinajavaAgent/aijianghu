<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <h1 class="text-xl font-bold text-gray-800 py-4">我的订单</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 标签页导航 -->
      <div class="bg-white rounded-xl shadow-sm mb-6 overflow-hidden">
        <div class="flex border-b">
          <button 
            v-for="tab in tabs" 
            :key="tab.value"
            @click="activeTab = tab.value"
            class="flex-1 py-3 text-center font-medium transition-colors duration-200 relative"
            :class="[
              activeTab === tab.value 
                ? 'text-blue-600' 
                : 'text-gray-600 hover:text-gray-800'
            ]"
          >
            {{ tab.label }}
            <div 
              v-if="activeTab === tab.value"
              class="absolute bottom-0 left-0 w-full h-0.5 bg-blue-600 transition-all duration-200"
            ></div>
          </button>
        </div>

        <!-- 订单列表内容 -->
        <div class="p-4">
          <div v-if="activeTab === 'pending'" class="space-y-4">
            <!-- 待审核订单 -->
            <div 
              v-for="order in pendingOrders" 
              :key="order.id" 
              class="bg-white rounded-xl p-4 shadow-sm transition-shadow duration-200 hover:shadow-md"
            >
              <div class="flex items-start gap-3">
                <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center flex-shrink-0">
                  <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                  </svg>
                </div>
                <div class="flex-1">
                  <div class="flex justify-between items-start">
                    <h3 class="font-medium text-gray-800 text-lg">{{ order.title }}</h3>
                    <span class="px-3 py-1 bg-yellow-50 text-yellow-600 rounded-full text-sm font-medium">审核中</span>
                  </div>
                  <p class="text-sm text-gray-500 mt-2">申请时间：{{ order.applyTime }}</p>
                  <p class="text-sm text-gray-500 mt-1">订单编号：{{ order.id }}</p>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">订单金额：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <button class="text-blue-600 hover:text-blue-700 text-sm font-medium">
                        查看详情
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 无订单提示 -->
            <div v-if="pendingOrders.length === 0" class="text-center py-12">
              <div class="w-20 h-20 mx-auto mb-4">
                <svg class="w-full h-full text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                </svg>
              </div>
              <p class="text-gray-500 mb-2">暂无待审核订单</p>
              <p class="text-sm text-gray-400">快去选购你感兴趣的机缘吧</p>
            </div>
          </div>

          <div v-else class="space-y-4">
            <!-- 已审核订单 -->
            <div 
              v-for="order in approvedOrders" 
              :key="order.id" 
              class="bg-white rounded-xl p-4 shadow-sm transition-shadow duration-200 hover:shadow-md"
            >
              <div class="flex items-start gap-3">
                <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center flex-shrink-0">
                  <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                  </svg>
                </div>
                <div class="flex-1">
                  <div class="flex justify-between items-start">
                    <h3 class="font-medium text-gray-800 text-lg">{{ order.title }}</h3>
                    <span class="px-3 py-1 bg-green-50 text-green-600 rounded-full text-sm font-medium">已通过</span>
                  </div>
                  <p class="text-sm text-gray-500 mt-2">审核时间：{{ order.approveTime }}</p>
                  <p class="text-sm text-gray-500 mt-1">订单编号：{{ order.id }}</p>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">订单金额：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <button class="text-blue-600 hover:text-blue-700 text-sm font-medium">
                        查看详情
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 无订单提示 -->
            <div v-if="approvedOrders.length === 0" class="text-center py-12">
              <div class="w-20 h-20 mx-auto mb-4">
                <svg class="w-full h-full text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                </svg>
              </div>
              <p class="text-gray-500 mb-2">暂无已审核订单</p>
              <p class="text-sm text-gray-400">快去选购你感兴趣的机缘吧</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 标签页配置
const tabs = [
  { label: '待审核', value: 'pending' },
  { label: '已审核', value: 'approved' }
]

// 当前选中的标签页
const activeTab = ref('pending')

// 模拟订单数据
const pendingOrders = ref([
  {
    id: 'ORD20240120143',
    title: 'AI写作变现',
    price: 10,
    applyTime: '2024-01-20 14:30'
  }
])

const approvedOrders = ref([
  {
    id: 'ORD20240119152',
    title: 'AI绘画变现',
    price: 20,
    approveTime: '2024-01-19 15:20'
  }
])
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 