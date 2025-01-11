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
  coverImage?: string
  backgroundColor?: string
  price?: number
}>(), {
  userId: 0,
  tipId: 0,
  projectId: 0,
  title: '',
  introduction: '',
  coverImage: '',
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
    const response = await getProjectPoster(props.projectId)
    if (!response.data) {
      throw new Error('获取海报数据失败')
    }

    const posterData = response.data
    
    // 设置画布大小
    canvas.width = 800
    canvas.height = 1200
    
    // 1. 绘制背景
    const bg = customBackground
    
    if (bg.type === 'solid') {
      ctx.fillStyle = bg.startColor
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    } else {
      const gradient = ctx.createLinearGradient(0, 0, 0, canvas.height)
      gradient.addColorStop(0, bg.startColor)
      gradient.addColorStop(1, bg.endColor)
      ctx.fillStyle = gradient
      ctx.fillRect(0, 0, canvas.width, canvas.height)
    }

    // 2. 绘制内容区域
    const margin = 40
    const contentWidth = canvas.width - (margin * 2)
    const contentHeight = canvas.height - (margin * 2)

    // 绘制白色背景
    ctx.fillStyle = '#FFFFFF'
    ctx.beginPath()
    ctx.roundRect(margin, margin, contentWidth, contentHeight, 20)
    ctx.fill()

    let currentY = margin + 60

    // 3. 绘制项目标题
    ctx.fillStyle = '#1a1a1a'
    ctx.font = 'bold 36px sans-serif'
    ctx.textAlign = 'left'
    ctx.fillText(posterData.title, margin + 40, currentY)
    currentY += 60

    // 4. 绘制项目描述
    if (posterData.projects[0]?.description) {
      ctx.font = '28px sans-serif'
      ctx.fillStyle = '#666666'
      const maxWidth = contentWidth - 80
      const lineHeight = 40
      
      const words = posterData.projects[0].description.split('')
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

      // 绘制每一行文字
      lines.forEach((line, index) => {
        ctx.fillText(line, margin + 40, currentY + index * lineHeight)
      })

      currentY += lines.length * lineHeight + 60
    }

    // 4.5 绘制项目详细信息
    if (posterData.projects[0]?.detail) {
      ctx.fillStyle = '#1a1a1a'
      ctx.font = 'bold 32px sans-serif'
      ctx.fillText('项目详情', margin + 40, currentY)
      currentY += 50

      // 创建临时容器处理HTML内容
      const tempDiv = document.createElement('div')
      tempDiv.innerHTML = posterData.projects[0].detail

      // 移除所有图片
      tempDiv.querySelectorAll('img').forEach(img => img.remove())

      // 处理富文本内容
      const processNode = (node: Node, level: number = 0) => {
        let lines: string[] = []
        const indent = level * 20 // 每级缩进20像素

        if (node.nodeType === Node.TEXT_NODE) {
          const text = node.textContent?.trim()
          if (text) {
            lines.push(text)
          }
        } else if (node.nodeType === Node.ELEMENT_NODE) {
          const element = node as Element
          
          // 处理标题
          if (element.tagName.match(/^H[1-6]$/)) {
            ctx.font = 'bold 30px sans-serif'
            ctx.fillStyle = '#1a1a1a'
          } 
          // 处理列表项
          else if (element.tagName === 'LI') {
            lines.push('• ')
            ctx.font = '26px sans-serif'
            ctx.fillStyle = '#666666'
          }
          // 处理段落
          else if (element.tagName === 'P') {
            ctx.font = '26px sans-serif'
            ctx.fillStyle = '#666666'
          }

          // 递归处理子节点
          Array.from(node.childNodes).forEach(child => {
            lines = lines.concat(processNode(child, element.tagName === 'LI' ? level + 1 : level))
          })

          // 段落和列表项后添加额外的空行
          if (element.tagName === 'P' || element.tagName === 'LI') {
            lines.push('')
          }
        }

        return lines
      }

      // 获取处理后的文本行
      const textLines = processNode(tempDiv)

      // 绘制文本
      ctx.font = '26px sans-serif'
      ctx.fillStyle = '#666666'
      const maxWidth = contentWidth - 80
      const lineHeight = 40

      let currentLine = ''
      let renderedLines: string[] = []

      textLines.forEach(text => {
        if (!text) {
          if (currentLine) {
            renderedLines.push(currentLine)
            currentLine = ''
          }
          renderedLines.push('')
          return
        }

        const words = text.split('')
        for (let word of words) {
          const testLine = currentLine + word
          const metrics = ctx.measureText(testLine)
          if (metrics.width > maxWidth) {
            renderedLines.push(currentLine)
            currentLine = word
          } else {
            currentLine = testLine
          }
        }
      })
      if (currentLine) {
        renderedLines.push(currentLine)
      }

      // 限制最多显示15行，超出部分用省略号表示
      if (renderedLines.length > 15) {
        renderedLines = renderedLines.slice(0, 14)
        renderedLines.push('...')
      }

      // 绘制每一行文字
      renderedLines.forEach((line, index) => {
        ctx.fillText(line, margin + 40, currentY + index * lineHeight)
      })

      currentY += renderedLines.length * lineHeight + 60
    }

    // 5. 绘制项目案例
    if (posterData.projects[0]?.cases?.length) {
      ctx.fillStyle = '#1a1a1a'
      ctx.font = 'bold 32px sans-serif'
      ctx.fillText('成功案例', margin + 40, currentY)
      currentY += 50

      ctx.font = '26px sans-serif'
      ctx.fillStyle = '#666666'
      posterData.projects[0].cases.forEach((case_: string, index: number) => {
        ctx.fillText(`${index + 1}. ${case_}`, margin + 40, currentY)
        currentY += 40
      })
      currentY += 40
    }

    // 6. 绘制项目福利
    if (posterData.projects[0]?.benefits?.length) {
      ctx.fillStyle = '#1a1a1a'
      ctx.font = 'bold 32px sans-serif'
      ctx.fillText('项目福利', margin + 40, currentY)
      currentY += 50

      ctx.font = '26px sans-serif'
      ctx.fillStyle = '#666666'
      posterData.projects[0].benefits.forEach((benefit: string, index: number) => {
        // 绘制勾选图标
        ctx.fillStyle = '#10B981'
        ctx.beginPath()
        ctx.arc(margin + 55, currentY - 8, 12, 0, Math.PI * 2)
        ctx.fill()
        
        ctx.strokeStyle = '#FFFFFF'
        ctx.lineWidth = 2
        ctx.beginPath()
        ctx.moveTo(margin + 48, currentY - 8)
        ctx.lineTo(margin + 53, currentY - 3)
        ctx.lineTo(margin + 62, currentY - 13)
        ctx.stroke()

        // 绘制福利文本
        ctx.fillStyle = '#666666'
        ctx.fillText(benefit, margin + 80, currentY)
        currentY += 40
      })
      currentY += 40
    }

    // 7. 绘制底部信息
    const bottomY = canvas.height - margin - 60
    
    // 右侧二维码
    try {
      const qrSize = 120
      const qrUrl = new URL(props.shareUrl)
      if (props.userId && props.userId !== 0) {
        try {
          const { data } = await encryptUserId(props.userId)
          if (data) {
            qrUrl.searchParams.append('shareCode', data)
          }
        } catch (error) {
          console.error('Failed to encrypt userId:', error)
          qrUrl.searchParams.append('userId', props.userId.toString())
        }
      }
      
      const qrCodeUrl = await QRCode.toDataURL(qrUrl.toString(), {
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

      const qrX = canvas.width - margin - qrSize - 40
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
      ctx.fillText(posterData.qrCodeTip || '扫码查看详情', qrX + qrSize/2, bottomY + 5)

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