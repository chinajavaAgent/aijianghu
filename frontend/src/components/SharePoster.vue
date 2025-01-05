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
          <div class="poster-container">
            <canvas ref="posterCanvas" class="poster-canvas"></canvas>
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
import { ref, nextTick } from 'vue'
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
  
  // 等待下一个 tick，确保 Canvas 元素已经挂载
  await nextTick()
  
  generating.value = true
  
  // 打印传入的属性
  console.log('Generating poster with props:', {
    title: props.title,
    price: props.price,
    introduction: props.introduction,
    coverImage: props.coverImage,
    shareUrl: props.shareUrl
  })

  try {
    await generatePoster()
    console.log('Poster generation completed successfully')
  } catch (error) {
    console.error('Failed to generate poster:', error)
    ElMessage.error('生成海报失败，请重试')
  } finally {
    generating.value = false
  }
}

const generatePoster = async () => {
  // 多次尝试获取 Canvas 元素
  let retries = 3
  while (!posterCanvas.value && retries > 0) {
    console.log(`Waiting for canvas element, retries left: ${retries}`)
    await new Promise(resolve => setTimeout(resolve, 100))
    retries--
  }

  if (!posterCanvas.value) {
    console.error('Canvas element not found after retries')
    throw new Error('无法获取画布元素，请重试')
  }

  const canvas = posterCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) {
    console.error('Failed to get canvas context')
    throw new Error('无法获取画布上下文，请重试')
  }

  try {
    console.log('Starting poster generation...')
    console.log('Canvas dimensions:', { width: canvas.width, height: canvas.height })
    
    // 设置画布大小
    canvas.width = 750
    canvas.height = 1334
    
    // 验证画布尺寸是否正确设置
    if (canvas.width !== 750 || canvas.height !== 1334) {
      console.error('Canvas dimensions were not set correctly')
      throw new Error('画布尺寸设置失败')
    }
    
    // 1. 绘制白色背景
    ctx.fillStyle = '#ffffff'
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    console.log('Background drawn')

    // 2. 绘制顶部渐变背景
    const gradient = ctx.createLinearGradient(0, 0, 0, 300)
    gradient.addColorStop(0, '#3B82F6')
    gradient.addColorStop(1, '#8B5CF6')
    ctx.fillStyle = gradient
    ctx.fillRect(0, 0, canvas.width, 300)
    console.log('Gradient background drawn')

    // 3. 绘制标题
    ctx.fillStyle = '#ffffff'
    ctx.font = 'bold 40px sans-serif'
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'
    const title = props.title || 'AI副业项目'
    ctx.fillText(title, canvas.width / 2, 100)
    console.log('Title drawn:', title)

    // 4. 绘制价格
    const price = typeof props.price === 'number' ? props.price.toFixed(2) : props.price
    ctx.font = 'bold 36px sans-serif'
    ctx.fillText(`￥${price}`, canvas.width / 2, 180)
    console.log('Price drawn:', price)

    let currentY = 320 // 跟踪当前绘制位置的Y坐标

    // 5. 绘制封面图片
    if (props.coverImage) {
      console.log('Loading cover image:', props.coverImage)
      try {
        const image = new Image()
        image.crossOrigin = 'anonymous'
        await new Promise<void>((resolve, reject) => {
          image.onload = () => {
            console.log('Image loaded successfully')
            resolve()
          }
          image.onerror = (e) => {
            console.error('Image load error:', e)
            reject(new Error('Failed to load image'))
          }
          // 确保 coverImage 不为 undefined
          const imageUrl = props.coverImage || ''
          // 添加时间戳防止缓存
          image.src = `${imageUrl}?t=${Date.now()}`
        })

        // 计算图片尺寸（保持16:9比例）
        const imageWidth = 670
        const imageHeight = Math.round(imageWidth * 9 / 16)
        const imageX = (canvas.width - imageWidth) / 2

        // 绘制图片
        ctx.drawImage(image, imageX, currentY, imageWidth, imageHeight)
        console.log('Cover image drawn')

        // 更新Y坐标
        currentY += imageHeight + 40
      } catch (error) {
        console.error('Failed to load or draw cover image:', error)
      }
    }

    // 6. 绘制项目介绍
    if (props.introduction) {
      ctx.fillStyle = '#333333'
      ctx.font = '28px sans-serif'
      ctx.textAlign = 'left'
      ctx.textBaseline = 'top'

      const maxWidth = 670
      const lineHeight = 42
      const padding = 40
      const words = props.introduction.split('')
      let line = ''

      console.log('Drawing introduction text')
      for (let i = 0; i < words.length; i++) {
        const testLine = line + words[i]
        const metrics = ctx.measureText(testLine)
        
        if (metrics.width > maxWidth - padding * 2) {
          ctx.fillText(line, padding, currentY)
          currentY += lineHeight
          line = words[i]
        } else {
          line = testLine
        }
      }
      if (line) {
        ctx.fillText(line, padding, currentY)
        currentY += lineHeight
      }
      console.log('Introduction text drawn')
    }

    // 7. 绘制分隔线
    currentY += 40
    ctx.strokeStyle = '#E5E7EB'
    ctx.lineWidth = 1
    ctx.beginPath()
    ctx.moveTo(40, currentY)
    ctx.lineTo(canvas.width - 40, currentY)
    ctx.stroke()
    console.log('Separator line drawn')

    // 8. 绘制二维码标题
    currentY += 40
    ctx.fillStyle = '#666666'
    ctx.font = '28px sans-serif'
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'
    ctx.fillText('扫码查看详情', canvas.width / 2, currentY)
    console.log('QR code title drawn')

    // 9. 生成并绘制二维码
    try {
      console.log('Generating QR code for URL:', props.shareUrl)
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

      currentY += 40
      const qrSize = 200
      const qrX = (canvas.width - qrSize) / 2
      
      // 绘制二维码背景
      ctx.fillStyle = '#ffffff'
      ctx.shadowColor = 'rgba(0, 0, 0, 0.1)'
      ctx.shadowBlur = 10
      ctx.fillRect(qrX - 10, currentY - 10, qrSize + 20, qrSize + 20)
      ctx.shadowColor = 'transparent'
      
      // 绘制二维码
      ctx.drawImage(qrCode, qrX, currentY, qrSize, qrSize)
      console.log('QR code drawn')
    } catch (error) {
      console.error('Failed to generate or draw QR code:', error)
    }

    console.log('Poster generation completed')
  } catch (error) {
    console.error('Poster generation failed:', error)
    ElMessage.error('生成海报失败，请重试')
    throw error
  }
}

const downloadPoster = () => {
  if (!posterCanvas.value) {
    console.error('Canvas element not found during download')
    return
  }

  try {
    console.log('Starting poster download...')
    const link = document.createElement('a')
    link.download = `${props.title}-分享海报.png`
    link.href = posterCanvas.value.toDataURL('image/png')
    link.click()
    console.log('Poster downloaded successfully')
    ElMessage.success('海报保存成功')
    visible.value = false
  } catch (error) {
    console.error('Failed to download poster:', error)
    ElMessage.error('保存海报失败')
  }
}
</script>

<style scoped>
.poster-container {
  position: relative;
  width: 100%;
  padding-bottom: 177.78%; /* 9:16 ratio */
  background-color: #f3f4f6;
  border-radius: 0.5rem;
}

.poster-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: contain;
}
</style> 