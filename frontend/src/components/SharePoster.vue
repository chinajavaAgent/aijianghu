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
import { getSharePoster } from '@/api/tips'

const props = defineProps<{
  tipId: number
  title?: string
  price?: string | number
  introduction?: string
  shareUrl: string
}>()

const visible = ref(false)
const generating = ref(false)
const posterCanvas = ref<HTMLCanvasElement | null>(null)

const showPoster = async () => {
  visible.value = true
  await nextTick()
  generating.value = true
  
  try {
    await generatePoster()
  } catch (error) {
    console.error('Failed to generate poster:', error)
    ElMessage.error('生成海报失败，请重试')
  } finally {
    generating.value = false
  }
}

const generatePoster = async () => {
  if (!posterCanvas.value) {
    throw new Error('无法获取画布元素，请重试')
  }

  const canvas = posterCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) {
    throw new Error('无法获取画布上下文，请重试')
  }

  try {
    // 获取海报数据
    const response = await getSharePoster(props.tipId)
    if (!response.data) {
      throw new Error('获取海报数据失败')
    }

    const posterData = response.data
    
    // 设置画布大小
    canvas.width = 800
    canvas.height = 800
    
    // 1. 绘制背景
    ctx.fillStyle = posterData.backgroundColor || '#FFFFFF'
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    // 2. 绘制卡片内容
    const cardMargin = 40
    const cardWidth = canvas.width - (cardMargin * 2)
    const cardHeight = canvas.height - (cardMargin * 2)

    let currentY = cardMargin + 60

    // 3. 绘制标题
    ctx.fillStyle = '#333333'
    ctx.font = 'bold 48px sans-serif'
    ctx.fillText(posterData.title || props.title || '', cardMargin + 40, currentY)

    currentY += 80

    // 4. 绘制项目介绍
    if (posterData.description) {
      ctx.font = '32px sans-serif'
      ctx.fillStyle = '#666666'
      const maxWidth = cardWidth - 80
      const lineHeight = 50
      
      // 将项目介绍文字按行分割
      const words = posterData.description.split('')
      let line = ''
      let lines = []
      
      for (let word of words) {
        const testLine = line + word
        const metrics = ctx.measureText(testLine)
        if (metrics.width > maxWidth) {
          lines.push(line)
          line = word
        } else {
          line = testLine
        }
      }
      if (line) {
        lines.push(line)
      }

      // 限制最多显示3行，超出部分用省略号表示
      if (lines.length > 3) {
        lines = lines.slice(0, 2)
        lines.push(lines[2] + '...')
      }

      // 绘制每一行文字
      lines.forEach((line, index) => {
        ctx.fillText(line, cardMargin + 40, currentY + index * lineHeight)
      })

      currentY += lines.length * lineHeight + 60
    }

    // 5. 绘制成功案例
    if (posterData.cases?.length) {
      ctx.fillStyle = '#333333'
      ctx.font = 'bold 32px sans-serif'
      ctx.fillText('成功案例：', cardMargin + 40, currentY)
      
      currentY += 40

      ctx.font = '28px sans-serif'
      ctx.fillStyle = '#666666'
      posterData.cases.forEach((item: string, index: number) => {
        ctx.fillText(item, cardMargin + 40, currentY + index * 40)
      })

      currentY += posterData.cases.length * 40 + 60
    }

    // 6. 绘制底部信息
    const bottomY = canvas.height - cardMargin - 60
    
    // 左侧品牌名
    ctx.fillStyle = '#333333'
    ctx.font = 'bold 28px sans-serif'
    ctx.textAlign = 'left'
    ctx.fillText(posterData.brandName || 'AI锦囊', cardMargin + 40, bottomY)

    // 右侧二维码
    try {
      const qrSize = 100
      const qrCodeUrl = await QRCode.toDataURL(props.shareUrl, {
        width: qrSize,
        margin: 0,
        color: {
          dark: '#000000',
          light: '#FFFFFF'
        }
      })

      const qrImage = new Image()
      await new Promise((resolve, reject) => {
        qrImage.onload = resolve
        qrImage.onerror = reject
        qrImage.src = qrCodeUrl
      })

      const qrX = canvas.width - cardMargin - qrSize - 40
      const qrY = bottomY - qrSize - 40
      
      // 绘制二维码背景
      ctx.fillStyle = '#F8F8F8'
      ctx.beginPath()
      ctx.roundRect(qrX - 10, qrY - 10, qrSize + 20, qrSize + 20, 10)
      ctx.fill()
      
      // 绘制二维码
      ctx.drawImage(qrImage, qrX, qrY, qrSize, qrSize)

      // 绘制扫码提示文字
      ctx.fillStyle = '#666666'
      ctx.font = '24px sans-serif'
      ctx.textAlign = 'center'
      ctx.fillText(posterData.qrCodeTip || '扫描查看详情', qrX + qrSize/2, bottomY + 5)

    } catch (error) {
      console.error('Failed to generate QR code:', error)
      throw new Error('生成二维码失败，请重试')
    }

  } catch (error) {
    console.error('Error in generatePoster:', error)
    throw error
  }
}

const downloadPoster = () => {
  if (!posterCanvas.value) return
  
  try {
    const link = document.createElement('a')
    link.download = `${props.title}-海报.png`
    link.href = posterCanvas.value.toDataURL('image/png')
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    
    ElMessage.success('海报保存成功')
  } catch (error) {
    console.error('Failed to download poster:', error)
    ElMessage.error('保存失败，请重试')
  }
}
</script>

<style scoped>
.poster-container {
  position: relative;
  width: 100%;
  padding-bottom: 100%; /* 1:1比例 */
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