<!-- 大机缘页面 -->
<template>
  <div class="min-h-screen bg-fixed bg-cover bg-center" 
       style="background-image: url('https://wechat-group-all.oss-cn-hangzhou.aliyuncs.com/image/header_back.png');">
    <!-- 整体背景遮罩 -->
    <div class="min-h-screen bg-gradient-to-b from-black/30 via-white/80 to-white/90">
      <!-- 顶部导航栏 -->
      <div class="bg-white/80 backdrop-blur-sm shadow-sm sticky top-0 z-10">
        <div class="max-w-screen-md mx-auto px-4">
          <h1 class="text-lg font-bold text-gray-800 py-3">大机缘</h1>
        </div>
      </div>

      <div class="max-w-screen-md mx-auto px-4 py-4">
        <!-- 标签页导航 -->
        <div class="bg-white rounded-lg shadow-sm mb-4 overflow-hidden">
          <div class="flex border-b">
            <button 
              v-for="tab in tabs" 
              :key="tab.value"
              @click="activeTab = tab.value"
              class="flex-1 py-2.5 text-center text-sm font-medium transition-colors duration-200 relative"
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
          <div class="p-3">
            <div v-if="activeTab === 'pending'" class="space-y-3">
              <!-- 待审核订单 -->
              <div 
                v-for="order in pendingOrders" 
                :key="order.id" 
                class="bg-white rounded-lg p-4 shadow-sm transition-all duration-200 hover:shadow border border-gray-100"
                @click="goToTipDetail(order.tipsId)"
              >
                <!-- 标题和状态 -->
                <div class="flex justify-between items-center mb-3 gap-3">
                  <h3 class="font-medium text-gray-800 text-base truncate">{{ order.title }}</h3>
                  <span class="px-2 py-1 bg-yellow-50 text-yellow-600 rounded-full text-xs font-medium whitespace-nowrap">等待掌门审核</span>
                </div>

                <!-- 用户基本信息 -->
                <div class="flex flex-wrap gap-y-2 mb-3">
                  <div class="flex items-center text-sm basis-1/2">
                    <span class="text-gray-500">求道者：</span>
                    <span class="text-gray-800 ml-1">{{ order.userName }}</span>
                  </div>
                </div>

                <!-- 联系方式 -->
                <div class="space-y-2 mb-3">
                  <div class="flex items-center justify-between">
                    <div class="flex items-center text-sm">
                      <span class="text-gray-500">传信飞鸽：</span>
                      <span class="text-gray-800 ml-1">{{ order.userPhone }}</span>
                    </div>
                    <button 
                      class="px-2.5 py-1 bg-blue-50 text-blue-600 rounded text-xs font-medium active:bg-blue-100"
                      @click.stop="copyText(order.userPhone)"
                    >
                      一键传信
                    </button>
                  </div>
                  <div class="flex items-center justify-between">
                    <div class="flex items-center text-sm">
                      <span class="text-gray-500">江湖微信：</span>
                      <span class="text-gray-800 ml-1">{{ order.userWechat }}</span>
                    </div>
                    <button 
                      class="px-2.5 py-1 bg-green-50 text-green-600 rounded text-xs font-medium active:bg-green-100"
                      @click.stop="copyText(order.userWechat)"
                    >
                      点击复制
                    </button>
                  </div>
                </div>

                <!-- 底部信息 -->
                <div class="border-t border-gray-100 pt-3 mt-3">
                  <div class="flex items-center justify-between mb-3">
                    <div class="flex items-baseline gap-1">
                      <span class="text-gray-500 text-xs">入门贡献</span>
                      <span class="text-lg font-bold text-orange-600">￥{{ order.price }}</span>
                    </div>
                    <div class="text-right">
                      <div class="text-xs text-gray-400">申请时间</div>
                      <div class="text-xs text-gray-600">{{ formatTime(order.applyTime) }}</div>
                    </div>
                  </div>
                  
                  <!-- 操作按钮 -->
                  <div class="flex gap-2">
                    <button 
                      @click.stop="handleApprove(order.id)" 
                      class="flex-1 py-2 bg-green-50 text-green-600 rounded text-sm font-medium active:bg-green-100"
                    >
                      准许入门
                    </button>
                    <button 
                      @click.stop="handleReject(order.id)"
                      class="flex-1 py-2 bg-red-50 text-red-600 rounded text-sm font-medium active:bg-red-100"
                    >
                      婉拒
                    </button>
                  </div>
                </div>
              </div>

              <!-- 无订单提示 -->
              <div v-if="pendingOrders.length === 0" class="text-center py-10">
                <div class="w-16 h-16 mx-auto mb-3">
                  <svg class="w-full h-full text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                  </svg>
                </div>
                <p class="text-gray-500 text-sm mb-1">暂无大机缘</p>
                <p class="text-xs text-gray-400">江湖平静，静待机缘</p>
              </div>
            </div>

            <div v-else class="space-y-3">
              <!-- 已审核订单 -->
              <div 
                v-for="order in approvedOrders" 
                :key="order.id" 
                class="bg-white rounded-lg p-4 shadow-sm transition-all duration-200 hover:shadow border border-gray-100"
                @click="goToTipDetail(order.tipsId)"
              >
                <!-- 标题和状态 -->
                <div class="flex justify-between items-center mb-3 gap-3">
                  <h3 class="font-medium text-gray-800 text-base truncate">{{ order.title }}</h3>
                  <span class="px-2 py-1 bg-green-50 text-green-600 rounded-full text-xs font-medium whitespace-nowrap">掌门已准许</span>
                </div>

                <!-- 用户基本信息 -->
                <div class="flex flex-wrap gap-y-2 mb-3">
                  <div class="flex items-center text-sm basis-1/2">
                    <span class="text-gray-500">求道者：</span>
                    <span class="text-gray-800 ml-1">{{ order.userName }}</span>
                  </div>
                </div>

                <!-- 联系方式 -->
                <div class="space-y-2 mb-3">
                  <div class="flex items-center justify-between">
                    <div class="flex items-center text-sm">
                      <span class="text-gray-500">传信飞鸽：</span>
                      <span class="text-gray-800 ml-1">{{ order.userPhone }}</span>
                    </div>
                    <button 
                      class="px-2.5 py-1 bg-blue-50 text-blue-600 rounded text-xs font-medium active:bg-blue-100"
                      @click.stop="copyText(order.userPhone)"
                    >
                      一键传信
                    </button>
                  </div>
                  <div class="flex items-center justify-between">
                    <div class="flex items-center text-sm">
                      <span class="text-gray-500">江湖微信：</span>
                      <span class="text-gray-800 ml-1">{{ order.userWechat }}</span>
                    </div>
                    <button 
                      class="px-2.5 py-1 bg-green-50 text-green-600 rounded text-xs font-medium active:bg-green-100"
                      @click.stop="copyText(order.userWechat)"
                    >
                      点击复制
                    </button>
                  </div>
                </div>

                <!-- 底部信息 -->
                <div class="border-t border-gray-100 pt-3 mt-3">
                  <div class="flex items-center justify-between">
                    <div class="flex items-baseline gap-1">
                      <span class="text-gray-500 text-xs">入门贡献</span>
                      <span class="text-lg font-bold text-orange-600">￥{{ order.price }}</span>
                    </div>
                    <div class="text-right">
                      <div class="text-xs text-gray-400">准许时间</div>
                      <div class="text-xs text-gray-600">{{ formatTime(order.approveTime) }}</div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 无订单提示 -->
              <div v-if="approvedOrders.length === 0" class="text-center py-10">
                <div class="w-16 h-16 mx-auto mb-3">
                  <svg class="w-full h-full text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                  </svg>
                </div>
                <p class="text-gray-500 text-sm mb-1">暂无已通过的大机缘</p>
                <p class="text-xs text-gray-400">江湖平静，静待机缘</p>
              </div>
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

