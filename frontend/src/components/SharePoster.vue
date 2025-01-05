<template>
  <div>
    <!-- 分享按钮 -->
    <button @click="showPoster" 
      class="flex items-center space-x-2 px-4 py-2 bg-blue-50 text-blue-600 rounded-lg hover:bg-blue-100 transition-colors">
      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z"></path>
      </svg>
      <span>分享海报</span>
    </button>

    <!-- 海报弹窗 -->
    <div v-if="visible" class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50 p-4">
      <div class="bg-white rounded-xl p-8 w-full max-w-2xl">
        <div class="flex justify-between items-center mb-6">
          <h3 class="text-2xl font-bold">分享项目海报</h3>
          <button @click="visible = false" 
            class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-6 h-6 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>

        <!-- 海报预览区域 -->
        <div class="relative bg-gray-50 rounded-lg overflow-hidden mb-6">
          <div class="aspect-w-9 aspect-h-16">
            <canvas ref="posterCanvas" class="w-full h-full object-contain"></canvas>
          </div>
          <div v-if="generating" 
            class="absolute inset-0 flex items-center justify-center bg-white bg-opacity-80 backdrop-blur-sm">
            <div class="flex flex-col items-center">
              <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent mb-4"></div>
              <span class="text-gray-600">正在生成海报...</span>
            </div>
          </div>
        </div>

        <!-- 操作按钮 -->
        <div class="flex justify-end space-x-4">
          <button @click="visible = false" 
            class="px-6 py-3 text-gray-600 hover:bg-gray-100 rounded-lg transition-colors">
            取消
          </button>
          <button @click="downloadPoster" 
            class="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors flex items-center">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"></path>
            </svg>
            保存海报
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import QRCode from 'qrcode'

const props = defineProps<{
  title: string
  price: string | number
  introduction: string
  coverImage?: string
  shareUrl: string
}>()

const visible = ref(false)
const generating = ref(false)
const posterCanvas = ref<HTMLCanvasElement | null>(null)

const showPoster = async () => {
  visible.value = true
  generating.value = true
  await generatePoster()
  generating.value = false
}

