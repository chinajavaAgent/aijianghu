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
      <div class="prose max-w-none font-ma-shan">
        <div v-html="currentProject?.detail"></div>
      </div>

      <!-- 拜师学艺按钮 -->
      <div class="flex justify-center mt-12 mb-8">
        <button 
          class="learn-btn relative px-10 py-3 text-xl text-white font-ma-shan bg-[#1a1a1a] hover:bg-[#2c2c2c] rounded-md transition-all duration-300 border-2 border-yellow-500/50"
          @click="handleLearn"
        >
          拜师学艺
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
import { getProjectById } from '@/api/project'
import SharePoster from '@/components/SharePoster.vue'

const router = useRouter()
const route = useRoute()
const currentProject = ref<Project | null>(null)

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

// 处理偷师学艺点击
const handleLearn = () => {
  ElMessage.success({
    message: '恭喜你学到了一门绝世武功！',
    duration: 2000
  })
  // TODO: 这里可以添加实际的学习/购买逻辑
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
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  letter-spacing: 0.1em;
}

.learn-btn:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  transform: translateY(-1px);
}

.learn-btn:active {
  transform: translateY(1px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.prose {
  color: #1a1a1a;
  font-size: 16px;
  line-height: 1.75;
}
</style> 