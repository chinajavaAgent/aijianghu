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
          :title="currentProject.title"
          :price="currentProject.price"
          :introduction="currentProject.introduction"
          :cover-image="currentProject.coverImage"
          :share-url="shareUrl"
        />
      </div>

      <!-- 项目标题 -->
      <div class="bg-white rounded-xl p-6 mb-6 shadow-lg">
        <div class="flex items-center justify-between">
          <h1 class="text-2xl sm:text-3xl font-bold text-gray-800">
            {{ currentProject.title }}
          </h1>
          <div class="text-2xl font-bold text-red-500">
            ￥{{ currentProject.price }}
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
              <p>{{ currentProject.introduction }}</p>
              <ul class="list-disc list-inside space-y-2">
                <li v-for="(point, index) in currentProject.keyPoints" :key="index">
                  {{ point }}
                </li>
              </ul>
            </div>
          </div>

          <!-- 案例展示 -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h2 class="text-xl font-bold mb-4 flex items-center">
              <svg class="w-6 h-6 mr-2 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              成功案例
            </h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div v-for="(example, index) in currentProject.examples" :key="index"
                class="border border-gray-200 rounded-lg p-4">
                <img :src="example.image" :alt="example.title" 
                  class="w-full h-48 object-cover rounded-lg mb-4"/>
                <h3 class="font-bold mb-2">{{ example.title }}</h3>
                <p class="text-gray-600 text-sm">{{ example.description }}</p>
                <div class="mt-2 text-green-600 font-bold">
                  收益：{{ example.income }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 右侧：视频和福利 -->
        <div class="space-y-6">
          <!-- 视频展示 -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h2 class="text-xl font-bold mb-4 flex items-center">
              <svg class="w-6 h-6 mr-2 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                  d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"></path>
              </svg>
              视频教程
            </h2>
            <div class="space-y-4">
              <div v-for="(video, index) in currentProject.videos" :key="index"
                class="border border-gray-200 rounded-lg overflow-hidden cursor-pointer"
                @click="playVideo(video)">
                <div class="aspect-w-16 aspect-h-9">
                  <img :src="video.cover" :alt="video.title" class="w-full h-full object-cover"/>
                  <div class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                    <svg class="w-16 h-16 text-white opacity-80" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M10 0a10 10 0 100 20 10 10 0 000-20zm4 10.75l-6 3.5A.75.75 0 016.5 13V7a.75.75 0 011.125-.65l6 3.5a.75.75 0 010 1.3z"></path>
                    </svg>
                  </div>
                </div>
                <div class="p-4">
                  <h3 class="font-bold mb-1">{{ video.title }}</h3>
                  <p class="text-gray-600 text-sm">{{ video.duration }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 福利展示 -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h2 class="text-xl font-bold mb-4 flex items-center">
              <svg class="w-6 h-6 mr-2 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                  d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"></path>
              </svg>
              项目福利
            </h2>
            <ul class="space-y-3">
              <li v-for="(benefit, index) in currentProject.benefits" :key="index"
                class="flex items-start">
                <svg class="w-5 h-5 text-green-500 mr-2 mt-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                <span class="text-gray-600">{{ benefit }}</span>
              </li>
            </ul>
          </div>

          <!-- 购买按钮 -->
          <button class="w-full py-4 rounded-xl text-white font-bold text-lg transition-colors bg-blue-600 hover:bg-blue-700"
            @click="handlePurchase">
            立即购买 ￥{{ currentProject.price }}
          </button>
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
                style="background-image: url('data:image/svg+xml,%3Csvg width='20' height='20' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='%23ffffff' fill-opacity='1' fill-rule='evenodd'%3E%3Ccircle cx='3' cy='3' r='3'/%3E%3Ccircle cx='13' cy='13' r='3'/%3E%3C/g%3E%3C/svg%3E');">
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
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import SharePoster from '@/components/SharePoster.vue'
import type { Project } from '@/types/project'

const router = useRouter()

// 当前项目数据
const currentProject = ref<Project>({
  id: 1,
  title: 'AI副业项目详情',
  price: 299,
  introduction: '这是一个基于人工智能技术的副业项目，通过AI技术帮助你轻松创收。项目包含详细的视频教程和实战案例，让你快速掌握技能。',
  coverImage: 'https://img.freepik.com/free-vector/artificial-intelligence-concept-landing-page_23-2148247345.jpg',
  keyPoints: [
    '无需编程基础，人人可学',
    '一次学习，终身受用',
    '实时技术支持，确保学习效果',
    '丰富的实战案例，快速上手'
  ],
  examples: [
    {
      title: '案例一：AI写作助手',
      image: 'https://img.freepik.com/free-vector/ai-powered-content-writing-concept_23-2149387017.jpg',
      description: '通过AI技术，帮助客户快速生成高质量文章',
      income: '￥5000/月'
    },
    {
      title: '案例二：AI绘画设计',
      image: 'https://img.freepik.com/free-vector/artificial-intelligence-concept-illustration_114360-7000.jpg',
      description: '利用AI绘画技术，为客户提供设计服务',
      income: '￥8000/月'
    }
  ],
  videos: [
    {
      title: '入门指南：AI技术基础',
      cover: 'https://img.freepik.com/free-vector/online-tutorials-concept_23-2148529958.jpg',
      duration: '45分钟',
      videoUrl: 'https://example.com/video1.mp4'
    },
    {
      title: '进阶教程：项目实战',
      cover: 'https://img.freepik.com/free-vector/digital-presentation-concept-illustration_114360-8175.jpg',
      duration: '60分钟',
      videoUrl: 'https://example.com/video2.mp4'
    }
  ],
  benefits: [
    '终身学习权限',
    '一对一技术指导',
    '项目实战机会',
    '优先推荐客户资源'
  ]
})

// 生成分享链接
const shareUrl = computed(() => {
  const baseUrl = window.location.origin
  return `${baseUrl}${router.currentRoute.value.fullPath}`
})

// 视频播放相关
const showVideoModal = ref(false)
const currentVideo = ref<Project['videos'][0] | null>(null)

const playVideo = (video: Project['videos'][0]) => {
  currentVideo.value = video
  showVideoModal.value = true
}

// 联系方式相关
const showContactModal = ref(false)

const copyText = (text: string) => {
  navigator.clipboard.writeText(text)
    .then(() => {
      ElMessage.success('复制成功')
    })
    .catch(() => {
      ElMessage.error('复制失败')
    })
}

// 购买处理
const handlePurchase = () => {
  showContactModal.value = true
}

// 提交审核处理
const submitForReview = () => {
  showContactModal.value = false
  // 跳转到订单待审核页面
  router.push('/orders/pending')
}
</script>

<style scoped>
.container {
  max-width: 768px;
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