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
          拜师学艺
        </button>
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

        <div class="p-6">
          <div class="space-y-4">
            <!-- 手机号 -->
            <div class="bg-gray-50 rounded-lg p-4">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center mr-3">
                    <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path>
                    </svg>
                  </div>
                  <div>
                    <span class="font-medium text-gray-800">传音电话</span>
                    <p class="text-gray-600 text-sm mt-0.5">{{ adminInfo?.phone || '暂无留符' }}</p>
                  </div>
                </div>
                <a :href="'tel:' + adminInfo?.phone" v-if="adminInfo?.phone"
                  class="px-3 py-1.5 bg-blue-50 text-blue-600 rounded text-sm font-medium hover:bg-blue-100 transition-colors">
                  传音入密
                </a>
              </div>
            </div>

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
          </div>

          <button @click="submitForReview" 
            class="w-full mt-6 py-3 bg-blue-500 text-white rounded-lg font-medium hover:bg-blue-600 transition-colors">
            拜师求道
          </button>
          <p class="text-gray-500 text-xs text-center mt-3">
            拜师信已递出，高人必有回应
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { showToast } from 'vant'
import type { Project } from '@/types/project'
import { getProjectById } from '@/api/project'
import { getAdminByTipId } from '@/api/admin'
import { createOrder } from '@/api/order'
import { useUserStore } from '@/store/user'
import SharePoster from '@/components/SharePoster.vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const currentProject = ref<Project | null>(null)
const showContactModal = ref(false)
const adminInfo = ref<any>(null)

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

// 处理拜师学艺点击
const handleLearn = async () => {
  try {
    const projectId = Number(route.params.id)
    const response = await getAdminByTipId(projectId)
    console.log('API Response:', response)
    
    if (response.code === 200) {
      if (response.data === null) {
        showToast({
          message: '暂无教导者，待有缘人出山',
          duration: 2000,
        })
        return
      }
      
      // 如果存在推荐人信息，使用推荐人的联系方式
      if (response.data.referrerUser) {
        adminInfo.value = {
          ...response.data,
          phone: response.data.referrerUser.phone,
          wechatId: response.data.referrerUser.wechat
        }
      } else {
        // 否则使用原始管理员信息
        adminInfo.value = response.data
      }
      showContactModal.value = true
    } else {
      showToast({
        message: '获取信息失败，请重试',
        duration: 2000,
      })
    }
  } catch (error) {
    console.error('获取管理员信息失败:', error)
    showToast({
      message: '暂时无法联系教导者',
      duration: 2000,
    })
  }
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
    if (!currentProject.value?.id || !adminInfo.value?.id) {
      showToast({
        message: '信息不完整，请重试',
        duration: 2000,
      })
      return
    }

    const response = await createOrder({
      userId: userStore.id,
      tipsId: currentProject.value.id,
      adminId: adminInfo.value.id,
      title: currentProject.value.name,
      price: currentProject.value.price.toString()
    })

    if (response.data) {
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