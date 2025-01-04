<template>
  <div class="min-h-screen bg-[#FFF8E1]">
    <!-- 顶部横幅 -->
    <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white py-16">
      <div class="container mx-auto px-4 text-center">
        <h1 class="text-4xl font-bold mb-4">AI江湖 - 你的AI资源与机遇平台</h1>
        <p class="text-xl opacity-90">汇聚海量AI资源，创造无限商业可能</p>
      </div>
    </div>

    <!-- 三大核心价值主张 -->
    <div class="container mx-auto px-4 py-12">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <!-- AI资源库 -->
        <div class="bg-white rounded-xl shadow-lg p-6 transform hover:scale-105 transition-transform duration-300">
          <div class="text-center mb-4">
            <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">海量AI资源</h2>
            <p class="text-gray-600">
              精选优质AI工具与资源<br>
              持续更新最新AI应用<br>
              专业的资源分类与推荐
            </p>
          </div>
          <div class="text-center">
            <router-link to="/opportunities" class="inline-block">
              <button class="bg-blue-600 text-white px-6 py-2 rounded-full hover:bg-blue-700 transition-colors">
                探索资源
              </button>
            </router-link>
          </div>
        </div>

        <!-- AI副业机会 -->
        <div class="bg-white rounded-xl shadow-lg p-6 transform hover:scale-105 transition-transform duration-300">
          <div class="text-center mb-4">
            <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">AI副业机会</h2>
            <p class="text-gray-600">
              丰富的AI创业项目<br>
              低门槛副业机会<br>
              完整的变现指导
            </p>
          </div>
          <div class="text-center">
            <button class="bg-green-600 text-white px-6 py-2 rounded-full hover:bg-green-700 transition-colors">
              发现机会
            </button>
          </div>
        </div>

        <!-- AI流量池 -->
        <div class="bg-white rounded-xl shadow-lg p-6 transform hover:scale-105 transition-transform duration-300">
          <div class="text-center mb-4">
            <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">AI流量池</h2>
            <p class="text-gray-600">
              专业的流量获取方法<br>
              AI营销体系搭建<br>
              持续稳定的流量来源
            </p>
          </div>
          <div class="text-center">
            <button class="bg-purple-600 text-white px-6 py-2 rounded-full hover:bg-purple-700 transition-colors">
              了解更多
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 数据统计 -->
    <div class="bg-gray-100 py-12">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
          <div>
            <div class="text-3xl font-bold text-blue-600 mb-2">1000+</div>
            <div class="text-gray-600">AI工具资源</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-green-600 mb-2">500+</div>
            <div class="text-gray-600">副业项目</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-purple-600 mb-2">50000+</div>
            <div class="text-gray-600">用户数量</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-red-600 mb-2">100+</div>
            <div class="text-gray-600">成功案例</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 最新资源展示 -->
    <div class="container mx-auto px-4 py-12">
      <h2 class="text-2xl font-bold text-gray-800 mb-8 text-center">最新AI资源</h2>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div v-for="i in 6" :key="i" class="bg-white rounded-lg shadow-md overflow-hidden">
          <div class="aspect-w-16 aspect-h-9 bg-gray-200"></div>
          <div class="p-4">
            <h3 class="text-lg font-semibold mb-2">AI资源标题</h3>
            <p class="text-gray-600 text-sm mb-4">这是一个简短的资源描述，介绍其主要功能和特点。</p>
            <div class="flex justify-between items-center">
              <span class="text-sm text-gray-500">更新时间：2023-12-25</span>
              <span class="text-sm text-blue-600">查看详情 →</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 加入我们 -->
    <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white py-16">
      <div class="container mx-auto px-4 text-center">
        <h2 class="text-3xl font-bold mb-4">加入AI江湖，开启你的AI之旅</h2>
        <p class="text-xl mb-8 opacity-90">与数万用户一起，探索AI带来的无限可能</p>
        <button class="bg-white text-blue-600 px-8 py-3 rounded-full text-lg font-semibold hover:bg-gray-100 transition-colors">
          立即加入
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// 可以在这里添加交互逻辑
</script>

<style scoped>
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
</style> 