// 处理审核通过
const handleApprove = (orderId: number) => {
  showDialog({
    title: '确认准许',
    message: '确定要准许该大机缘吗？',
    showCancelButton: true,
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    confirmButtonColor: '#10B981',
    cancelButtonColor: '#6B7280',
  }).then(async (action) => {
    if (action === 'confirm') {
      try {
        await approveOrder(orderId, 1)
        showToast({
          type: 'success',
          message: '已准许入门'
        })
        loadOrders()
      } catch (error) {
        showToast({
          type: 'fail',
          message: '操作失败'
        })
      }
    }
  })
}

// 处理审核拒绝
const handleReject = (orderId: number) => {
  showDialog({
    title: '确认婉拒',
    message: '确定要婉拒该大机缘吗？',
    showCancelButton: true,
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    confirmButtonColor: '#EF4444',
    cancelButtonColor: '#6B7280',
  }).then(async (action) => {
    if (action === 'confirm') {
      try {
        await approveOrder(orderId, 2)
        showToast({
          type: 'success',
          message: '已婉拒'
        })
        loadOrders()
      } catch (error) {
        showToast({
          type: 'fail',
          message: '操作失败'
        })
      }
    }
  })
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.max-w-screen-md {
  max-width: 640px;
}

/* 统一字体设置 */
.font-ma-shan {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 所有文字都使用毛笔字体 */
h1, h2, h3, p, button, div, span {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 优化字间距 */
.tracking-wider {
  letter-spacing: 0.05em;
}

/* 数字使用正常字体权重 */
.text-lg.font-bold {
  font-weight: normal;
}
</style> 