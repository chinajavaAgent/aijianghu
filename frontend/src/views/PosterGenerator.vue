<template>
  <div class="poster-generator">
    <div class="header flex items-center mb-6">
      <button class="mr-4" @click="router.back()">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
        </svg>
      </button>
      <h1 class="text-xl font-bold">海报生成</h1>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- 左侧编辑区域 -->
      <div class="edit-section space-y-6">
        <div class="bg-white rounded-lg p-6 shadow-sm">
          <h2 class="text-lg font-semibold mb-4">基本信息</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">标题</label>
              <input v-model="posterData.title" type="text" 
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                placeholder="请输入海报标题">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">项目介绍</label>
              <textarea v-model="posterData.introduction" rows="4"
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                placeholder="请输入项目介绍文字"></textarea>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">案例图片</label>
              <div class="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center">
                <div v-if="posterData.coverImage" class="mb-2">
                  <img :src="posterData.coverImage" alt="案例图片" class="max-h-40 mx-auto">
                  <button @click="posterData.coverImage = ''" 
                    class="text-red-600 text-sm mt-2">删除图片</button>
                </div>
                <button v-else @click="uploadImage" 
                  class="text-blue-600 hover:text-blue-700">
                  <svg class="w-8 h-8 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                      d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path>
                  </svg>
                  <span>点击上传图片</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg p-6 shadow-sm">
          <h2 class="text-lg font-semibold mb-4">样式设置</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">背景颜色</label>
              <div class="flex space-x-2">
                <button v-for="color in backgroundColors" :key="color"
                  @click="posterData.backgroundColor = color"
                  class="w-8 h-8 rounded-full border-2"
                  :class="{'border-blue-500': posterData.backgroundColor === color}"
                  :style="{ backgroundColor: color }">
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧预览区域 -->
      <div class="preview-section">
        <div class="bg-white rounded-lg p-6 shadow-sm">
          <h2 class="text-lg font-semibold mb-4">预览</h2>
          <div class="poster-preview">
            <SharePoster
              :tipId="posterData.tipId"
              :title="posterData.title"
              :introduction="posterData.introduction"
              :cover-image="posterData.coverImage"
              :background-color="posterData.backgroundColor"
              :share-url="shareUrl"
              :price="0"
            />
          </div>
          <div class="mt-4 flex justify-end">
            <button @click="generatePoster" 
              class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
              生成海报
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import SharePoster from '@/components/SharePoster.vue'

const router = useRouter()

const posterData = ref({
  tipId: 0,
  title: '',
  introduction: '',
  coverImage: '',
  backgroundColor: '#F3F4F6'
})

const backgroundColors = [
  '#F3F4F6',
  '#FEE2E2',
  '#E0E7FF',
  '#ECFDF5',
  '#FEF3C7'
]

const shareUrl = 'https://example.com/share'

const uploadImage = () => {
  // TODO: 实现图片上传功能
  console.log('Upload image')
}

const generatePoster = () => {
  // TODO: 实现海报生成功能
  console.log('Generate poster')
}
</script>

<style scoped>
.poster-generator {
  padding: 20px;
  min-height: 100vh;
  background-color: #F9FAFB;
}

.poster-preview {
  @apply border rounded-lg p-4 bg-gray-50;
}

@media (min-width: 768px) {
  .preview-section {
    position: sticky;
    top: 20px;
  }
}
</style> 