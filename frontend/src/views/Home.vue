<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50">
    <!-- 顶部横幅 -->
    <div class="bg-gradient-to-r from-cyan-500 via-fuchsia-500 to-yellow-400 text-white py-16 relative overflow-hidden">
      <!-- 添加动态光效 -->
      <div class="absolute inset-0">
        <div class="absolute inset-0 bg-gradient-to-r from-cyan-400/30 via-fuchsia-400/30 to-yellow-400/30 animate-pulse"></div>
        <div class="absolute top-0 left-0 w-full h-full bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNTYiIGhlaWdodD0iMTAwIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxkZWZzPjxwYXR0ZXJuIGlkPSJ3YXZlIiB4PSIwIiB5PSIwIiB3aWR0aD0iNTYiIGhlaWdodD0iMTAwIiBwYXR0ZXJuVW5pdHM9InVzZXJTcGFjZU9uVXNlIj48cGF0aCBkPSJNMjggNjZDNDAgNjYgNDAgMzQgNTYgMzRMNTYgMEw1NiAxMDBMMjggNjZaIiBmaWxsPSJyZ2JhKDI1NSwyNTUsMjU1LDAuMDMpIi8+PHBhdGggZD0iTTI4IDY2QzE2IDY2IDE2IDM0IDAgMzRMMCAwTDAgMTAwTDI4IDY2WiIgZmlsbD0icmJnYSgyNTUsMjU1LDI1NSwwLjAzKSIvPjwvcGF0dGVybj48L2RlZnM+PHJlY3Qgd2lkdGg9IjEwMCUiIGhlaWdodD0iMTAwJSIgZmlsbD0idXJsKCN3YXZlKSIvPjwvc3ZnPg==')] opacity-10"></div>
      </div>
      <div class="container mx-auto px-4 text-center relative z-10">
        <h1 class="text-4xl font-bold mb-4 drop-shadow-lg">AI群江湖</h1>
        <p class="text-xl opacity-90">一个充满神秘与机遇的AI江湖世界</p>
        <router-link to="/opportunities" class="inline-block">
        <button class="mt-4 bg-white text-gray-800 px-8 py-3 rounded-full text-lg font-semibold hover:bg-opacity-90 transition-all duration-300 shadow-lg hover:shadow-xl">
          立即加入
        </button>
      </router-link>
      </div>
      
    </div>

    <!-- 三大核心价值主张 -->
    <div class="container mx-auto px-4 py-12">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <!-- AI资源库 -->
        <div class="bg-gradient-to-br from-white to-cyan-50 rounded-xl shadow-lg p-6 transform hover:scale-105 transition-all duration-300 hover:shadow-xl border border-cyan-100/50">
          <div class="text-center mb-4">
            <div class="w-16 h-16 bg-gradient-to-br from-cyan-400 to-cyan-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg shadow-cyan-200">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">汇聚AI英雄</h2>
            <p class="text-gray-600">
              初入江湖的“AI小白”<br>
              身怀绝技的“AI创造者”<br>
              勇猛精进的“创业者”
            </p>
          </div>
          <!-- <div class="text-center">
            <router-link to="/opportunities" class="inline-block">
              <button class="bg-gradient-to-r from-cyan-500 to-cyan-600 text-white px-6 py-2 rounded-full hover:shadow-lg hover:from-cyan-600 hover:to-cyan-700 transition-all duration-300">
                探索资源
              </button>
            </router-link>
          </div> -->
        </div>

        <!-- AI副业机会 -->
        <div class="bg-gradient-to-br from-white to-fuchsia-50 rounded-xl shadow-lg p-6 transform hover:scale-105 transition-all duration-300 hover:shadow-xl border border-fuchsia-100/50">
          <div class="text-center mb-4">
            <div class="w-16 h-16 bg-gradient-to-br from-fuchsia-400 to-fuchsia-600 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg shadow-fuchsia-200">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">AI经纪人IP</h2>
            <p class="text-gray-600">
              社交资产变现<br>
              好朋友的经纪人<br>
              做人力资源专家
            </p>
          </div>
          <!-- <div class="text-center">
            <button class="bg-gradient-to-r from-fuchsia-500 to-fuchsia-600 text-white px-6 py-2 rounded-full hover:shadow-lg hover:from-fuchsia-600 hover:to-fuchsia-700 transition-all duration-300">
              发现机会
            </button>
          </div> -->
        </div>

        <!-- AI流量池 -->
        <div class="bg-gradient-to-br from-white to-yellow-50 rounded-xl shadow-lg p-6 transform hover:scale-105 transition-all duration-300 hover:shadow-xl border border-yellow-100/50">
          <div class="text-center mb-4">
            <div class="w-16 h-16 bg-gradient-to-br from-yellow-400 to-yellow-500 rounded-full flex items-center justify-center mx-auto mb-4 shadow-lg shadow-yellow-200">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">共享共创</h2>
            <p class="text-gray-600">
              流量共享项目共创<br>
              品质服务创造价值<br>
              信任连接关系网络
            </p>
          </div>
          <!-- <div class="text-center">
            <button class="bg-gradient-to-r from-yellow-400 to-yellow-500 text-white px-6 py-2 rounded-full hover:shadow-lg hover:from-yellow-500 hover:to-yellow-600 transition-all duration-300">
              了解更多
            </button>
          </div> -->
        </div>
      </div>
    </div>

    <!-- 数据统计 -->
    <div class="bg-gradient-to-br from-white to-gray-50 py-12">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
          <div class="bg-gradient-to-br from-white to-cyan-50 p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
            <div class="text-3xl font-bold text-cyan-600 mb-2">2522+</div>
            <div class="text-gray-600">武林藏宝阁</div>
          </div>
          <div class="bg-gradient-to-br from-white to-fuchsia-50 p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
            <div class="text-3xl font-bold text-fuchsia-600 mb-2">823+</div>
            <div class="text-gray-600">江湖财富经</div>
          </div>
          <div class="bg-gradient-to-br from-white to-orange-50 p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
            <div class="text-3xl font-bold text-orange-600 mb-2">100+</div>
            <div class="text-gray-600">抢天赐机缘</div>
          </div>
          <div class="bg-gradient-to-br from-white to-orange-50 p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300">
            <div class="text-3xl font-bold text-orange-600 mb-2">100+</div>
            <div class="text-gray-600">江湖共创令</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 加入我们 -->
    <div class="bg-gradient-to-r from-cyan-500 via-fuchsia-500 to-yellow-400 text-white py-16 relative overflow-hidden">
      <!-- 添加动态光效 -->
      <div class="absolute inset-0">
        <div class="absolute inset-0 bg-gradient-to-r from-cyan-400/30 via-fuchsia-400/30 to-yellow-400/30 animate-pulse"></div>
        <div class="absolute top-0 left-0 w-full h-full bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNTYiIGhlaWdodD0iMTAwIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxkZWZzPjxwYXR0ZXJuIGlkPSJ3YXZlIiB4PSIwIiB5PSIwIiB3aWR0aD0iNTYiIGhlaWdodD0iMTAwIiBwYXR0ZXJuVW5pdHM9InVzZXJTcGFjZU9uVXNlIj48cGF0aCBkPSJNMjggNjZDNDAgNjYgNDAgMzQgNTYgMzRMNTYgMEw1NiAxMDBMMjggNjZaIiBmaWxsPSJyZ2JhKDI1NSwyNTUsMjU1LDAuMDMpIi8+PHBhdGggZD0iTTI4IDY2QzE2IDY2IDE2IDM0IDAgMzRMMCAwTDAgMTAwTDI4IDY2WiIgZmlsbD0icmJnYSgyNTUsMjU1LDI1NSwwLjAzKSIvPjwvcGF0dGVybj48L2RlZnM+PHJlY3Qgd2lkdGg9IjEwMCUiIGhlaWdodD0iMTAwJSIgZmlsbD0idXJsKCN3YXZlKSIvPjwvc3ZnPg==')] opacity-10"></div>
      </div>
      <div class="container mx-auto px-4 text-center relative z-10">
        <h2 class="text-3xl font-bold mb-4">你的AI江湖冒险即将开启</h2>
        <p class="text-xl mb-8 opacity-90">与各路英雄一起，探索AI带来的无限可能</p>
        <router-link to="/opportunities" class="inline-block">
        <button class="bg-white text-gray-800 px-8 py-3 rounded-full text-lg font-semibold hover:bg-opacity-90 transition-all duration-300 shadow-lg hover:shadow-xl">
          立即加入
        </button>
      </router-link>
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
</style> 