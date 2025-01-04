<template>
  <div class="min-h-screen bg-[#FFF8E1] py-6 sm:py-12">
    <div class="container mx-auto px-4">
      <!-- 页面标题 -->
      <div class="text-center mb-8 sm:mb-12">
        <h1 class="text-3xl sm:text-4xl font-bold text-gray-800 mb-2 sm:mb-4">AI副业项目</h1>
        <p class="text-lg sm:text-xl text-gray-600">精选AI副业项目，助你快速变现，实现收入倍增</p>
      </div>

      <!-- 机缘列表 -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-8">
        <div v-for="(item, index) in opportunities" :key="index"
          class="bg-white rounded-xl sm:rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition-transform duration-300">
          <div class="relative">
            <!-- 锦囊图片 -->
            <div class="h-36 sm:h-48 bg-gradient-to-br"
              :class="[item.gradientClass]">
              <div class="absolute inset-0 flex items-center justify-center">
                <img src="../assets/images/treasure.png" alt="锦囊" class="w-20 h-20 sm:w-24 sm:h-24 object-contain"/>
              </div>
            </div>
            <!-- 价格标签 -->
            <div class="absolute top-3 right-3 sm:top-4 sm:right-4 bg-white px-3 sm:px-4 py-1 rounded-full shadow-md">
              <span class="text-base sm:text-lg font-bold" :class="item.priceColor">￥{{ item.price }}</span>
            </div>
          </div>
          
          <div class="p-4 sm:p-6">
            <h3 class="text-lg sm:text-xl font-bold mb-2 sm:mb-3" :class="item.titleColor">{{ item.title }}</h3>
            <ul class="space-y-1.5 sm:space-y-2 mb-4 sm:mb-6 text-sm sm:text-base">
              <li v-for="(benefit, idx) in item.benefits" :key="idx" class="flex items-start">
                <svg class="w-4 h-4 sm:w-5 sm:h-5 text-green-500 mr-2 mt-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                <span class="text-gray-600">{{ benefit }}</span>
              </li>
            </ul>
            <button class="w-full py-2.5 sm:py-3 rounded-lg text-white font-semibold text-sm sm:text-base transition-colors"
              :class="item.buttonClass"
              @click="handlePurchase(item)">
              获取项目
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const opportunities = [
  {
    title: '新手起步',
    price: 10,
    gradientClass: 'from-blue-400 to-blue-600',
    titleColor: 'text-blue-600',
    priceColor: 'text-blue-600',
    buttonClass: 'bg-blue-600 hover:bg-blue-700',
    benefits: [
      'ChatGPT写作变现指南',
      '抖音文案制作方法',
      '小红书爆款笔记教程'
    ]
  },
  {
    title: '进阶起航',
    price: 20,
    gradientClass: 'from-green-400 to-green-600',
    titleColor: 'text-green-600',
    priceColor: 'text-green-600',
    buttonClass: 'bg-green-600 hover:bg-green-700',
    benefits: [
      'Midjourney接单技巧',
      'AI写作项目获客',
      '闲鱼副业变现方法',
      '新媒体涨粉秘籍'
    ]
  },
  {
    title: '稳步成长',
    price: 30,
    gradientClass: 'from-purple-400 to-purple-600',
    titleColor: 'text-purple-600',
    priceColor: 'text-purple-600',
    buttonClass: 'bg-purple-600 hover:bg-purple-700',
    benefits: [
      'AI电商选品方法',
      '抖音带货实操技巧',
      '小红书精准引流',
      '私域成交话术'
    ]
  },
  {
    title: '多点开花',
    price: 40,
    gradientClass: 'from-red-400 to-red-600',
    titleColor: 'text-red-600',
    priceColor: 'text-red-600',
    buttonClass: 'bg-red-600 hover:bg-red-700',
    benefits: [
      'AI自媒体账号矩阵',
      '短视频批量制作',
      '多平台引流方案',
      '粉丝转化技巧'
    ]
  },
  {
    title: '规模扩张',
    price: 50,
    gradientClass: 'from-yellow-400 to-yellow-600',
    titleColor: 'text-yellow-600',
    priceColor: 'text-yellow-600',
    buttonClass: 'bg-yellow-600 hover:bg-yellow-700',
    benefits: [
      'AI知识付费课程',
      '个人IP打造方案',
      '社群运营体系',
      '成交转化话术'
    ]
  },
  {
    title: '团队起步',
    price: 60,
    gradientClass: 'from-pink-400 to-pink-600',
    titleColor: 'text-pink-600',
    priceColor: 'text-pink-600',
    buttonClass: 'bg-pink-600 hover:bg-pink-700',
    benefits: [
      'AI工作室组建',
      '团队管理方案',
      '客户获取体系',
      '项目定价策略'
    ]
  },
  {
    title: '全面发展',
    price: 70,
    gradientClass: 'from-indigo-400 to-indigo-600',
    titleColor: 'text-indigo-600',
    priceColor: 'text-indigo-600',
    buttonClass: 'bg-indigo-600 hover:bg-indigo-700',
    benefits: [
      'AI工具代理合作',
      '付费流量投放',
      '全网营销方案',
      '客户裂变体系'
    ]
  },
  {
    title: '商业拓展',
    price: 80,
    gradientClass: 'from-cyan-400 to-cyan-600',
    titleColor: 'text-cyan-600',
    priceColor: 'text-cyan-600',
    buttonClass: 'bg-cyan-600 hover:bg-cyan-700',
    benefits: [
      'AI企业服务方案',
      'B端客户开发',
      '商务合作体系',
      '规模化运营'
    ]
  },
  {
    title: '品牌升级',
    price: 90,
    gradientClass: 'from-teal-400 to-teal-600',
    titleColor: 'text-teal-600',
    priceColor: 'text-teal-600',
    buttonClass: 'bg-teal-600 hover:bg-teal-700',
    benefits: [
      'AI产品矩阵搭建',
      '全渠道获客',
      '品牌升级方案',
      '团队扩张策略'
    ]
  },
  {
    title: '创业启航',
    price: 100,
    gradientClass: 'from-orange-400 to-orange-600',
    titleColor: 'text-orange-600',
    priceColor: 'text-orange-600',
    buttonClass: 'bg-orange-600 hover:bg-orange-700',
    benefits: [
      'AI创业项目孵化',
      '商业模式优化',
      '融资对接渠道',
      '市场拓展方案'
    ]
  },
  {
    title: '企业服务',
    price: 110,
    gradientClass: 'from-lime-400 to-lime-600',
    titleColor: 'text-lime-600',
    priceColor: 'text-lime-600',
    buttonClass: 'bg-lime-600 hover:bg-lime-700',
    benefits: [
      'AI企业服务升级',
      '品牌矩阵打造',
      '全国市场布局',
      '商务资源对接'
    ]
  },
  {
    title: '全面发展',
    price: 120,
    gradientClass: 'from-emerald-400 to-emerald-600',
    titleColor: 'text-emerald-600',
    priceColor: 'text-emerald-600',
    buttonClass: 'bg-emerald-600 hover:bg-emerald-700',
    benefits: [
      'AI产业链布局',
      '全国代理体系',
      '投资人对接',
      '战略合作拓展'
    ]
  }
];

// 添加购买处理函数
const handlePurchase = (item: any) => {
  // TODO: 实现购买逻辑
  console.log('购买项目:', item.title, '价格:', item.price);
};

</script>

<style scoped>
/* 添加移动端滚动优化 */
@media (max-width: 640px) {
  .container {
    max-width: 100%;
    overflow-x: hidden;
  }
}
</style> 