<template>
  <div class="min-h-screen bg-white">
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
      <h1 class="text-[22px] font-bold text-[#1a1a1a] leading-[1.4] mb-[14px]">
        {{ currentProject?.name }}
      </h1>

      <!-- 文章信息 -->
      <div class="flex items-center text-[14px] text-[#8590a6] mb-[24px]">
        <span>{{ formatDate(currentProject?.createdAt) }}</span>
      </div>

      <!-- 文章正文 -->
      <div class="prose max-w-none">
        <div v-html="currentProject?.detail"></div>
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

.prose {
  color: #1a1a1a;
  font-size: 16px;
  line-height: 1.75;
  font-family: 'Ma Shan Zheng', cursive;
}

.prose :deep(img) {
  max-width: 100%;
  height: auto;
  margin: 16px 0;
  border-radius: 4px;
}

.prose :deep(p) {
  margin: 1em 0;
  font-family: 'Ma Shan Zheng', cursive;
}

.prose :deep(h1),
.prose :deep(h2),
.prose :deep(h3),
.prose :deep(h4),
.prose :deep(h5),
.prose :deep(h6) {
  font-family: 'Ma Shan Zheng', cursive;
  font-weight: 600;
  line-height: 1.4;
  margin: 1.2em 0 0.8em;
}

.prose :deep(blockquote) {
  margin: 1em 0;
  padding: 0.5em 1em;
  border-left: 4px solid #e5e7eb;
  background-color: #f9fafb;
  color: #4b5563;
  font-family: 'Ma Shan Zheng', cursive;
}

.prose :deep(ul),
.prose :deep(ol) {
  margin: 1em 0;
  padding-left: 1.5em;
  font-family: 'Ma Shan Zheng', cursive;
}

.prose :deep(li) {
  margin: 0.5em 0;
  font-family: 'Ma Shan Zheng', cursive;
}

.prose :deep(pre) {
  margin: 1em 0;
  padding: 1em;
  background-color: #f9fafb;
  border-radius: 4px;
  overflow-x: auto;
}

.prose :deep(code) {
  background-color: #f3f4f6;
  padding: 0.2em 0.4em;
  border-radius: 4px;
  font-size: 0.9em;
}

.prose :deep(a) {
  color: #1890ff;
  text-decoration: none;
  font-family: 'Ma Shan Zheng', cursive;
}

.prose :deep(a:hover) {
  text-decoration: underline;
}

/* 统一字体设置 */
h1, h2, p, button, div {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 优化字间距 */
.tracking-wider {
  letter-spacing: 0.05em;
}
</style> 