const generatePoster = async () => {
  if (!posterCanvas.value) return

  const canvas = posterCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) return

  // 设置画布大小 (750x1334，常见手机屏幕尺寸)
  canvas.width = 750
  canvas.height = 1334

  // 绘制背景
  ctx.fillStyle = '#ffffff'
  ctx.fillRect(0, 0, canvas.width, canvas.height)

  // 绘制顶部背景装饰
  const gradient = ctx.createLinearGradient(0, 0, canvas.width, 300)
  gradient.addColorStop(0, '#3B82F6')
  gradient.addColorStop(1, '#8B5CF6')
  ctx.fillStyle = gradient
  ctx.fillRect(0, 0, canvas.width, 300)

  // 绘制装饰图案
  ctx.fillStyle = 'rgba(255, 255, 255, 0.1)'
  for (let i = 0; i < 10; i++) {
    const x = Math.random() * canvas.width
    const y = Math.random() * 300
    const size = Math.random() * 30 + 10
    ctx.beginPath()
    ctx.arc(x, y, size, 0, Math.PI * 2)
    ctx.fill()
  }

  // 绘制标题
  ctx.fillStyle = '#ffffff'
  ctx.font = 'bold 48px sans-serif'
  ctx.textAlign = 'center'
  ctx.fillText(props.title, canvas.width / 2, 160)

  // 绘制副标题
  ctx.font = '28px sans-serif'
  ctx.fillStyle = 'rgba(255, 255, 255, 0.9)'
  ctx.fillText('AI副业项目', canvas.width / 2, 220)

  // 如果有封面图，绘制封面图
  if (props.coverImage) {
    try {
      const image = new Image()
      image.crossOrigin = 'anonymous'
      await new Promise((resolve, reject) => {
        image.onload = resolve
        image.onerror = reject
        image.src = props.coverImage || ''
      })
      
      // 绘制封面图（居中显示）
      const imageWidth = 650
      const imageHeight = 366 // 16:9 比例
      const imageX = (canvas.width - imageWidth) / 2
      ctx.drawImage(image, imageX, 340, imageWidth, imageHeight)

      // 添加渐变阴影
      const shadowGradient = ctx.createLinearGradient(imageX, 340 + imageHeight - 50, imageX, 340 + imageHeight)
      shadowGradient.addColorStop(0, 'rgba(0, 0, 0, 0)')
      shadowGradient.addColorStop(1, 'rgba(0, 0, 0, 0.3)')
      ctx.fillStyle = shadowGradient
      ctx.fillRect(imageX, 340 + imageHeight - 50, imageWidth, 50)
    } catch (error) {
      console.error('加载封面图失败:', error)
    }
  }

  // 绘制简介
  ctx.fillStyle = '#374151'
  ctx.font = '32px sans-serif'
  ctx.textAlign = 'left'
  const maxWidth = 650
  const lineHeight = 48
  const startY = props.coverImage ? 750 : 340
  const words = props.introduction.split('')
  let line = ''
  let y = startY

  for (let i = 0; i < words.length; i++) {
    const testLine = line + words[i]
    const metrics = ctx.measureText(testLine)
    if (metrics.width > maxWidth && i > 0) {
      ctx.fillText(line, 50, y)
      line = words[i]
      y += lineHeight
    } else {
      line = testLine
    }
  }
  ctx.fillText(line, 50, y)

  // 绘制价格标签背景
  const priceY = y + 100
  ctx.fillStyle = '#FEF2F2'
  ctx.beginPath()
  ctx.roundRect((canvas.width - 300) / 2, priceY - 40, 300, 80, 20)
  ctx.fill()

  // 绘制价格
  ctx.fillStyle = '#EF4444'
  ctx.font = 'bold 64px sans-serif'
  ctx.textAlign = 'center'
  ctx.fillText(`￥${props.price}`, canvas.width / 2, priceY + 20)

  // 生成并绘制二维码
  try {
    const qrCodeUrl = await QRCode.toDataURL(props.shareUrl, {
      width: 200,
      margin: 1,
      color: {
        dark: '#000000',
        light: '#ffffff'
      }
    })
    
    const qrCode = new Image()
    await new Promise((resolve, reject) => {
      qrCode.onload = resolve
      qrCode.onerror = reject
      qrCode.src = qrCodeUrl
    })
    
    const qrCodeSize = 200
    const qrCodeX = (canvas.width - qrCodeSize) / 2
    const qrCodeY = priceY + 100

    // 绘制二维码背景
    ctx.fillStyle = '#ffffff'
    ctx.shadowColor = 'rgba(0, 0, 0, 0.1)'
    ctx.shadowBlur = 20
    ctx.beginPath()
    ctx.roundRect(qrCodeX - 20, qrCodeY - 20, qrCodeSize + 40, qrCodeSize + 40, 20)
    ctx.fill()
    ctx.shadowColor = 'transparent'

    // 绘制二维码
    ctx.drawImage(qrCode, qrCodeX, qrCodeY, qrCodeSize, qrCodeSize)

    // 绘制提示文字
    ctx.fillStyle = '#6B7280'
    ctx.font = '28px sans-serif'
    ctx.textAlign = 'center'
    ctx.fillText('扫码查看详情', canvas.width / 2, qrCodeY + qrCodeSize + 50)
  } catch (error) {
    console.error('生成二维码失败:', error)
  }
}

const downloadPoster = () => {
  if (!posterCanvas.value) return

  try {
    const link = document.createElement('a')
    link.download = `${props.title}-分享海报.png`
    link.href = posterCanvas.value.toDataURL('image/png')
    link.click()
    ElMessage.success('海报保存成功')
    visible.value = false
  } catch (error) {
    console.error('保存海报失败:', error)
    ElMessage.error('保存海报失败')
  }
}
</script>

<style scoped>
.aspect-w-9 {
  position: relative;
  padding-bottom: 177.78%; /* 9:16 */
}

.aspect-w-9 > * {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}
</style> 