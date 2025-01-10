<template>
  <div class="min-h-screen bg-fixed bg-cover bg-center" 
       style="background-image: url('https://wechat-group-all.oss-cn-hangzhou.aliyuncs.com/image/header_back.png');">
    <!-- 整体背景遮罩 -->
    <div class="min-h-screen bg-gradient-to-b from-black/30 via-white/80 to-white/90">
      <!-- 顶部横幅 -->
      <div class="relative overflow-hidden min-h-[280px] md:min-h-[400px]">
        <!-- 内容 -->
        <div class="container mx-auto px-4 h-full flex flex-col items-center justify-center relative z-10 py-12 md:py-20">
          <h1 class="text-4xl md:text-6xl font-bold mb-4 md:mb-6 text-white drop-shadow-lg text-center" 
              style="font-family: 'Ma Shan Zheng', cursive; text-shadow: 2px 2px 4px rgba(0,0,0,0.3);">
            AI群江湖
          </h1>
          <p class="text-xl md:text-2xl text-white mb-6 md:mb-8 tracking-wider text-center max-w-2xl font-ma-shan">
            一个充满神秘与机遇的AI江湖世界
          </p>
          <router-link to="/opportunities" class="inline-block w-full md:w-auto px-4 md:px-0">
            <button class="w-full md:w-auto bg-white/90 backdrop-blur-sm text-gray-800 px-6 md:px-8 py-3 rounded-full text-lg font-semibold hover:bg-white transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-1 font-ma-shan">
              立即加入
            </button>
          </router-link>
        </div>
      </div>

      <!-- 三大核心价值主张 -->
      <div class="container mx-auto px-4 py-12 -mt-20 relative z-20">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <!-- AI资源库 -->
          <div class="bg-white/95 backdrop-blur-md rounded-xl shadow-lg p-6 transform hover:scale-105 transition-all duration-300 hover:shadow-xl border border-gray-100">
            <div class="text-center mb-4">
              <div class="w-16 h-16 bg-gradient-to-br from-cyan-400 to-cyan-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
                <svg class="w-8 h-8 text-white" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M12 2L13 4H19L20 6L12 22L4 6L5 4H11L12 2Z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M12 6V18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M8 8L16 8" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M9 12L15 12" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </div>
              <h2 class="text-2xl font-bold text-gray-800 mb-2">汇聚AI英雄</h2>
              <p class="text-gray-600 font-ma-shan">
                初入江湖的"AI小白"<br>
                身怀绝技的"AI创造者"<br>
                勇猛精进的"创业者"
              </p>
            </div>
          </div>

          <!-- AI副业机会 -->
          <div class="bg-white/95 backdrop-blur-md rounded-xl shadow-lg p-6 transform hover:scale-105 transition-all duration-300 hover:shadow-xl border border-gray-100">
            <div class="text-center mb-4">
              <div class="w-16 h-16 bg-gradient-to-br from-fuchsia-400 to-fuchsia-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
                <svg class="w-8 h-8 text-white" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M5 3H19C20.1046 3 21 3.89543 21 5V19C21 20.1046 20.1046 21 19 21H5C3.89543 21 3 20.1046 3 19V5C3 3.89543 3.89543 3 5 3Z" stroke="currentColor" stroke-width="1.5"/>
                  <path d="M7 7H17M7 12H17M7 17H13" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                  <path d="M6 3C6 3 8 7 12 7C16 7 18 3 18 3" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                  <path d="M6 21C6 21 8 17 12 17C16 17 18 21 18 21" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                </svg>
              </div>
              <h2 class="text-2xl font-bold text-gray-800 mb-2">AI经纪人IP</h2>
              <p class="text-gray-600 font-ma-shan">
                社交资产变现<br>
                好朋友的经纪人<br>
                做人力资源专家
              </p>
            </div>
          </div>

          <!-- AI流量池 -->
          <div class="bg-white/95 backdrop-blur-md rounded-xl shadow-lg p-6 transform hover:scale-105 transition-all duration-300 hover:shadow-xl border border-gray-100">
            <div class="text-center mb-4">
              <div class="w-16 h-16 bg-gradient-to-br from-yellow-400 to-yellow-500 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg">
                <svg class="w-8 h-8 text-white" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" stroke="currentColor" stroke-width="1.5"/>
                  <path d="M12 2C14.5 7 17 9.5 22 12C17 14.5 14.5 17 12 22C9.5 17 7 14.5 2 12C7 9.5 9.5 7 12 2Z" stroke="currentColor" stroke-width="1.5"/>
                  <circle cx="12" cy="12" r="2" fill="currentColor"/>
                  <path d="M12 7C12 7 14 9 14 12C14 15 12 17 12 17" stroke="currentColor" stroke-width="1.5"/>
                  <path d="M12 7C12 7 10 9 10 12C10 15 12 17 12 17" stroke="currentColor" stroke-width="1.5"/>
                </svg>
              </div>
              <h2 class="text-2xl font-bold text-gray-800 mb-2">共享共创</h2>
              <p class="text-gray-600 font-ma-shan">
                流量共享项目共创<br>
                品质服务创造价值<br>
                信任连接关系网络
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 数据统计 -->
      <div class="py-12 relative">
        <div class="absolute inset-0 bg-black/5"></div>
        <div class="container mx-auto px-4 relative z-10">
          <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
            <div class="bg-white/95 backdrop-blur-md p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
              <div class="text-3xl font-bold text-cyan-600 mb-2 font-ma-shan">2522+</div>
              <div class="text-gray-600 font-ma-shan">武林藏宝阁</div>
            </div>
            <div class="bg-white/95 backdrop-blur-md p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
              <div class="text-3xl font-bold text-fuchsia-600 mb-2 font-ma-shan">823+</div>
              <div class="text-gray-600 font-ma-shan">江湖财富经</div>
            </div>
            <div class="bg-white/95 backdrop-blur-md p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
              <div class="text-3xl font-bold text-orange-600 mb-2 font-ma-shan">100+</div>
              <div class="text-gray-600 font-ma-shan">抢天赐机缘</div>
            </div>
            <div class="bg-white/95 backdrop-blur-md p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
              <div class="text-3xl font-bold text-orange-600 mb-2 font-ma-shan">100+</div>
              <div class="text-gray-600 font-ma-shan">江湖共创令</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 加入我们 -->
      <div class="relative overflow-hidden py-16">
        <!-- 背景装饰 -->
        <div class="absolute inset-0 bg-gradient-to-r from-black/10 via-transparent to-black/10"></div>
        <div class="container mx-auto px-4 text-center relative z-10">
          <h2 class="text-4xl font-bold mb-6 text-gray-800 font-ma-shan">你的AI江湖冒险即将开启</h2>
          <p class="text-xl mb-8 text-gray-600 font-ma-shan">与各路英雄一起，探索AI带来的无限可能</p>
          <router-link to="/opportunities" class="inline-block">
            <button class="bg-white/95 backdrop-blur-md text-gray-800 px-8 py-3 rounded-full text-lg font-semibold hover:bg-white transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-1 font-ma-shan">
              立即加入
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.aspect-w-16 {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 */
}

