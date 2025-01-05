<template>
  <div class="qr-generator">
    <div class="header flex items-center mb-6">
      <button class="mr-4" @click="router.back()">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
        </svg>
      </button>
      <h1 class="text-xl font-bold">二维码生成</h1>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- 左侧编辑区域 -->
      <div class="edit-section space-y-6">
        <div class="bg-white rounded-lg p-6 shadow-sm">
          <h2 class="text-lg font-semibold mb-4">基本信息</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">链接地址</label>
              <input v-model="qrData.url" type="text" 
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                placeholder="请输入需要生成二维码的链接">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Logo图片</label>
              <div class="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center">
                <div v-if="qrData.logo" class="mb-2">
                  <img :src="qrData.logo" alt="Logo" class="max-h-20 mx-auto">
                  <button @click="qrData.logo = ''" 
                    class="text-red-600 text-sm mt-2">删除图片</button>
                </div>
                <button v-else @click="uploadLogo" 
                  class="text-blue-600 hover:text-blue-700">
                  <svg class="w-8 h-8 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                      d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path>
                  </svg>
                  <span>点击上传Logo</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg p-6 shadow-sm">
          <h2 class="text-lg font-semibold mb-4">样式设置</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">二维码颜色</label>
              <div class="flex space-x-2">
                <button v-for="color in qrColors" :key="color"
                  @click="qrData.color = color"
                  class="w-8 h-8 rounded-full border-2"
                  :class="{'border-blue-500': qrData.color === color}"
                  :style="{ backgroundColor: color }">
                </button>
              </div>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">背景颜色</label>
              <div class="flex space-x-2">
                <button v-for="color in backgroundColors" :key="color"
                  @click="qrData.backgroundColor = color"
                  class="w-8 h-8 rounded-full border-2"
                  :class="{'border-blue-500': qrData.backgroundColor === color}"
                  :style="{ backgroundColor: color }">
                </button>
              </div>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">二维码大小</label>
              <select v-model="qrData.size" 
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                <option value="small">小尺寸 (200x200)</option>
                <option value="medium">中等尺寸 (300x300)</option>
                <option value="large">大尺寸 (400x400)</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧预览区域 -->
      <div class="preview-section">
        <div class="bg-white rounded-lg p-6 shadow-sm">
          <h2 class="text-lg font-semibold mb-4">预览</h2>
          <div class="qr-preview">
            <div class="qr-code-container" :style="{ backgroundColor: qrData.backgroundColor }">
              <QrCode
                ref="qrCodeRef"
                :url="qrData.url"
                :color="qrData.color"
                :background-color="qrData.backgroundColor"
                :size="qrData.size"
                :logo="qrData.logo"
              />
            </div>
          </div>
          <div class="mt-4 flex justify-end space-x-4">
            <button @click="downloadQR" 
              class="px-4 py-2 border border-blue-600 text-blue-600 rounded-lg hover:bg-blue-50">
              下载二维码
            </button>
            <button @click="generateQR" 
              class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
              生成二维码
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
import QrCode from '@/components/QrCode.vue'

const router = useRouter()
const qrCodeRef = ref()

const qrData = ref({
  url: '',
  logo: '',
  color: '#000000',
  backgroundColor: '#FFFFFF',
  size: 'medium' as 'small' | 'medium' | 'large'
})

const qrColors = [
  '#000000',
  '#3B82F6',
  '#10B981',
  '#F59E0B',
  '#EF4444'
]

const backgroundColors = [
  '#FFFFFF',
  '#F3F4F6',
  '#FEE2E2',
  '#E0E7FF',
  '#ECFDF5'
]

const uploadLogo = () => {
  // TODO: 实现Logo上传功能
  console.log('Upload logo')
}

const generateQR = () => {
  if (!qrData.value.url) {
    alert('请输入链接地址')
    return
  }
  // 触发二维码重新生成
  qrCodeRef.value?.generateQR()
}

const downloadQR = () => {
  qrCodeRef.value?.download()
}
</script>

<style scoped>
.qr-generator {
  padding: 20px;
  min-height: 100vh;
  background-color: #F9FAFB;
}

.qr-preview {
  @apply border rounded-lg p-4 bg-gray-50;
}

.qr-code-container {
  @apply p-8 rounded-lg flex items-center justify-center;
}

@media (min-width: 768px) {
  .preview-section {
    position: sticky;
    top: 20px;
  }
}
</style> 