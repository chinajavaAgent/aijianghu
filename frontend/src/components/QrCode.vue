<template>
  <div class="qr-code" ref="qrContainer"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import QRCode from 'qrcode'

const props = defineProps<{
  url: string
  color?: string
  backgroundColor?: string
  size?: 'small' | 'medium' | 'large'
  logo?: string
}>()

const qrContainer = ref<HTMLElement | null>(null)

const sizePx = {
  small: 200,
  medium: 300,
  large: 400
}

const generateQR = async () => {
  if (!qrContainer.value || !props.url) return

  try {
    const canvas = document.createElement('canvas')
    const size = sizePx[props.size || 'medium']
    
    await QRCode.toCanvas(canvas, props.url, {
      width: size,
      margin: 1,
      color: {
        dark: props.color || '#000000',
        light: props.backgroundColor || '#FFFFFF'
      }
    })

    // 如果有Logo，添加到二维码中心
    if (props.logo) {
      const ctx = canvas.getContext('2d')
      if (ctx) {
        const logo = new Image()
        logo.src = props.logo
        logo.onload = () => {
          const logoSize = size * 0.2 // Logo大小为二维码的20%
          const logoX = (size - logoSize) / 2
          const logoY = (size - logoSize) / 2
          
          // 绘制白色背景
          ctx.fillStyle = '#FFFFFF'
          ctx.fillRect(logoX - 2, logoY - 2, logoSize + 4, logoSize + 4)
          
          // 绘制Logo
          ctx.drawImage(logo, logoX, logoY, logoSize, logoSize)
        }
      }
    }

    // 清除旧的内容
    qrContainer.value.innerHTML = ''
    qrContainer.value.appendChild(canvas)
  } catch (error) {
    console.error('Failed to generate QR code:', error)
  }
}

// 监听属性变化，重新生成二维码
watch(() => props.url, generateQR, { immediate: true })
watch(() => props.color, generateQR)
watch(() => props.backgroundColor, generateQR)
watch(() => props.size, generateQR)
watch(() => props.logo, generateQR)

onMounted(generateQR)

// 导出下载方法
defineExpose({
  download: () => {
    if (!qrContainer.value) return
    
    const canvas = qrContainer.value.querySelector('canvas')
    if (!canvas) return
    
    const link = document.createElement('a')
    link.download = 'qrcode.png'
    link.href = canvas.toDataURL('image/png')
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }
})
</script>

<style scoped>
.qr-code {
  @apply flex items-center justify-center;
}

.qr-code canvas {
  @apply max-w-full h-auto;
}
</style> 