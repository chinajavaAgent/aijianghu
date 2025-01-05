<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <div class="container mx-auto px-4 py-6 sm:py-12">
      <!-- 返回按钮 -->
      <button @click="router.back()" 
        class="mb-6 flex items-center text-gray-600 hover:text-gray-800 transition-colors">
        <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
        </svg>
        返回列表
      </button>

      <!-- 项目标题 -->
      <div class="bg-white rounded-xl p-6 mb-6 shadow-lg">
        <div class="flex items-center justify-between">
          <h1 class="text-2xl sm:text-3xl font-bold text-gray-800">
            AI副业项目详情
          </h1>
        </div>
      </div>

      <!-- 项目切换标签 -->
      <div class="flex justify-center mb-6 space-x-4">
        <button 
          v-for="(tab, index) in projectTabs" 
          :key="index"
          @click="activeTab = index"
          class="flex-1 max-w-[280px] bg-white rounded-xl p-4 shadow-lg transition-all duration-300 hover:shadow-xl"
          :class="[
            activeTab === index 
              ? 'ring-2 ring-blue-500 transform scale-[1.02]' 
              : 'hover:scale-[1.01]'
          ]"
        >
          <div class="flex flex-col items-center text-center">
            <!-- 图标 -->
            <div class="w-12 h-12 mb-3 rounded-full flex items-center justify-center"
              :class="[
                activeTab === index 
                  ? 'bg-blue-100' 
                  : 'bg-gray-100'
              ]">
              <svg v-if="tab.title.includes('写作')" class="w-6 h-6" :class="[activeTab === index ? 'text-blue-600' : 'text-gray-600']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
              </svg>
              <svg v-else-if="tab.title.includes('绘画')" class="w-6 h-6" :class="[activeTab === index ? 'text-blue-600' : 'text-gray-600']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17h.01"></path>
              </svg>
              <svg v-else class="w-6 h-6" :class="[activeTab === index ? 'text-blue-600' : 'text-gray-600']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"></path>
              </svg>
            </div>
            <!-- 标题 -->
            <h3 class="font-bold mb-2" :class="[
              activeTab === index 
                ? 'text-blue-600' 
                : 'text-gray-700'
            ]">{{ tab.title }}</h3>
            <!-- 价格标签 -->
            <div class="px-3 py-1 rounded-full text-sm font-semibold"
              :class="[
                activeTab === index 
                  ? 'bg-blue-100 text-blue-600' 
                  : 'bg-gray-100 text-gray-600'
              ]"
            >￥{{ tab.price }}</div>
          </div>
        </button>
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
          <div class="bg-gradient-to-r from-blue-500 to-purple-500 rounded-t-2xl p-6 text-center relative overflow-hidden">
            <div class="absolute inset-0 bg-white opacity-10">
              <div class="absolute inset-0 bg-repeat opacity-30" style="background-image: url('data:image/svg+xml,%3Csvg width='20' height='20' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='%23ffffff' fill-opacity='1' fill-rule='evenodd'%3E%3Ccircle cx='3' cy='3' r='3'/%3E%3Ccircle cx='13' cy='13' r='3'/%3E%3C/g%3E%3C/svg%3E');"></div>
            </div>
            <h3 class="font-bold text-xl text-white relative z-10 mb-1">联系AI经纪人解锁项目</h3>
            <!-- <p class="text-white text-opacity-90 text-sm relative z-10">添加导师微信，获取完整项目资料</p> -->
          </div>

          <div class="p-6">

            <!-- <div class="bg-gradient-to-b from-gray-50 to-white rounded-xl p-4 mb-6">
              <div class="bg-white rounded-lg p-3 shadow-sm">
                <img src="https://example.com/qrcode.jpg" alt="微信二维码" class="w-48 h-48 mx-auto"/>
              </div>
              <p class="text-center mt-3 text-gray-600">联系AI经纪人解锁项目</p>
            </div> -->
            
            <!-- 联系方式列表 -->
            <div class="space-y-3">
              <div class="bg-gray-50 rounded-xl p-4 transition-all duration-300 hover:shadow-md">
                <div class="flex items-center justify-between">
                  <div class="flex items-center">
                    <div class="w-10 h-10 rounded-full bg-green-100 flex items-center justify-center mr-3">
                      <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z"></path>
                      </svg>
                    </div>
                    <div>
                      <span class="font-semibold text-gray-700">微信号</span>
                      <p class="text-gray-600 text-sm mt-0.5">ai_master888</p>
                    </div>
                  </div>
                  <button @click="copyText('ai_master888')" 
                    class="px-3 py-1.5 bg-green-50 text-green-600 rounded-lg text-sm font-medium hover:bg-green-100 transition-colors">
                    复制
                  </button>
                </div>
              </div>
              
              <div class="bg-gray-50 rounded-xl p-4 transition-all duration-300 hover:shadow-md">
                <div class="flex items-center justify-between">
                  <div class="flex items-center">
                    <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center mr-3">
                      <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path>
                      </svg>
                    </div>
                    <div>
                      <span class="font-semibold text-gray-700">手机号</span>
                      <p class="text-gray-600 text-sm mt-0.5">13800138000</p>
                    </div>
                  </div>
                  <button @click="copyText('13800138000')" 
                    class="px-3 py-1.5 bg-blue-50 text-blue-600 rounded-lg text-sm font-medium hover:bg-blue-100 transition-colors">
                    复制
                  </button>
                </div>
              </div>
            </div>

            <!-- 提示信息 -->
            <!-- <div class="mt-6 p-4 bg-blue-50 rounded-xl">
              <div class="flex items-start">
                <svg class="w-5 h-5 text-blue-600 mt-0.5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                <div class="flex-1">
                  <p class="text-blue-800 font-medium">添加时请备注</p>
                  <div class="mt-1 text-blue-600 text-sm space-y-1">
                    <p>项目：{{ currentProject.title }}</p>
                    <p>价格：￥{{ currentProject.price }}</p>
                  </div>
                </div>
              </div>
            </div> -->
          </div>

          <!-- 关闭按钮 -->
          <button @click="showContactModal = false" 
            class="absolute -top-3 -right-3 w-12 h-12 rounded-full bg-white shadow-lg hover:shadow-xl flex items-center justify-center transition-all duration-200 transform hover:scale-110">
            <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

