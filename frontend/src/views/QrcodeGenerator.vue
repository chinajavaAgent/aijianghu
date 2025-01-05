<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex items-center mb-6">
      <button @click="router.back()" 
        class="mr-4 p-2 hover:bg-gray-100 rounded-lg transition-colors">
        <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
        </svg>
      </button>
      <h1 class="text-2xl font-bold">二维码生成</h1>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- 左侧编辑区域 -->
      <div class="space-y-6">
        <div class="bg-white rounded-xl shadow-sm p-6">
          <h2 class="text-lg font-semibold mb-4">基本设置</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">链接地址</label>
              <input v-model="qrcodeData.url" type="text" 
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                placeholder="请输入需要生成二维码的链接">
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Logo图片</label>
              <div class="flex items-center space-x-4">
                <div class="w-20 h-20 border-2 border-dashed rounded-lg flex items-center justify-center">
                  <img v-if="qrcodeData.logo" :src="qrcodeData.logo" class="w-full h-full object-contain">
                  <svg v-else class="w-8 h-8 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
                  </svg>
                </div>
                <button class="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 transition-colors">
                  上传Logo
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-sm p-6">
          <h2 class="text-lg font-semibold mb-4">样式设置</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">前景色</label>
              <div class="flex items-center space-x-2">
                <input type="color" v-model="qrcodeData.foreground" 
                  class="w-8 h-8 p-0 border rounded">
                <span class="text-sm text-gray-500">{{ qrcodeData.foreground }}</span>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">背景色</label>
              <div class="flex items-center space-x-2">
                <input type="color" v-model="qrcodeData.background" 
                  class="w-8 h-8 p-0 border rounded">
                <span class="text-sm text-gray-500">{{ qrcodeData.background }}</span>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">二维码尺寸</label>
              <input type="range" v-model="qrcodeData.size" min="100" max="400" step="10"
                class="w-full">
              <div class="flex justify-between text-sm text-gray-500">
                <span>100px</span>
                <span>{{ qrcodeData.size }}px</span>
                <span>400px</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧预览区域 -->
      <div class="bg-white rounded-xl shadow-sm p-6">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-lg font-semibold">预览效果</h2>
          <button @click="downloadQRCode" 
            class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
            下载二维码
          </button>
        </div>
        
        <div class="flex items-center justify-center bg-gray-50 rounded-lg p-8" style="min-height: 400px">
          <div ref="qrcodeRef"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import QRCode from 'qrcode'

const router = useRouter()
const qrcodeRef = ref<HTMLElement | null>(null)

// 二维码数据
const qrcodeData = reactive({
  url: 'https://example.com',
  logo: '',
  foreground: '#000000',
  background: '#FFFFFF',
  size: 200
})

// 生成二维码
const generateQRCode = async () => {
  if (!qrcodeRef.value) return
  
  try {
    const canvas = await QRCode.toCanvas(qrcodeData.url, {
      width: qrcodeData.size,
      margin: 1,
      color: {
        dark: qrcodeData.foreground,
        light: qrcodeData.background
      }
    })
    
    // 清除旧的内容
    qrcodeRef.value.innerHTML = ''
    // 添加新的画布
    qrcodeRef.value.appendChild(canvas)
  } catch (error) {
    console.error('Failed to generate QR code:', error)
  }
}

// 下载二维码
const downloadQRCode = () => {
  const canvas = qrcodeRef.value?.querySelector('canvas')
  if (!canvas) return
  
  const link = document.createElement('a')
  link.download = 'qrcode.png'
  link.href = canvas.toDataURL('image/png')
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

// 监听数据变化，重新生成二维码
watch(qrcodeData, generateQRCode, { deep: true })

// 初始化生成二维码
onMounted(generateQRCode)
</script> 