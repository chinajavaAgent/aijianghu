<!-- 项目列表页面 -->
<template>
  <div class="min-h-screen font-ma-shan bg-cover bg-center bg-fixed relative bg-blend-soft-light"
    style="background-image: url('https://wechat-group-all.oss-cn-hangzhou.aliyuncs.com/image/header_back.png')">
    <!-- 添加一个浅色遮罩层 -->
    <div class="absolute inset-0 bg-white/90 backdrop-blur-[1px]"></div>

    <!-- 页面内容 -->
    <div class="container mx-auto px-4 py-8 sm:py-12 relative z-10">
      <!-- 页面标题区域 -->
      <div class="max-w-3xl mx-auto text-center mb-8">
        <h1 class="text-4xl sm:text-5xl font-bold text-[#2A3F54] font-ma-shan mb-4 drop-shadow-sm">
          {{ tipTitle }}
        </h1>
        <p class="text-lg text-[#2A3F54] font-ma-shan mb-2">
          {{ tipDescription }}
        </p>
      </div>

      <!-- 项目列表 -->
      <div class="max-w-6xl mx-auto">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div v-for="project in projects" 
               :key="project.id" 
               class="project-item bg-white rounded-xl overflow-hidden shadow-sm hover:shadow-md transition-all duration-300"
               @click="router.push(`/project/${project.id}`)">
            <div class="flex h-16">
              <!-- 左侧：项目图片 -->
              <div class="w-16 h-16 relative shrink-0 p-2">
                <div class="w-12 h-12 rounded-full overflow-hidden">
                  <img 
                    src="https://wechat-group-all.oss-cn-hangzhou.aliyuncs.com/image/header_back.png"
                    :alt="project.name"
                    class="w-full h-full object-cover"
                  />
                </div>
              </div>
              
              <!-- 右侧：项目信息 -->
              <div class="flex-1 py-2 pr-3 min-w-0">
                <!-- 项目标题 -->
                <h3 class="text-base font-bold text-[#2A3F54] truncate">{{ project.name }}</h3>
                <!-- 项目描述 -->
                <p class="text-gray-600/50 text-xs mt-1 line-clamp-2">{{ project.description }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getAiTipsDetail } from '@/api/tips'
import { showToast } from 'vant'
import type { Project } from '@/types/project'

const route = useRoute()
const router = useRouter()
const projects = ref<Project[]>([])
const tipTitle = ref('')
const tipDescription = ref('')

// 加载项目列表
const loadProjects = async () => {
  try {
    const tipId = Number(route.params.id)
    if (!tipId) {
      showToast('锦囊ID无效')
      router.push('/opportunities')
      return
    }

    const response = await getAiTipsDetail(tipId)
    if (!response.data) {
      showToast('获取锦囊信息失败')
      router.push('/opportunities')
      return
    }

    tipTitle.value = response.data.title
    tipDescription.value = response.data.description
    projects.value = response.data.projects || []
  } catch (error) {
    console.error('获取项目列表失败:', error)
    showToast('获取项目列表失败，请重试')
    router.push('/opportunities')
  }
}

onMounted(() => {
  loadProjects()
})
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.font-ma-shan {
  font-family: 'Ma Shan Zheng', cursive;
}

.project-item {
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  background: rgba(255, 255, 255, 0.95);
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow: 0 2px 8px rgba(31, 38, 135, 0.1);
  transform: translateY(0);
  transition: all 0.3s ease;
  cursor: pointer;
}

.project-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(31, 38, 135, 0.15);
  background: rgba(255, 255, 255, 1);
}

.project-item:active {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(31, 38, 135, 0.1);
}

/* 增加文字清晰度 */
h1, h2, h3, p, span, div {
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

/* 移动端优化 */
@media (max-width: 640px) {
  .project-item .h-16 {
    height: auto;
    min-height: 4rem;
  }
}
</style> 