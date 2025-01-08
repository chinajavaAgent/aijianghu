<!-- 大机缘页面 -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <h1 class="text-xl font-bold text-gray-800 py-4">大机缘</h1>
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
                    <span class="px-3 py-1 bg-yellow-50 text-yellow-600 rounded-full text-sm font-medium">待审核</span>
                  </div>
                  <div class="mt-3">
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">申请用户：</span>
                      <span>{{ order.userName }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">用户电话：</span>
                      <span>{{ order.userPhone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click="copyText(order.userPhone)">一键拨号</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">用户微信：</span>
                      <span>{{ order.userWechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click="copyText(order.userWechat)">点击复制</button>
                    </div>
                  </div>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">订单金额：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <div class="flex items-center gap-2">
                        <span class="text-sm text-gray-500">{{ order.applyTime }}</span>
                        <div class="flex space-x-2">
                          <button @click="handleApprove(order.id)" 
                            class="px-3 py-1 bg-green-50 text-green-600 rounded text-sm font-medium hover:bg-green-100">
                            通过
                          </button>
                          <button @click="handleReject(order.id)"
                            class="px-3 py-1 bg-red-50 text-red-600 rounded text-sm font-medium hover:bg-red-100">
                            拒绝
                          </button>
                        </div>
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
              <p class="text-sm text-gray-400">耐心等待用户提交申请吧</p>
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
                      <span class="font-medium text-gray-700">申请用户：</span>
                      <span>{{ order.userName }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">用户电话：</span>
                      <span>{{ order.userPhone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click="copyText(order.userPhone)">一键拨号</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">用户微信：</span>
                      <span>{{ order.userWechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click="copyText(order.userWechat)">点击复制</button>
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
              <p class="text-sm text-gray-400">快去处理待审核的订单吧</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { showToast } from 'vant'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store/user'
import { getUserOrders, approveOrder } from '@/api/order'

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
  if (!userStore.isLoggedIn) {
    router.push({
      path: '/login',
      query: { redirect: route.fullPath }
    })
    return false
  }
  return true
}

// 获取订单列表
const loadOrders = async () => {
  try {
    if (!userStore.id) return

    const response = await getUserOrders(userStore.id, {
      page: 1,
      size: 100,
      status: activeTab.value === 'pending' ? 0 : 1
    })

    if (response.data) {
      if (activeTab.value === 'pending') {
        pendingOrders.value = response.data.records
      } else {
        approvedOrders.value = response.data.records
      }
    }
  } catch (error) {
    console.error('获取订单列表失败:', error)
    showToast('获取订单列表失败')
  }
}

// 监听标签页切换
watch(activeTab, () => {
  loadOrders()
})

// 在组件挂载时检查登录状态并获取用户信息
onMounted(async () => {
  if (!checkLogin()) return
  loadOrders()
})

// 订单数据
interface OrderItem {
  id: number
  title: string
  userName: string
  userPhone: string
  userWechat: string
  price: number
  applyTime: string
  approveTime?: string
  status: number
}

const pendingOrders = ref<OrderItem[]>([])
const approvedOrders = ref<OrderItem[]>([])

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

// 审核通过
const handleApprove = async (orderId: number) => {
  try {
    const response = await approveOrder(orderId, 1)
    if (response.data) {
      showToast('审核通过')
      loadOrders()
    }
  } catch (error) {
    console.error('审核失败:', error)
    showToast('审核失败，请重试')
  }
}

// 审核拒绝
const handleReject = async (orderId: number) => {
  try {
    const response = await approveOrder(orderId, 2)
    if (response.data) {
      showToast('已拒绝')
      loadOrders()
    }
  } catch (error) {
    console.error('审核失败:', error)
    showToast('审核失败，请重试')
  }
}
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 