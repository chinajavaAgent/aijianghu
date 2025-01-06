<!-- AI锦囊详情页面 -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50">
    <!-- 加载状态 -->
    <div v-if="loading" class="fixed inset-0 bg-white bg-opacity-80 flex items-center justify-center z-50 backdrop-blur-sm">
      <div class="flex flex-col items-center">
        <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent mb-4"></div>
        <span class="text-gray-600">加载中...</span>
      </div>
    </div>

    <div v-else class="animate-fade-in">
      <!-- 顶部导航和操作区 -->
      <div class="bg-white sticky top-0 z-40 border-b border-gray-100">
        <!-- 返回按钮和标题区 -->
        <div class="max-w-7xl mx-auto px-4 sm:px-6">
          <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between py-4 gap-4">
            <div class="flex items-center gap-4 min-w-0">
              <button @click="router.back()" 
                class="flex-shrink-0 flex items-center px-3 py-2 text-gray-600 hover:text-gray-800 transition-all duration-300 rounded-lg hover:bg-gray-100">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
                </svg>
              </button>
              <h1 class="text-xl sm:text-2xl font-bold text-gray-800 truncate">{{ tipDetail?.title }}</h1>
            </div>
            <SharePoster 
              :title="tipDetail?.title || ''"
              :price="tipDetail?.price || 0"
              :introduction="tipDetail?.description || ''"
              :share-url="shareUrl"
              class="flex-shrink-0"
            />
          </div>
        </div>

        <!-- 项目切换标签页 -->
        <div class="max-w-7xl mx-auto px-4 sm:px-6">
          <div class="flex items-center -mb-px overflow-x-auto hide-scrollbar">
            <button v-for="(project, index) in tipDetail?.projects" :key="project.id"
              class="flex-shrink-0 px-6 py-4 text-base sm:text-lg whitespace-nowrap transition-all duration-300 border-b-2 focus:outline-none"
              :class="[
                currentProjectIndex === index
                  ? 'border-blue-500 text-blue-600 font-medium'
                  : 'border-transparent text-gray-600 hover:text-gray-900 hover:border-gray-300'
              ]"
              @click="selectProject(index)">
              {{ project.name }}
            </button>
          </div>
        </div>
      </div>

      <!-- 主要内容区域 -->
      <div class="max-w-7xl mx-auto px-4 sm:px-6 py-6">
        <div v-if="currentProject" class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <!-- 左侧：项目介绍和案例 -->
          <div class="lg:col-span-2 space-y-6">
            <!-- 项目介绍 -->
            <div v-if="currentProject.description" 
              class="bg-white rounded-xl p-6 shadow-sm">
              <h2 class="text-lg sm:text-xl font-bold mb-4 flex items-center text-gray-800">
                <svg class="w-6 h-6 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                    d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                项目介绍
              </h2>
              <div class="text-gray-600 text-base sm:text-lg leading-relaxed">
                <p>{{ currentProject.description }}</p>
              </div>
            </div>

            <!-- 视频教程 -->
            <div v-if="currentProject.videoUrl" 
              class="bg-white rounded-xl p-6 shadow-sm">
              <h2 class="text-lg sm:text-xl font-bold mb-4 flex items-center text-gray-800">
                <svg class="w-6 h-6 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                    d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"></path>
                </svg>
                视频教程
              </h2>
              <div class="group border border-gray-200 rounded-lg overflow-hidden cursor-pointer hover:shadow-md transition-all duration-300"
                @click="playVideo({
                  title: currentProject.name,
                  cover: '',
                  duration: '',
                  videoUrl: currentProject.videoUrl
                })">
                <div class="aspect-w-16 aspect-h-9">
                  <div class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center group-hover:bg-opacity-40 transition-all duration-300">
                    <svg class="w-16 h-16 text-white opacity-90 group-hover:opacity-100 transform group-hover:scale-110 transition-all duration-300" 
                      fill="currentColor" viewBox="0 0 20 20">
                      <path d="M10 0a10 10 0 100 20 10 10 0 000-20zm4 10.75l-6 3.5A.75.75 0 016.5 13V7a.75.75 0 011.125-.65l6 3.5a.75.75 0 010 1.3z"></path>
                    </svg>
                  </div>
                </div>
                <div class="p-4 bg-gray-50">
                  <h3 class="font-bold text-gray-800 group-hover:text-blue-600 transition-colors duration-300">
                    {{ currentProject.name }}
                  </h3>
                  <p class="mt-1 text-sm text-gray-600">点击观看完整视频教程</p>
                </div>
              </div>
            </div>

            <!-- 成功案例 -->
            <div v-if="currentProject.cases?.length" 
              class="bg-white rounded-xl p-6 shadow-sm">
              <h2 class="text-lg sm:text-xl font-bold mb-4 flex items-center text-gray-800">
                <svg class="w-6 h-6 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                    d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                成功案例
              </h2>
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div v-for="(item, index) in currentProject.cases" :key="index"
                  class="bg-gray-50 rounded-lg p-4 hover:shadow-md transition-all duration-300">
                  <img v-if="item.imageUrl" 
                    :src="item.imageUrl" 
                    :alt="item.description" 
                    class="w-full h-48 object-cover rounded-lg mb-3">
                  <p class="text-gray-600">{{ item.description }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 右侧：购买信息 -->
          <div class="lg:col-span-1">
            <div class="bg-white rounded-xl p-6 shadow-sm lg:sticky lg:top-[120px]">
              <!-- 项目福利 -->
              <div v-if="currentProject.benefits?.length" class="mb-6">
                <h2 class="text-lg sm:text-xl font-bold mb-4 text-gray-800">项目福利</h2>
                <ul class="space-y-3">
                  <li v-for="(benefit, index) in currentProject.benefits" :key="index"
                    class="flex items-start p-3 bg-gray-50 rounded-lg">
                    <svg class="w-5 h-5 text-green-500 mr-2 mt-0.5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                    </svg>
                    <span class="text-gray-600">{{ benefit.content }}</span>
                  </li>
                </ul>
              </div>

              <div class="border-t pt-6">
                <div class="flex items-center justify-between mb-4">
                  <span class="text-gray-600">项目价格</span>
                  <div class="flex items-baseline">
                    <span class="text-sm text-gray-500 mr-1">￥</span>
                    <span class="text-2xl font-bold text-red-500">{{ tipDetail?.price }}</span>
                  </div>
                </div>
                <button @click="handlePurchase"
                  class="w-full py-3 bg-blue-500 text-white rounded-lg font-medium hover:bg-blue-600 transition-colors">
                  立即购买
                </button>
                <p class="text-gray-500 text-sm text-center mt-3">
                  购买后可获得终身学习权限
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 视频播放弹窗 -->
    <div v-if="showVideoModal" 
      class="fixed inset-0 bg-black bg-opacity-75 backdrop-blur-sm flex items-center justify-center z-50 p-4">
      <div class="bg-white rounded-xl w-full max-w-4xl">
        <div class="p-4 border-b flex justify-between items-center">
          <h3 class="font-bold text-lg text-gray-800">{{ currentVideo?.title }}</h3>
          <button @click="showVideoModal = false" class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
        <div class="aspect-w-16 aspect-h-9">
          <iframe 
            :src="currentVideo?.videoUrl" 
            class="w-full h-full" 
            frameborder="0" 
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
            allowfullscreen>
          </iframe>
        </div>
      </div>
    </div>

    <!-- 联系方式弹窗 -->
    <div v-if="showContactModal" 
      class="fixed inset-0 bg-black bg-opacity-60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
      <div class="bg-white rounded-xl w-full max-w-md">
        <div class="p-6 border-b">
          <div class="flex justify-between items-center">
            <h3 class="font-bold text-xl text-gray-800">联系AI经纪人</h3>
            <button @click="showContactModal = false" class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
              <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
          </div>
          <p class="mt-2 text-gray-600">添加微信，立即解锁项目</p>
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
                    <span class="font-medium text-gray-800">手机号</span>
                    <p class="text-gray-600 text-sm mt-0.5">13800138000</p>
                  </div>
                </div>
                <a href="tel:13800138000" 
                  class="px-3 py-1.5 bg-blue-50 text-blue-600 rounded text-sm font-medium hover:bg-blue-100 transition-colors">
                  拨打电话
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
                    <p class="text-gray-600 text-sm mt-0.5">ai_master888</p>
                  </div>
                </div>
                <button @click="copyText('ai_master888')" 
                  class="px-3 py-1.5 bg-green-50 text-green-600 rounded text-sm font-medium hover:bg-green-100 transition-colors">
                  复制微信号
                </button>
              </div>
            </div>
          </div>

          <button @click="submitForReview" 
            class="w-full mt-6 py-3 bg-blue-500 text-white rounded-lg font-medium hover:bg-blue-600 transition-colors">
            提交审核
          </button>
          <p class="text-gray-500 text-xs text-center mt-3">
            提交审核后，我们将尽快处理您的申请
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
import SharePoster from '@/components/SharePoster.vue'
import type { Project } from '@/types/project'
import type { AiTips } from '@/types/tips'
import { getAiTipsDetail } from '@/api/tips'

const router = useRouter()
const route = useRoute()
const tipDetail = ref<AiTips | null>(null)
const showVideoModal = ref(false)
const showContactModal = ref(false)
const currentVideo = ref<any>(null)
const currentProjectIndex = ref(0)
const loading = ref(false)

const currentProject = computed(() => tipDetail.value?.projects?.[currentProjectIndex.value])
const shareUrl = computed(() => window.location.href)

// 获取锦囊详情
const fetchTipDetail = async () => {
  loading.value = true
  try {
    const tipId = Number(route.params.id)
    if (!tipId) {
      ElMessage.error('锦囊ID不能为空')
      router.back()
      return
    }

    const response = await getAiTipsDetail(tipId)
    if (!response.data) {
      ElMessage.error('锦囊不存在')
      router.back()
      return
    }

    tipDetail.value = response.data
    // 如果有项目，默认选中第一个
    if (response.data.projects && response.data.projects.length > 0) {
      currentProjectIndex.value = 0
    }
  } catch (error) {
    console.error('获取锦囊详情失败:', error)
    ElMessage.error('获取锦囊详情失败')
    router.back()
  } finally {
    loading.value = false
  }
}

// 切换项目
const selectProject = (index: number) => {
  currentProjectIndex.value = index
}

// 播放视频
const playVideo = (videoInfo: any) => {
  currentVideo.value = videoInfo
  showVideoModal.value = true
}

// 处理购买
const handlePurchase = () => {
  showContactModal.value = true
}

// 复制文本
const copyText = async (text: string) => {
  try {
    await navigator.clipboard.writeText(text)
    ElMessage.success('复制成功')
  } catch (error) {
    console.error('复制失败:', error)
    ElMessage.error('复制失败')
  }
}

// 提交审核
const submitForReview = () => {
  ElMessage.success('提交成功，我们将尽快处理您的申请')
  showContactModal.value = false
}

// 组件挂载时获取数据
onMounted(() => {
  fetchTipDetail()
})
</script>

<style scoped>
.hide-scrollbar {
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}

.hide-scrollbar::-webkit-scrollbar {
  display: none;  /* Chrome, Safari and Opera */
}

.aspect-w-16 {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 */
}

.aspect-w-16 > * {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in {
  animation: fadeIn 0.5s ease-out forwards;
}
</style> 