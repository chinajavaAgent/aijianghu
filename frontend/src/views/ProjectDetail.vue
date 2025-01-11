<template>
  <div class="min-h-screen bg-white font-ma-shan">
    <!-- 顶部导航栏 -->
    <div class="sticky top-0 z-10 bg-white border-b border-gray-100">
      <div class="max-w-[960px] mx-auto px-4 flex items-center justify-between h-[44px]">
        <button @click="router.back()" class="flex items-center text-gray-600">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
          </svg>
        </button>
        <SharePoster 
          :projectId="Number(route.params.id)"
          :share-url="shareUrl"
          :title="currentProject?.name"
          :introduction="currentProject?.description"
          :detail="currentProject?.detail"
          class="flex-shrink-0"
        />
      </div>
    </div>

    <!-- 文章内容区域 -->
    <div class="max-w-[960px] mx-auto px-[16px] py-[20px]">
      <!-- 文章标题 -->
      <h1 class="text-[22px] font-bold text-[#1a1a1a] leading-[1.4] mb-[14px] font-ma-shan">
        {{ currentProject?.name }}
      </h1>

      <!-- 文章信息 -->
      <div class="flex items-center text-[14px] text-[#8590a6] mb-[24px] font-ma-shan">
        <span>{{ formatDate(currentProject?.createdAt) }}</span>
      </div>

      <!-- 文章正文 -->
      <div class="prose max-w-none font-ma-shan pb-20">
        <div v-html="currentProject?.detail"></div>
      </div>

      <!-- 拜师学艺按钮 -->
      <div class="fixed bottom-20 left-0 right-0 flex justify-center py-6 bg-gradient-to-t from-white via-white to-transparent">
        <button 
          class="learn-btn relative px-6 py-2 text-2xl text-white font-ma-shan rounded-lg transition-all duration-300"
          @click="handleLearn"
        >
          {{ buttonText }}
        </button>
      </div>
    </div>
  </div>

  <!-- 联系方式弹窗 -->
  <div v-if="showContactModal" 
    class="fixed inset-0 bg-black bg-opacity-60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-xl w-full max-w-md">
      <div class="p-6 border-b">
        <div class="flex justify-between items-center">
          <h3 class="font-bold text-xl text-gray-800">寻访高人</h3>
          <button @click="showContactModal = false" class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
        <p class="mt-2 text-gray-600">速速添加高人微信，共商武学大计</p>
      </div>

      <div class="p-6 space-y-4">
        <!-- 微信号 -->
        <div class="bg-gray-50 rounded-lg p-4">
          <div class="flex items-center justify-between">
            <div class="flex items-center">
              <div class="w-10 h-10 rounded-full bg-green-100 flex items-center justify-center mr-3">
                <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                    d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z">
                  </path>
                </svg>
              </div>
              <div>
                <span class="font-medium text-gray-800">微信号</span>
                <p class="text-gray-600 text-sm mt-0.5">{{ adminInfo?.wechatId || '暂无微信' }}</p>
              </div>
            </div>
            <button @click="copyText(adminInfo?.wechatId)" v-if="adminInfo?.wechatId"
              class="px-3 py-1.5 bg-green-50 text-green-600 rounded text-sm font-medium hover:bg-green-100 transition-colors">
              联系高人
            </button>
          </div>
        </div>

        <!-- 提交按钮 -->
        <button @click="submitForReview"
          class="w-full py-3 bg-gradient-to-r from-cyan-500 to-fuchsia-500 text-white rounded-lg font-medium hover:shadow-lg transition-all duration-300">
          提交拜师请求
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { Project } from '@/types/project'
import type { AiTips } from '@/types/tips'
import type { ApiResponse } from '@/types/common'
import type { Order } from '@/types/order'
import { getProjectById } from '@/api/project'
import { createOrder, getOrdersByTipId, type OrderCreateRequest } from '@/api/order'
import { useUserStore } from '@/store/user'
import SharePoster from '@/components/SharePoster.vue'
import { getAiTipsDetail } from '@/api/tips'
import { getAdminByTipId } from '@/api/admin'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const currentProject = ref<Project | null>(null)
const currentTip = ref<AiTips | null>(null)
const showContactModal = ref(false)
const loading = ref(false)
const adminInfo = ref<any>(null)
const hasAppliedOrder = ref(false)

// 显示提示信息的函数
const showToast = (options: { message: string; duration?: number }) => {
  ElMessage({
    message: options.message,
    duration: options.duration || 2000,
    type: 'info'
  })
}

// 分享链接
const shareUrl = computed(() => {
  return `${window.location.origin}/project/${route.params.id}`
})

