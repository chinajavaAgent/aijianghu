<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 pb-20">
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
                  <div class="mt-3">
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">申核商家：</span>
                      <span>{{ order.reviewer }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">商家电话：</span>
                      <span>{{ order.phone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click="copyText(order.phone)">一键拨号</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">商家微信：</span>
                      <span>{{ order.wechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click="copyText(order.wechat)">点击复制</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">商家信誉：</span>
                      <span>{{ order.credit }} 分</span>
                    </div>
                  </div>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">订单金额：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <div class="flex items-center gap-2">
                        <span class="text-sm text-gray-500">{{ order.applyTime }}</span>
                      </div>
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
                  <div class="mt-3">
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">申核商家：</span>
                      <span>{{ order.reviewer }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">商家电话：</span>
                      <span>{{ order.phone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click="copyText(order.phone)">一键拨号</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">商家微信：</span>
                      <span>{{ order.wechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click="copyText(order.wechat)">点击复制</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">商家信誉：</span>
                      <span>{{ order.credit }} 分</span>
                    </div>
                  </div>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">订单金额：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <div class="flex items-center gap-2">
                        <span class="text-sm text-gray-500">{{ order.approveTime }}</span>
                      </div>
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
import { ref, onMounted } from 'vue'
import { showToast } from 'vant'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

// 标签页配置
const tabs = [
  { label: '待审核', value: 'pending' },
  { label: '已审核', value: 'approved' }
]

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 当前选中的标签页
const activeTab = ref('pending')

// 检查登录状态
const checkLogin = () => {
  const token = localStorage.getItem('token')
  const user = userStore.userInfo
  if (!token || !user) {
    // 如果发现token存在但user不存在，清除token
    if (token && !user) {
      localStorage.removeItem('token')
    }
    router.push({
      path: '/login',
      query: { redirect: route.fullPath }
    })
    return false
  }
  return true
}

// 在组件挂载时检查登录状态并获取用户信息
onMounted(async () => {
  if (!checkLogin()) return
  // 这里可以添加获取订单列表的逻辑
})

// 模拟订单数据
const pendingOrders = ref([
  {
    id: 'ORD20240120143',
    title: '申请升级：二级会员',
    price: 20,
    reviewer: '悦悦',
    phone: '15813545885',
    wechat: 'djdj20012',
    credit: 81,
    applyTime: '2025-01-04 23:49:0'
  }
])

const approvedOrders = ref([
  {
    id: 'ORD20240119152',
    title: 'AI绘画变现',
    price: 20,
    reviewer: '悦悦',
    phone: '15813545885',
    wechat: 'djdj20012',
    credit: 81,
    approveTime: '2024-01-19 15:20'
  }
])

// 复制文本
const copyText = (text: string) => {
  if (!checkLogin()) return

  if (text.match(/^\d+$/)) {
    // 如果是纯数字，则是电话号码，直接拨打
    window.location.href = `tel:${text}`
  } else {
    // 否则复制到剪贴板
    navigator.clipboard.writeText(text).then(() => {
      showToast('复制成功')
    }).catch(() => {
      showToast('复制失败，请手动复制')
    })
  }
}
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 