.aspect-w-16 > * {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}

@keyframes pulse {
  0%, 100% {
    opacity: 0.6;
  }
  50% {
    opacity: 0.8;
  }
}

.animate-pulse {
  animation: pulse 3s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

/* 添加毛笔字体效果 */
h1, h2 {
  font-family: 'Ma Shan Zheng', cursive;
  letter-spacing: 0.05em;
}

/* 添加卡片悬浮效果 */
.hover\:scale-105:hover {
  transform: scale(1.05);
  transition: all 0.3s ease-in-out;
}

/* 添加磨砂玻璃效果 */
.backdrop-blur-sm {
  backdrop-filter: blur(8px);
}

/* 优化移动端标题阴影 */
@media (max-width: 768px) {
  h1.text-4xl {
    text-shadow: 
      0 1px 2px rgba(0,0,0,0.4),
      0 2px 4px rgba(0,0,0,0.3);
  }
  
  .text-xl {
    text-shadow: 
      0 1px 2px rgba(0,0,0,0.3);
  }
}

/* 优化背景图片在不同设备上的显示 */
.bg-cover {
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
}

/* 增强卡片的透明度和模糊效果 */
.backdrop-blur-md {
  backdrop-filter: blur(12px);
}

.bg-white\/95 {
  background-color: rgba(255, 255, 255, 0.92);
}

/* 优化文字阴影效果 */
.text-white {
  text-shadow: 
    0 2px 4px rgba(0,0,0,0.3),
    0 4px 8px rgba(0,0,0,0.2);
}

/* 移动端优化 */
@media (max-width: 768px) {
  .bg-cover {
    background-position: center center;
  }
  
  .from-black\/30 {
    --tw-gradient-from: rgba(0, 0, 0, 0.4);
  }
}

/* 统一字体设置 */
.font-ma-shan {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 所有文字都使用毛笔字体 */
h1, h2, p, button, div {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 优化字间距 */
.tracking-wider {
  letter-spacing: 0.05em;
}

/* 数字使用正常字体权重 */
.text-3xl.font-bold {
  font-weight: normal;
}
</style> 