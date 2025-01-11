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
    <div v-if="visible" class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-[9999] p-4 overflow-hidden" style="min-height: 100vh;">
      <div class="bg-white rounded-xl p-8 w-full max-w-2xl max-h-[80vh] overflow-y-auto relative transform transition-all my-auto" style="margin-bottom: 80px;">
        <div class="flex justify-between items-center mb-6">
          <h3 class="text-2xl font-bold">分享项目海报</h3>
          <button @click="visible = false" 
            class="p-2 hover:bg-gray-100 rounded-lg transition-colors">
            <svg class="w-6 h-6 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>

        <!-- 背景颜色选择器 -->
        <div class="mb-4">
          <h4 class="text-sm font-medium mb-2">选择背景样式</h4>
          <div class="flex flex-wrap gap-2">
            <!-- 预设颜色方案 -->
            <div v-for="(scheme, index) in colorSchemes" 
              :key="index"
              @click="selectColorScheme(scheme)"
              class="relative w-12 h-12 rounded-md cursor-pointer overflow-hidden hover:ring-2 hover:ring-blue-500 transition-all"
              :class="{'ring-2 ring-blue-500': isCurrentScheme(scheme)}">
              <div v-if="scheme.type === 'gradient'"
                class="w-full h-full"
                :style="{
                  background: `linear-gradient(to bottom, ${scheme.startColor}, ${scheme.endColor})`
                }">
              </div>
              <div v-else
                class="w-full h-full"
                :style="{
                  backgroundColor: scheme.startColor
                }">
              </div>
              <div class="absolute bottom-0 left-0 right-0 bg-black bg-opacity-20 text-white text-[8px] text-center py-0.5">
                {{ scheme.name }}
              </div>
            </div>
            
            <!-- 自定义颜色选择器 -->
            <div class="flex items-center gap-2">
              <select v-model="customBackground.type" 
                class="border rounded px-1 py-0.5 text-sm">
                <option value="gradient">渐变</option>
                <option value="solid">纯色</option>
              </select>
              
              <div class="flex items-center gap-1">
                <input type="color" 
                  v-model="customBackground.startColor"
                  class="w-6 h-6 p-0 border rounded">
                
                <input v-if="customBackground.type === 'gradient'" 
                  type="color" 
                  v-model="customBackground.endColor"
                  class="w-6 h-6 p-0 border rounded">
              </div>
            </div>
          </div>
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
import { ref, nextTick, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'
import QRCode from 'qrcode'
import { getProjectPoster } from '@/api/project'
import { encryptUserId } from '@/api/share'

const props = withDefaults(defineProps<{
  tipId?: number
  projectId?: number
  shareUrl: string
  userId?: number
  title?: string
  introduction?: string
  detail?: string
  backgroundColor?: string
  price?: number
}>(), {
  userId: 0,
  tipId: 0,
  projectId: 0,
  title: '',
  introduction: '',
  detail: '',
  backgroundColor: '#F5F5F5',
  price: 0
})

interface ColorScheme {
  startColor: string
  endColor?: string
  type: 'gradient' | 'solid'
  name: string
}

// 预设颜色方案
const colorSchemes: ColorScheme[] = [
  {
    startColor: '#40E0D0',
    endColor: '#4169E1',
    type: 'gradient',
    name: '默认蓝绿'
  },
  {
    startColor: '#FF6B6B',
    endColor: '#4ECDC4',
    type: 'gradient',
    name: '珊瑚红'
  },
  {
    startColor: '#A8E6CF',
    endColor: '#FFD3B6',
    type: 'gradient',
    name: '清新绿'
  },
  {
    startColor: '#3494E6',
    endColor: '#EC6EAD',
    type: 'gradient',
    name: '梦幻紫'
  },
  {
    startColor: '#11998E',
    endColor: '#38EF7D',
    type: 'gradient',
    name: '森林绿'
  },
  {
    startColor: '#FC466B',
    type: 'solid',
    name: '活力红'
  },
  {
    startColor: '#3B4371',
    type: 'solid',
    name: '深邃蓝'
  },
  {
    startColor: '#F5F5F5',
    type: 'solid',
    name: '简约白'
  }
]

interface Background {
  startColor: string
  endColor: string
  type: 'gradient' | 'solid'
}

// 当前选中的背景方案
const customBackground = reactive<Background>({
  startColor: '#40E0D0',
  endColor: '#4169E1',
  type: 'gradient'
})

const visible = ref(false)
const generating = ref(false)
const posterCanvas = ref<HTMLCanvasElement | null>(null)

// 监听颜色变化
watch(
  () => [customBackground.startColor, customBackground.endColor, customBackground.type],
  () => {
    if (visible.value) {
      generatePoster()
    }
  },
  { deep: true }
)

// 选择颜色方案
const selectColorScheme = (scheme: ColorScheme) => {
  customBackground.type = scheme.type
  customBackground.startColor = scheme.startColor
  if (scheme.type === 'gradient' && scheme.endColor) {
    customBackground.endColor = scheme.endColor
  }
  generatePoster()
}

// 检查是否是当前选中的方案
const isCurrentScheme = (scheme: ColorScheme) => {
  if (scheme.type === 'solid') {
    return customBackground.type === 'solid' && 
           customBackground.startColor === scheme.startColor
  }
  return customBackground.type === 'gradient' && 
         customBackground.startColor === scheme.startColor && 
         customBackground.endColor === scheme.endColor
}

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
  if (!posterCanvas.value) return

  const canvas = posterCanvas.value
  const ctx = canvas.getContext('2d')
  if (!ctx) return

  // 设置画布大小（增加宽度）
  canvas.width = 900
  canvas.height = 1600

  // 绘制背景
  if (customBackground.type === 'gradient') {
    const gradient = ctx.createLinearGradient(0, 0, 0, canvas.height)
    gradient.addColorStop(0, customBackground.startColor)
    gradient.addColorStop(1, customBackground.endColor)
    ctx.fillStyle = gradient
  } else {
    ctx.fillStyle = customBackground.startColor
  }
  ctx.fillRect(0, 0, canvas.width, canvas.height)

  // 绘制标题（处理换行）
  ctx.fillStyle = '#FFFFFF'
  ctx.font = 'bold 48px sans-serif'
  ctx.textAlign = 'center'
  const titleLines = wrapText(ctx, props.title || '', canvas.width - 160, 48)
  let currentY = 120
  titleLines.forEach((line, index) => {
    ctx.fillText(line, canvas.width / 2, currentY)
    currentY += 60 // 标题行间距加大
  })

  // 绘制简介
  ctx.font = '32px sans-serif'
  const introLines = wrapText(ctx, props.introduction || '', canvas.width - 160, 32)
  currentY += 40 // 标题和简介之间的间距
  introLines.forEach(line => {
    ctx.fillText(line, canvas.width / 2, currentY)
    currentY += 44
  })

  // 绘制富文本内容
  if (props.detail) {
    const detailText = stripHtmlTags(props.detail)
    const detailLines = wrapText(ctx, detailText, canvas.width - 160, 28)
    ctx.font = '28px sans-serif'
    currentY += 40 // 简介和详情之间的间距
    detailLines.slice(0, 12).forEach(line => { // 增加显示行数到12行
      ctx.fillText(line, canvas.width / 2, currentY)
      currentY += 40
    })
    if (detailLines.length > 12) {
      ctx.fillText('...', canvas.width / 2, currentY)
    }
  }

  // 绘制二维码
  try {
    const qrCodeUrl = await QRCode.toDataURL(props.shareUrl, {
      width: 240, // 增加二维码尺寸
      margin: 1,
      color: {
        dark: '#000000',
        light: '#FFFFFF'
      }
    })
    const qrImage = new Image()
    qrImage.src = qrCodeUrl
    await new Promise((resolve) => {
      qrImage.onload = resolve
    })
    const qrSize = 240
    const qrX = (canvas.width - qrSize) / 2
    const qrY = canvas.height - qrSize - 120
    ctx.drawImage(qrImage, qrX, qrY, qrSize, qrSize)

    // 绘制二维码提示文字
    ctx.fillStyle = '#FFFFFF'
    ctx.font = '32px sans-serif' // 增加字体大小
    ctx.fillText('扫码查看详情', canvas.width / 2, canvas.height - 60)
  } catch (error) {
    console.error('Failed to generate QR code:', error)
  }

  generating.value = false
}

// 文本换行处理
const wrapText = (ctx: CanvasRenderingContext2D, text: string, maxWidth: number, fontSize: number) => {
  const words = text.split('')
  const lines = []
  let currentLine = ''

  for (let i = 0; i < words.length; i++) {
    const word = words[i]
    const width = ctx.measureText(currentLine + word).width
    if (width < maxWidth) {
      currentLine += word
    } else {
      lines.push(currentLine)
      currentLine = word
    }
  }
  lines.push(currentLine)
  return lines
}

// 去除HTML标签
const stripHtmlTags = (html: string) => {
  const tmp = document.createElement('div')
  tmp.innerHTML = html
  return tmp.textContent || tmp.innerText || ''
}

const downloadPoster = () => {
  if (!posterCanvas.value) return
  
  try {
    const link = document.createElement('a')
    link.download = '项目海报.png'
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
  padding-bottom: 177.78%; /* 9:16比例 */
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