<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <div class="container mx-auto px-4 py-6 sm:py-12">
      <!-- 返回按钮和分享按钮 -->
      <div class="flex justify-between items-center mb-6">
        <button @click="router.back()" 
          class="flex items-center text-gray-600 hover:text-gray-800 transition-colors">
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
          </svg>
          返回列表
        </button>
        <SharePoster 
          :title="tipDetail?.title || ''"
          :price="tipDetail?.price || 0"
          :introduction="tipDetail?.description || ''"
          :share-url="shareUrl"
        />
      </div>

      <!-- 项目标签页 -->
      <div class="bg-white rounded-xl p-4 mb-6 shadow-lg">
        <div class="flex items-center space-x-4 overflow-x-auto">
          <button v-for="(project, index) in projects" :key="project.id"
            class="px-4 py-2 rounded-lg whitespace-nowrap transition-colors"
            :class="[
              currentProjectIndex === index
                ? 'bg-blue-500 text-white'
                : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
            ]"
            @click="selectProject(index)">
            {{ project.name }}
          </button>
        </div>
      </div>

      <!-- 项目标题 -->
      <div class="bg-white rounded-xl p-6 mb-6 shadow-lg">
        <div class="flex items-center justify-between">
          <h1 class="text-2xl sm:text-3xl font-bold text-gray-800">
            {{ currentProject.name }}
          </h1>
          <div class="text-2xl font-bold text-red-500">
            ￥{{ tipDetail?.price }}
          </div>
        </div>
      </div>

      <!-- 项目内容 -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- 左侧：项目介绍和案例 -->
        <div class="lg:col-span-2 space-y-6">
          <!-- 项目介绍 -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h2 class="text-xl font-bold mb-4 flex items-center">
              <svg class="w-6 h-6 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                  d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              项目介绍
            </h2>
            <div class="space-y-4 text-gray-600">
              <p>{{ currentProject.description }}</p>
            </div>
          </div>

          <!-- 视频教程 -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h2 class="text-xl font-bold mb-4 flex items-center">
              <svg class="w-6 h-6 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                  d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"></path>
              </svg>
              视频教程
            </h2>
            <div class="space-y-4">
              <div v-if="currentProject.videoUrl"
                class="border border-gray-200 rounded-lg overflow-hidden cursor-pointer"
                @click="playVideo({
                  title: currentProject.name,
                  cover: '',
                  duration: '',
                  videoUrl: currentProject.videoUrl
                })">
                <div class="aspect-w-16 aspect-h-9">
                  <div class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                    <svg class="w-16 h-16 text-white opacity-80" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M10 0a10 10 0 100 20 10 10 0 000-20zm4 10.75l-6 3.5A.75.75 0 016.5 13V7a.75.75 0 011.125-.65l6 3.5a.75.75 0 010 1.3z"></path>
                    </svg>
                  </div>
                </div>
                <div class="p-4">
                  <h3 class="font-bold mb-1">{{ currentProject.name }}</h3>
                </div>
              </div>
            </div>
          </div>

          <!-- 成功案例 -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h2 class="text-xl font-bold mb-4 flex items-center">
              <svg class="w-6 h-6 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              成功案例
            </h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div v-for="(item, index) in currentProject.cases" :key="index"
                class="bg-gray-50 rounded-lg p-4">
                <img :src="item.imageUrl" :alt="item.description" class="w-full h-48 object-cover rounded-lg mb-4">
                <p class="text-gray-600">{{ item.description }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 右侧：购买信息 -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl p-6 shadow-lg sticky top-6">
            <h2 class="text-xl font-bold mb-4">项目福利</h2>
            <ul class="space-y-3 mb-6">
              <li v-for="(benefit, index) in currentProject.benefits" :key="index"
                class="flex items-start">
                <svg class="w-5 h-5 text-green-500 mr-2 mt-0.5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                <span class="text-gray-600">{{ benefit.content }}</span>
              </li>
            </ul>

            <div class="border-t pt-6">
              <div class="flex items-center justify-between mb-4">
                <span class="text-gray-600">项目价格</span>
                <span class="text-2xl font-bold text-red-500">￥{{ tipDetail?.price }}</span>
              </div>
              <button @click="handlePurchase"
                class="w-full py-3 bg-gradient-to-r from-blue-500 to-purple-500 text-white rounded-xl font-medium hover:opacity-90 transition-opacity">
                立即购买
              </button>
              <p class="text-gray-500 text-sm text-center mt-3">
                购买后可获得终身学习权限
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 视频播放弹窗 -->
      <div v-if="showVideoModal" 
        class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-xl w-full max-w-4xl">
          <div class="p-4 border-b flex justify-between items-center">
            <h3 class="font-bold text-lg">{{ currentVideo?.title }}</h3>
            <button @click="showVideoModal = false" class="text-gray-500 hover:text-gray-700">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
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
        class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 p-4 backdrop-blur-sm">
        <div class="bg-white rounded-2xl w-full max-w-md transform transition-all duration-300 scale-100">
          <!-- 顶部背景装饰 -->
          <div class="bg-gradient-to-r from-blue-500 to-purple-500 rounded-t-2xl p-8 text-center relative overflow-hidden">
            <div class="absolute inset-0 bg-white opacity-10">
              <div class="absolute inset-0 bg-repeat opacity-30" 
                style="background-image: url('data:image/svg+xml,%3Csvg width=20 height=20 viewBox=0 0 20 20 xmlns=http://www.w3.org/2000/svg%3E%3Cg fill=%23ffffff fill-opacity=1 fill-rule=evenodd%3E%3Ccircle cx=3 cy=3 r=3/%3E%3Ccircle cx=13 cy=13 r=3/%3E%3C/g%3E%3C/svg%3E');">
              </div>
            </div>
            <button @click="showContactModal = false" 
              class="absolute right-4 top-4 text-white hover:text-white/80 transition-colors">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
            <h3 class="font-bold text-2xl text-white relative z-10 mb-2">联系AI经纪人</h3>
            <p class="text-white text-opacity-90 relative z-10">添加微信，立即解锁项目</p>
          </div>

          <div class="p-8">
            <!-- 联系方式列表 -->
            <div class="space-y-4">
              <!-- 手机号 -->
              <div class="bg-gray-50 rounded-xl p-6 transition-all duration-300 hover:shadow-md">
                <div class="flex items-center justify-between">
                  <div class="flex items-center">
                    <div class="w-12 h-12 rounded-full bg-blue-100 flex items-center justify-center mr-4">
                      <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path>
                      </svg>
                    </div>
                    <div>
                      <span class="font-semibold text-gray-700 text-lg">手机号</span>
                      <p class="text-gray-600 mt-1">13800138000</p>
                    </div>
                  </div>
                  <a href="tel:13800138000" 
                    class="px-4 py-2 bg-blue-50 text-blue-600 rounded-lg text-sm font-medium hover:bg-blue-100 transition-colors">
                    拨打电话
                  </a>
                </div>
              </div>

              <!-- 微信号 -->
              <div class="bg-gray-50 rounded-xl p-6 transition-all duration-300 hover:shadow-md">
                <div class="flex items-center justify-between">
                  <div class="flex items-center">
                    <div class="w-12 h-12 rounded-full bg-green-100 flex items-center justify-center mr-4">
                      <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                          d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z">
                        </path>
                      </svg>
                    </div>
                    <div>
                      <span class="font-semibold text-gray-700 text-lg">微信号</span>
                      <p class="text-gray-600 mt-1">ai_master888</p>
                    </div>
                  </div>
                  <button @click="copyText('ai_master888')" 
                    class="px-4 py-2 bg-green-50 text-green-600 rounded-lg text-sm font-medium hover:bg-green-100 transition-colors">
                    复制微信号
                  </button>
                </div>
              </div>
            </div>

            <!-- 提交审核按钮 -->
            <div class="mt-8">
              <button @click="submitForReview" 
                class="w-full py-3 bg-gradient-to-r from-blue-500 to-purple-500 text-white rounded-xl font-medium hover:opacity-90 transition-opacity">
                提交审核
              </button>
              <p class="text-gray-500 text-sm text-center mt-3">
                提交审核后，我们将尽快处理您的申请
              </p>
            </div>
          </div>
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
import { getProjectById } from '@/api/project'

const router = useRouter()
const route = useRoute()
const tipDetail = ref<AiTips | null>(null)
const showVideoModal = ref(false)
const showContactModal = ref(false)
const currentVideo = ref<any>(null)
const currentProjectIndex = ref(0)
const projects = ref<Project[]>([])

const currentProject = computed(() => projects.value[currentProjectIndex.value] || {})
const shareUrl = computed(() => window.location.href)

// 获取提示详情
const fetchTipDetail = async () => {
  try {
    if (currentProject.value?.tipId) {
      const response = await getAiTipsDetail(currentProject.value.tipId)
      tipDetail.value = response.data
      
      // 如果锦囊中有项目列表，更新projects
      if (response.data.projects && response.data.projects.length > 0) {
        projects.value = response.data.projects
      }
    }
  } catch (error) {
    console.error('获取提示详情失败:', error)
    ElMessage.error('获取提示详情失败')
  }
}

// 切换项目
const selectProject = (index: number) => {
  currentProjectIndex.value = index
  // 更新URL，但不重新加载页面
  const newProjectId = projects.value[index].id
  if (newProjectId) {
    router.replace(`/project/${newProjectId}`)
  }
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
onMounted(async () => {
  const projectId = Number(route.params.id)
  if (!projectId) {
    ElMessage.error('项目ID不能为空')
    return
  }

  try {
    // 获取项目详情
    const response = await getProjectById(projectId)
    if (response.data) {
      const { projects: projectList, tipId } = response.data
      if (projectList && projectList.length > 0) {
        projects.value = projectList
        // 找到当前项目的索引
        const index = projectList.findIndex(p => p.id === projectId)
        if (index !== -1) {
          currentProjectIndex.value = index
        }
      }
      // 获取锦囊详情
      await fetchTipDetail()
    }
  } catch (error) {
    console.error('获取项目数据失败:', error)
    ElMessage.error('获取项目数据失败')
  }
})
</script>

<style scoped>
.container {
  max-width: 1024px;
}

/* 添加水平滚动条样式 */
.overflow-x-auto {
  scrollbar-width: thin;
  scrollbar-color: rgba(156, 163, 175, 0.5) transparent;
}

.overflow-x-auto::-webkit-scrollbar {
  height: 6px;
}

.overflow-x-auto::-webkit-scrollbar-track {
  background: transparent;
}

.overflow-x-auto::-webkit-scrollbar-thumb {
  background-color: rgba(156, 163, 175, 0.5);
  border-radius: 3px;
}

.overflow-x-auto::-webkit-scrollbar-thumb:hover {
  background-color: rgba(156, 163, 175, 0.7);
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
</style> 