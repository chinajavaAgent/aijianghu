<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <div class="flex items-center justify-between">
          <h1 class="text-xl font-bold text-gray-800 py-4">修炼进阶</h1>
          <button
            @click="handleUpgrade"
            class="px-4 py-2 bg-gradient-to-r from-blue-500 to-blue-600 text-white rounded-lg hover:from-blue-600 hover:to-blue-700 transition-all duration-200 shadow-md hover:shadow-lg"
          >
            <span class="block text-sm">去升级</span>
          </button>
        </div>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 标签页导航 -->
      <div class="rounded-xl shadow-sm mb-6 overflow-hidden">
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
              class="bg-white rounded-xl p-4 mt-4 shadow-sm transition-shadow duration-200 hover:shadow-md cursor-pointer"
              @click="goToTipDetail(order.tipsId)"
            >
              <div class="flex items-start gap-3">
                <div class="w-12 h-12 bg-gradient-to-br from-yellow-400 to-yellow-600 rounded-lg flex items-center justify-center flex-shrink-0">
                  <img src="https://canyin-app.oss-cn-guangzhou.aliyuncs.com/images/money.png" 
                    alt="金元宝" 
                    class="w-8 h-8 object-contain drop-shadow-xl filter brightness-110"/>
                </div>
                <div class="flex-1">
                  <div class="flex justify-between items-start">
                    <h3 class="font-medium text-gray-800 text-lg">{{ order.title }}</h3>
                    <span class="px-3 py-1 bg-yellow-50 text-yellow-600 rounded-full text-sm font-medium">等待掌门审核</span>
                  </div>
                  <div class="mt-3">
                    <!-- 审核人信息 -->
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">掌门大人：</span>
                      <span class="ml-2">{{ order.reviewer || '待分配' }}</span>
                    </div>
                    <div v-if="order.reviewer" class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">掌门电话：</span>
                      <span class="ml-2">{{ order.reviewerPhone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click.stop="copyText(order.reviewerPhone)">一键传信</button>
                    </div>
                    <div v-if="order.reviewer" class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">掌门微信：</span>
                      <span>{{ order.reviewerWechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click.stop="copyText(order.reviewerWechat)">点击复制</button>
                    </div>
                  </div>
                  <div class="mt-3 pt-3 border-t border-gray-100">
                    <div class="flex justify-between items-center">
                      <span class="text-gray-600">入门贡献：
                        <span class="text-gray-800 font-medium text-lg">￥{{ order.price }}</span>
                      </span>
                      <div class="flex items-center gap-2">
                        <span class="text-xs text-gray-400">{{ formatTime(order.applyTime) }}</span>
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
              <p class="text-gray-500 mb-2">暂无修炼申请</p>
              <p class="text-sm text-gray-400">快去选择你心仪的修炼秘籍吧</p>
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
                      <span class="font-medium text-gray-700">掌门大人：</span>
                      <span>{{ order.reviewer }}</span>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">传信飞鸽：</span>
                      <span>{{ order.reviewerPhone }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-blue-50 text-blue-600 rounded" @click.stop="copyText(order.phone)">一键传信</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">江湖微信：</span>
                      <span>{{ order.reviewerWechat }}</span>
                      <button class="ml-2 px-2 py-0.5 text-xs bg-green-50 text-green-600 rounded" @click.stop="copyText(order.wechat)">点击复制</button>
                    </div>
                    <div class="flex items-center text-sm text-gray-500 mb-1">
                      <span class="font-medium text-gray-700">江湖声望：</span>
                      <div class="flex items-center">
                        <span class="text-yellow-500">★</span>
                        <span>{{ order.credit }} 点</span>
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
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
                </svg>
              </div>
              <p class="text-gray-500 mb-2">暂无已通过的修炼</p>
              <p class="text-sm text-gray-400">快去选择你心仪的修炼秘籍吧</p>
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
import { getUserOrders } from '@/api/order'

// 标签页配置
const tabs = [
  { label: '修炼中', value: 'pending' },
  { label: '已通过', value: 'approved' }
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
  tipsId: number
  title: string
  reviewer: string
  reviewerPhone: string
  reviewerWechat: string
  credit: number
  price: number
  applyTime: string
  approveTime?: string
  status: number
  // 申请人信息
  userName: string
  gender: number
  age: number
  city: string
  userPhone: string
  userWechat: string
  profession: string
  experience: number
  reason: string
  introduction: string
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

// 处理升级点击
const handleUpgrade = () => {
  router.push('/opportunities?tab=locked')
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