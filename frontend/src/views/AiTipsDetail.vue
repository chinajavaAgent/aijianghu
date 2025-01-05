<!-- AI锦囊详情页面 -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 py-8">
    <div class="container mx-auto px-4">
      <!-- 返回按钮 -->
      <div class="mb-6">
        <router-link
          to="/tips"
          class="inline-flex items-center text-gray-600 hover:text-gray-800"
        >
          <i class="fas fa-arrow-left mr-2"></i>
          返回列表
        </router-link>
      </div>

      <div v-if="tipDetail" class="bg-white rounded-xl shadow-lg overflow-hidden">
        <!-- 标题区域 -->
        <div class="p-8 bg-gradient-to-r from-cyan-500 to-fuchsia-500 text-white">
          <h1 class="text-3xl font-bold mb-4">{{ tipDetail.title }}</h1>
          <p class="text-lg opacity-90">{{ tipDetail.description }}</p>
          <div class="flex items-center mt-4 space-x-6 text-sm">
            <span class="flex items-center">
              <i class="fas fa-eye mr-2"></i>
              {{ tipDetail.views }} 次浏览
            </span>
            <span class="flex items-center">
              <i class="fas fa-heart mr-2"></i>
              {{ tipDetail.likes }} 次点赞
            </span>
            <span class="flex items-center">
              <i class="fas fa-tag mr-2"></i>
              {{ tipDetail.category }}
            </span>
          </div>
        </div>

        <!-- 内容区域 -->
        <div class="p-8">
          <!-- 等级要求提示 -->
          <div class="mb-6 p-4 bg-yellow-50 rounded-lg" v-if="!hasRequiredLevel">
            <div class="flex items-center text-yellow-700">
              <i class="fas fa-exclamation-triangle mr-2"></i>
              <span>
                需要达到 Lv.{{ tipDetail.requiredLevel }} 才能查看完整内容
              </span>
            </div>
          </div>

          <!-- 锦囊内容 -->
          <div class="prose max-w-none" v-html="formattedContent"></div>

          <!-- 操作按钮 -->
          <div class="flex justify-center mt-8 space-x-4">
            <button
              class="px-6 py-3 rounded-full flex items-center space-x-2 transition-all duration-300"
              :class="[
                isLiked
                  ? 'bg-pink-100 text-pink-500'
                  : 'bg-gray-100 text-gray-600 hover:bg-pink-50 hover:text-pink-500'
              ]"
              @click="toggleLike"
            >
              <i class="fas" :class="isLiked ? 'fa-heart' : 'fa-heart'"></i>
              <span>{{ isLiked ? '已点赞' : '点赞' }}</span>
            </button>
          </div>
        </div>
      </div>

      <!-- 加载状态 -->
      <div v-else class="text-center py-12">
        <el-skeleton :rows="10" animated />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import type { AiTips } from '@/types/tips'
import { getAiTipsDetail, likeAiTips, unlikeAiTips } from '@/api/tips'
import { marked } from 'marked'

const route = useRoute()
const userStore = useUserStore()

// 锦囊详情数据
const tipDetail = ref<AiTips | null>(null)
const isLiked = ref(false)

// 计算是否有查看权限
const hasRequiredLevel = computed(() => {
  if (!tipDetail.value) return false
  return userStore.userInfo?.level >= tipDetail.value.requiredLevel
})

// 格式化内容（支持Markdown）
const formattedContent = computed(() => {
  if (!tipDetail.value) return ''
  if (!hasRequiredLevel.value) {
    return '内容已加密，请提升等级后查看'
  }
  return marked(tipDetail.value.content)
})

// 获取锦囊详情
const fetchTipDetail = async () => {
  try {
    const response = await getAiTipsDetail(Number(route.params.id))
    tipDetail.value = response.data
  } catch (error) {
    console.error('获取锦囊详情失败:', error)
  }
}

// 切换点赞状态
const toggleLike = async () => {
  if (!tipDetail.value) return
  try {
    if (isLiked.value) {
      await unlikeAiTips(tipDetail.value.id)
      tipDetail.value.likes--
    } else {
      await likeAiTips(tipDetail.value.id)
      tipDetail.value.likes++
    }
    isLiked.value = !isLiked.value
  } catch (error) {
    console.error('操作失败:', error)
  }
}

// 页面加载时获取数据
onMounted(() => {
  fetchTipDetail()
})
</script>

<style>
.prose {
  max-width: none;
}
.prose h1,
.prose h2,
.prose h3,
.prose h4,
.prose h5,
.prose h6 {
  color: #1f2937;
  margin-top: 2em;
  margin-bottom: 1em;
}
.prose p {
  margin-top: 1.25em;
  margin-bottom: 1.25em;
}
.prose img {
  margin: 2em auto;
  border-radius: 0.5rem;
}
.prose code {
  color: #ef4444;
  background: #fee2e2;
  padding: 0.2em 0.4em;
  border-radius: 0.25em;
}
.prose pre {
  background: #1f2937;
  color: #f3f4f6;
  padding: 1em;
  border-radius: 0.5rem;
  overflow-x: auto;
}
.prose pre code {
  color: inherit;
  background: none;
  padding: 0;
}
.prose blockquote {
  border-left: 4px solid #e5e7eb;
  padding-left: 1em;
  color: #4b5563;
}
.prose ul,
.prose ol {
  padding-left: 1.5em;
}
.prose li {
  margin-top: 0.5em;
  margin-bottom: 0.5em;
}
</style> 