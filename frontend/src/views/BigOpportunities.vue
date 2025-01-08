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
              class="bg-white rounded-xl p-4 shadow-sm transition-shadow duration-200 hover:shadow-md cursor-pointer"
              @click="goToTipDetail(order.tipsId)"
            >
              <div class="flex items-start gap-3">
                <div class="w-12 h-12 bg-gradient-to-br from-purple-400 to-purple-600 rounded-lg flex items-center justify-center flex-shrink-0">
                  <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                  </svg>
                </div>
                <div class="flex-1">
                  <div class="flex justify-between items-start">
                    <h3 class="font-medium text-gray-800 text-lg">{{ order.title }}</h3>
                    <span class="px-3 py-1 bg-yellow-50 text-yellow-600 rounded-full text-sm font-medium">等待掌门审核</span>
                  </div>
                  <div class="mt-3">
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">求道者：</span>
                      <span>{{ order.userName }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">传信飞鸽：</span>
                      <span>{{ order.userPhone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click.stop="copyText(order.userPhone)">一键传信</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">江湖微信：</span>
                      <span>{{ order.userWechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click.stop="copyText(order.userWechat)">点击复制</button>
                    </div>
                  </div>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">入门贡献：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <div class="flex items-center gap-2">
                        <span class="text-xs text-gray-400">{{ formatTime(order.applyTime) }}</span>
                        <div class="flex space-x-2">
                          <button 
                            @click.stop="handleApprove(order.id)" 
                            class="px-3 py-1 bg-green-50 text-green-600 rounded text-sm font-medium hover:bg-green-100 transition-colors duration-200"
                          >
                            准许入门
                          </button>
                          <button 
                            @click.stop="handleReject(order.id)"
                            class="px-3 py-1 bg-red-50 text-red-600 rounded text-sm font-medium hover:bg-red-100 transition-colors duration-200"
                          >
                            婉拒
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
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                </svg>
              </div>
              <p class="text-gray-500 mb-2">暂无大机缘</p>
              <p class="text-sm text-gray-400">江湖平静，静待机缘</p>
            </div>
          </div>

          <div v-else class="space-y-4">
            <!-- 已审核订单 -->
            <div 
              v-for="order in approvedOrders" 
              :key="order.id" 
              class="bg-white rounded-xl p-4 shadow-sm transition-shadow duration-200 hover:shadow-md cursor-pointer"
              @click="goToTipDetail(order.tipsId)"
            >
              <div class="flex items-start gap-3">
                <div class="w-12 h-12 bg-gradient-to-br from-green-400 to-green-600 rounded-lg flex items-center justify-center flex-shrink-0">
                  <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                  </svg>
                </div>
                <div class="flex-1">
                  <div class="flex justify-between items-start">
                    <h3 class="font-medium text-gray-800 text-lg">{{ order.title }}</h3>
                    <span class="px-3 py-1 bg-green-50 text-green-600 rounded-full text-sm font-medium">掌门已准许</span>
                  </div>
                  <div class="mt-3">
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">求道者：</span>
                      <span>{{ order.userName }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">传信飞鸽：</span>
                      <span>{{ order.userPhone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click.stop="copyText(order.userPhone)">一键传信</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">江湖通号：</span>
                      <span>{{ order.userWechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click.stop="copyText(order.userWechat)">点击复制</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">求道理由：</span>
                      <span class="text-gray-600">{{ order.reason }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">江湖阅历：</span>
                      <div class="flex items-center">
                        <span class="text-yellow-500">★</span>
                        <span>{{ order.experience }} 年</span>
                      </div>
                    </div>
                  </div>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">入门贡献：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <div class="flex items-center gap-2">
                        <span class="text-xs text-gray-400">{{ formatTime(order.approveTime) }}</span>
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
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                </svg>
              </div>
              <p class="text-gray-500 mb-2">暂无已通过的大机缘</p>
              <p class="text-sm text-gray-400">江湖平静，静待机缘</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { showToast, showDialog } from 'vant'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store/user'
import { getUserOrders, approveOrder } from '@/api/order'

// 标签页配置
const tabs = [
  { label: '待审大机缘', value: 'pending' },
  { label: '已成大机缘', value: 'approved' }
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
      status: activeTab.value === 'pending' ? 0 : 1,
      isBig: true // 只获取大机缘类型的订单
    })

    if (response.data) {
      if (activeTab.value === 'pending') {
        pendingOrders.value = response.data.records
      } else {
        approvedOrders.value = response.data.records
      }
    }
  } catch (error) {
    console.error('获取大机缘列表失败:', error)
    showToast('获取大机缘列表失败')
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
  tipsId: number
  title: string
  userName: string
  gender: number // 1: 男, 2: 女
  age: number
  city: string
  userPhone: string
  userWechat: string
  profession: string
  experience: number
  reason: string
  introduction: string
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

// 跳转到锦囊详情
const goToTipDetail = (tipsId: number) => {
  router.push(`/tips/${tipsId}`)
}

// 格式化时间
const formatTime = (timeStr: string | undefined) => {
  if (!timeStr) return ''
  const date = new Date(timeStr)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hour = String(date.getHours()).padStart(2, '0')
  const minute = String(date.getMinutes()).padStart(2, '0')
  return `${year}年${month}月${day}日 ${hour}:${minute}`
}

// 审核通过
const handleApprove = async (orderId: number) => {
  try {
    const result = await showDialog({
      title: '准许入门',
      message: '确定要准许该求道者入门吗？',
      confirmButtonText: '确定',
      confirmButtonColor: '#10B981',
      cancelButtonText: '取消',
    })

    if (result === 'confirm') {
      const response = await approveOrder(orderId, 1)
      if (response.data) {
        showToast({
          message: '已准许入门',
          position: 'bottom',
        })
        loadOrders()
      }
    }
  } catch (error) {
    console.error('审核失败:', error)
    showToast({
      message: '审核失败，请重试',
      position: 'bottom',
    })
  }
}

// 审核拒绝
const handleReject = async (orderId: number) => {
  try {
    const result = await showDialog({
      title: '婉拒入门',
      message: '确定要婉拒该求道者吗？',
      confirmButtonText: '确定',
      confirmButtonColor: '#EF4444',
      cancelButtonText: '取消',
    })

    if (result === 'confirm') {
      const response = await approveOrder(orderId, 2)
      if (response.data) {
        showToast({
          message: '已婉拒',
          position: 'bottom',
        })
        loadOrders()
      }
    }
  } catch (error) {
    console.error('审核失败:', error)
    showToast({
      message: '审核失败，请重试',
      position: 'bottom',
    })
  }
}
</script>

<style scoped>
.container {
  max-width: 768px;
}

/* 添加漂浮动画 */
@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.animate-float {
  animation: float 3s ease-in-out infinite;
}
</style> 