// 视频播放相关
const showVideoModal = ref(false)
const currentVideo = ref<any>(null)

const playVideo = (video: any) => {
  currentVideo.value = video
  showVideoModal.value = true
}

// 项目数据
const projectTabs = [
  {
    title: 'AI写作变现',
    price: 10,
    introduction: 'AI写作变现是目前最容易上手的副业之一，通过ChatGPT等AI工具，你可以快速生成高质量的文案内容，在各大平台变现。',
    keyPoints: [
      '零基础快速入门AI写作',
      '掌握ChatGPT高级提示词技巧',
      '多平台内容变现方法',
      '爆款内容创作公式',
      '变现渠道推荐'
    ],
    examples: [
      {
        title: '新手小白月入过万',
        image: 'https://example.com/case1.jpg',
        description: '通过AI写作，在小红书和知乎平台月收入突破10000+',
        income: '￥10000+'
      },
      {
        title: '副业赚钱首选方案',
        image: 'https://example.com/case2.jpg',
        description: '利用AI工具，每天2小时，轻松实现月入5000+',
        income: '￥5000+'
      }
    ],
    videos: [
      {
        title: 'ChatGPT写作入门教程',
        cover: 'https://example.com/video1.jpg',
        duration: '45分钟',
        videoUrl: 'https://example.com/video1'
      },
      {
        title: '爆款内容创作技巧',
        cover: 'https://example.com/video2.jpg',
        duration: '30分钟',
        videoUrl: 'https://example.com/video2'
      }
    ],
    benefits: [
      'ChatGPT高级账号',
      '爆款文案模板100+',
      '变现平台优质账号',
      '一对一指导服务',
      '技术持续更新'
    ]
  },
  {
    title: 'AI绘画变现',
    price: 20,
    introduction: 'AI绘画是一个快速增长的市场，通过Midjourney等工具，你可以创作独特的视觉内容，为客户提供设计服务或在电商平台变现。',
    keyPoints: [
      'Midjourney基础到进阶',
      '商业图片生成技巧',
      '电商主图设计方法',
      '接单平台推荐',
      '客户开发技巧'
    ],
    examples: [
      {
        title: '电商设计月入8000+',
        image: 'https://example.com/case3.jpg',
        description: '为电商客户提供主图设计服务，月收入稳定8000+',
        income: '￥8000+'
      },
      {
        title: '插画设计兼职收入',
        image: 'https://example.com/case4.jpg',
        description: '业余时间接单创作，月额外收入3000+',
        income: '￥3000+'
      }
    ],
    videos: [
      {
        title: 'Midjourney入门教程',
        cover: 'https://example.com/video3.jpg',
        duration: '60分钟',
        videoUrl: 'https://example.com/video3'
      },
      {
        title: '商业图片创作技巧',
        cover: 'https://example.com/video4.jpg',
        duration: '45分钟',
        videoUrl: 'https://example.com/video4'
      }
    ],
    benefits: [
      'Midjourney会员账号',
      '商业提示词库500+',
      '电商图片模板',
      '客户对接渠道',
      '作品集指导'
    ]
  },
  {
    title: 'AI视频创作',
    price: 30,
    introduction: '通过AI视频创作工具，你可以快速生产短视频内容，打造爆款账号，实现广告收入和带货变现。',
    keyPoints: [
      'AI视频创作工具使用',
      '短视频内容规划',
      '爆款视频制作流程',
      '账号运营策略',
      '变现方式分析'
    ],
    examples: [
      {
        title: '抖音账号月入12000+',
        image: 'https://example.com/case5.jpg',
        description: '通过AI批量制作视频，抖音账号月收入12000+',
        income: '￥12000+'
      },
      {
        title: '视频号带货收入',
        image: 'https://example.com/case6.jpg',
        description: '视频号每月带货收入15000+',
        income: '￥15000+'
      }
    ],
    videos: [
      {
        title: 'AI视频创作教程',
        cover: 'https://example.com/video5.jpg',
        duration: '50分钟',
        videoUrl: 'https://example.com/video5'
      },
      {
        title: '短视频运营技巧',
        cover: 'https://example.com/video6.jpg',
        duration: '40分钟',
        videoUrl: 'https://example.com/video6'
      }
    ],
    benefits: [
      'AI视频制作工具',
      '爆款选题库',
      '剪辑模板素材',
      '运营指导服务',
      '流量扶持政策'
    ]
  }
]

const activeTab = ref(0)

const currentProject = computed(() => projectTabs[activeTab.value])

const showContactModal = ref(false)

const copyText = (text: string) => {
  navigator.clipboard.writeText(text).then(() => {
    alert('复制成功')
  }).catch(() => {
    alert('复制失败，请手动复制')
  })
}

const handlePurchase = () => {
  showContactModal.value = true
}

// 根据路由参数设置初始选中的项目
onMounted(() => {
  const id = Number(route.params.id)
  const index = projectTabs.findIndex(tab => tab.price === id)
  if (index !== -1) {
    activeTab.value = index
  }
})

// 添加路由激活状态的计算属性
const isActive = (path: string) => route.path === path
</script>

<style scoped>
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

/* 隐藏滚动条但保持可滚动 */
.scrollbar-hide {
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE and Edge */
}

.scrollbar-hide::-webkit-scrollbar {
  display: none; /* Chrome, Safari and Opera */
}

.router-link-active {
  color: #3B82F6; /* 蓝色，表示激活状态 */
}

.router-link-active svg {
  color: #3B82F6;
}
</style> 