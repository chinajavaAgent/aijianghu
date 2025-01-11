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
        <span class="mx-2">·</span>
        <span class="text-blue-600">
          <i class="fas fa-crown mr-1"></i>
          需要 Lv.{{ requiredLevel }}
        </span>
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
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { storeToRefs } from 'pinia'
import type { Project } from '@/types/project'
import { getProjectById } from '@/api/project'
import { getAiTipsDetail } from '@/api/tips'
import SharePoster from '@/components/SharePoster.vue'
import { useUserStore } from '@/store/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const { token, level } = storeToRefs(userStore)
const currentProject = ref<Project | null>(null)
const requiredLevel = ref<number>(1)

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
  // 检查用户等级是否满足要求
  if (!token.value) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  if (level.value < requiredLevel.value) {
    ElMessage.warning(`需要达到 ${requiredLevel.value} 级才能学习此项目`)
    return
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
      // 获取锦囊等级信息
      const tipResponse = await getAiTipsDetail(response.data.tipId)
      if (tipResponse.data) {
        requiredLevel.value = tipResponse.data.requiredLevel
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