// 格式化日期
const formatDate = (date: string | undefined) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}年${d.getMonth() + 1}月${d.getDate()}日`
}

// 获取锦囊详情和管理员信息
const fetchTipDetailAndAdmin = async (tipId: number) => {
  try {
    const response = await getAiTipsDetail(tipId)
    if (!response.data) {
      showToast({
        message: '锦囊已不知去向',
        duration: 2000,
      })
      return
    }
    currentTip.value = response.data

    // 获取管理员信息
    const adminResponse = await getAdminByTipId(tipId)
    if (adminResponse.data) {
      adminInfo.value = adminResponse.data
    }
  } catch (error) {
    console.error('获取信息失败:', error)
    showToast({
      message: '获取信息失败，请重试',
      duration: 2000,
    })
  }
}

// 是否已解锁
const isUnlocked = computed(() => {
  if (!currentTip.value || !userStore.level) return false
  return userStore.level >= currentTip.value.requiredLevel
})

// 检查是否已申请该锦囊
const checkOrderStatus = async (tipId: number) => {
  if (!userStore.id) return

  try {
    const response = await getOrdersByTipId(tipId, {
      page: 1,
      size: 100,
      status: undefined // 不传 status 参数，获取所有状态的订单
    })
    
    if (response?.records) {
      // 检查是否存在当前用户的订单
      hasAppliedOrder.value = response.records.some(order => 
        order.userId === userStore.id && 
        [0, 1].includes(order.status) // 0: 待审核, 1: 审核中
      )
    }
  } catch (error) {
    console.error('获取订单状态失败:', error)
  }
}

// 修改计算属性
const buttonText = computed(() => {
  if (isUnlocked.value) return '已解锁'
  if (hasAppliedOrder.value) return '师门考核中'
  return '拜师学艺'
})

// 处理拜师学艺点击
const handleLearn = async () => {
  if (!userStore.isLoggedIn) {
    showToast({
      message: '请先登录',
      duration: 2000,
    })
    router.push('/login')
    return
  }

  if (isUnlocked.value || hasAppliedOrder.value) {
    // 如果已解锁或已申请，跳转到订单页面
    router.push('/orders')
    return
  }

  if (!currentProject.value?.tipId) {
    showToast({
      message: '项目信息不完整',
      duration: 2000,
    })
    return
  }

  showContactModal.value = true
}

// 复制文本
const copyText = async (text: string) => {
  try {
    await navigator.clipboard.writeText(text)
    showToast({
      message: '微信号已复制',
      duration: 2000,
    })
  } catch (error) {
    console.error('复制失败:', error)
    showToast({
      message: '复制失败，请重试',
      duration: 2000,
    })
  }
}

// 提交审核
const submitForReview = async () => {
  try {
    if (!currentProject.value?.tipId || !adminInfo.value?.id || !currentTip.value?.price) {
      showToast({
        message: '信息不完整，请重试',
        duration: 2000,
      })
      return
    }

    const orderData: OrderCreateRequest & { adminId: number } = {
      tipsId: currentProject.value.tipId,
      userId: userStore.id,
      title: currentTip.value.title,
      price: currentTip.value.price,
      userName: userStore.username || '',
      gender: 1, // 默认值
      age: 20, // 默认值
      city: '',
      userPhone: userStore.phone || '',
      userWechat: '',
      profession: '',
      experience: 0,
      reason: '',
      introduction: '',
      adminId: adminInfo.value.id
    }

    const response = await createOrder(orderData)

    if (response) {
      showToast({
        message: '拜师请求已送达',
        duration: 2000,
      })
      showContactModal.value = false
    } else {
      showToast({
        message: '暂无教导者',
        duration: 2000,
      })
    }
  } catch (error) {
    console.error('提交审核失败:', error)
    showToast({
      message: '提交失败，请重试',
      duration: 2000,
    })
  }
}

// 加载项目数据
const loadProject = async () => {
  const projectId = Number(route.params.id)
  if (!projectId) {
    ElMessage.error('项目ID不能为空')
    router.back()
    return
  }

  try {
    const response = await getProjectById(projectId)
    if (response.data) {
      currentProject.value = response.data
      // 加载项目后立即获取锦囊信息
      if (response.data.tipId) {
        await fetchTipDetailAndAdmin(response.data.tipId)
        await checkOrderStatus(response.data.tipId)
      }
    } else {
      ElMessage.error('获取项目数据失败')
      router.back()
    }
  } catch (error) {
    console.error('获取项目数据失败:', error)
    ElMessage.error('获取项目数据失败')
    router.back()
  }
}

onMounted(() => {
  loadProject()
})
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.font-ma-shan {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 按钮特效 */
.learn-btn {
  background: linear-gradient(135deg, #ff4e50, #f9d423);
  text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
  box-shadow: 
    0 4px 15px rgba(0, 0, 0, 0.2),
    0 0 0 2px rgba(255, 255, 255, 0.1) inset,
    0 10px 15px -3px rgba(254, 78, 80, 0.3);
  letter-spacing: 0.2em;
  position: relative;
  overflow: hidden;
}

.learn-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    120deg,
    transparent,
    rgba(255, 255, 255, 0.3),
    transparent
  );
  transition: 0.5s;
}

.learn-btn:hover::before {
  left: 100%;
}

.learn-btn:hover {
  transform: translateY(-2px);
  box-shadow: 
    0 6px 20px rgba(0, 0, 0, 0.3),
    0 0 0 2px rgba(255, 255, 255, 0.15) inset,
    0 15px 20px -3px rgba(254, 78, 80, 0.4);
}

.learn-btn:active {
  transform: translateY(1px);
  box-shadow: 
    0 2px 10px rgba(0, 0, 0, 0.2),
    0 0 0 2px rgba(255, 255, 255, 0.1) inset,
    0 8px 12px -3px rgba(254, 78, 80, 0.3);
}

.prose {
  color: #1a1a1a;
  font-size: 16px;
  line-height: 1.75;
}
</style> 