<template>
  <div class="min-h-screen bg-[#FFF8E1] py-6 sm:py-12">
    <div class="container mx-auto px-4">
      <!-- 页面标题 -->
      <div class="text-center mb-8 sm:mb-12">
        <h1 class="text-3xl sm:text-4xl font-bold text-gray-800 mb-2 sm:mb-4">AI副业项目库</h1>
        <p class="text-lg sm:text-xl text-gray-600">精选AI副业项目，助你快速变现</p>
      </div>

      <!-- 机缘列表 -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-8">
        <div v-for="(item, index) in opportunities" :key="index"
          class="bg-white rounded-xl sm:rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition-transform duration-300">
          <div class="relative">
            <!-- 金元宝背景 -->
            <div class="h-36 sm:h-48 bg-gradient-to-br relative overflow-hidden"
              :class="[item.gradientClass]">
              <div class="absolute inset-0 flex items-center justify-center">
                <!-- 光效背景 -->
                <div class="absolute inset-0 opacity-20">
                  <div class="absolute inset-0 animate-pulse" 
                    :class="item.glowClass"></div>
                </div>
                <!-- 金元宝图片容器 -->
                <div class="relative w-24 h-24 sm:w-32 sm:h-32 
                  transform hover:scale-110 transition-transform duration-500
                  animate-float">
                  <img src="https://canyin-app.oss-cn-guangzhou.aliyuncs.com/images/money.png" 
                    alt="金元宝" 
                    class="w-full h-full object-contain drop-shadow-2xl filter brightness-110"/>
                  <!-- 光晕效果 -->
                  <div class="absolute inset-0 bg-white opacity-0 hover:opacity-20 
                    transition-opacity duration-300 rounded-full"></div>
                </div>
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
import { useRouter } from 'vue-router'

const router = useRouter()

const opportunities = [
  {
    title: '新手起步',
    price: 10,
    gradientClass: 'from-blue-400 to-blue-600',
    titleColor: 'text-blue-600',
    priceColor: 'text-blue-600',
    buttonClass: 'bg-blue-600 hover:bg-blue-700',
    glowClass: 'bg-blue-300',
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
    glowClass: 'bg-green-300',
    benefits: [
      'Midjourney接单技巧',
      'AI写作项目获客',
      '闲鱼副业变现方法',
      '新媒体涨粉秘籍'
    ]
  },
  {
    title: '内容创作',
    price: 20,
    gradientClass: 'from-green-400 to-green-600',
    titleColor: 'text-green-600',
    priceColor: 'text-green-600',
    buttonClass: 'bg-green-600 hover:bg-green-700',
    glowClass: 'bg-green-300',
    benefits: [
      'AI写作多平台变现',
      'Midjourney图片创作',
      '短视频文案批量生产',
      '爆款内容选题方法'
    ]
  },
  {
    title: '视频创作',
    price: 30,
    gradientClass: 'from-purple-400 to-purple-600',
    titleColor: 'text-purple-600',
    priceColor: 'text-purple-600',
    buttonClass: 'bg-purple-600 hover:bg-purple-700',
    glowClass: 'bg-purple-300',
    benefits: [
      'AI视频批量制作',
      '抖音短视频运营',
      '视频号带货方案',
      '直播话术生成'
    ]
  },
  {
    title: '电商运营',
    price: 40,
    gradientClass: 'from-red-400 to-red-600',
    titleColor: 'text-red-600',
    priceColor: 'text-red-600',
    buttonClass: 'bg-red-600 hover:bg-red-700',
    glowClass: 'bg-red-300',
    benefits: [
      'AI电商选品方法',
      '爆款文案制作',
      '详情页优化',
      '直播间运营'
    ]
  },
  {
    title: '知识付费',
    price: 50,
    gradientClass: 'from-yellow-400 to-yellow-600',
    titleColor: 'text-yellow-600',
    priceColor: 'text-yellow-600',
    buttonClass: 'bg-yellow-600 hover:bg-yellow-700',
    glowClass: 'bg-yellow-300',
    benefits: [
      'AI课程内容制作',
      '付费社群运营',
      '线上课程体系',
      '学员转化方案'
    ]
  },
  {
    title: '流量变现',
    price: 60,
    gradientClass: 'from-pink-400 to-pink-600',
    titleColor: 'text-pink-600',
    priceColor: 'text-pink-600',
    buttonClass: 'bg-pink-600 hover:bg-pink-700',
    glowClass: 'bg-pink-300',
    benefits: [
      '私域流量搭建',
      '粉丝转化体系',
      '多平台矩阵',
      '流量变现方案'
    ]
  },
  {
    title: '技术服务',
    price: 70,
    gradientClass: 'from-indigo-400 to-indigo-600',
    titleColor: 'text-indigo-600',
    priceColor: 'text-indigo-600',
    buttonClass: 'bg-indigo-600 hover:bg-indigo-700',
    glowClass: 'bg-indigo-300',
    benefits: [
      'AI工具调教服务',
      'prompt编写外包',
      'AI应用定制开发',
      '企业AI咨询'
    ]
  },
  {
    title: '培训变现',
    price: 80,
    gradientClass: 'from-cyan-400 to-cyan-600',
    titleColor: 'text-cyan-600',
    priceColor: 'text-cyan-600',
    buttonClass: 'bg-cyan-600 hover:bg-cyan-700',
    glowClass: 'bg-cyan-300',
    benefits: [
      'AI技能培训课程',
      '企业AI培训方案',
      '实战案例分享',
      '学员社群运营'
    ]
  },
  {
    title: '品牌代运营',
    price: 90,
    gradientClass: 'from-teal-400 to-teal-600',
    titleColor: 'text-teal-600',
    priceColor: 'text-teal-600',
    buttonClass: 'bg-teal-600 hover:bg-teal-700',
    glowClass: 'bg-teal-300',
    benefits: [
      '全案品牌策划',
      '新媒体矩阵运营',
      '内容创作外包',
      '整店代运营'
    ]
  },
  {
    title: '数字营销',
    price: 100,
    gradientClass: 'from-orange-400 to-orange-600',
    titleColor: 'text-orange-600',
    priceColor: 'text-orange-600',
    buttonClass: 'bg-orange-600 hover:bg-orange-700',
    glowClass: 'bg-orange-300',
    benefits: [
      'AI营销方案',
      '全渠道投放',
      '效果广告优化',
      'ROI提升策略'
    ]
  },
  {
    title: '企业服务',
    price: 110,
    gradientClass: 'from-lime-400 to-lime-600',
    titleColor: 'text-lime-600',
    priceColor: 'text-lime-600',
    buttonClass: 'bg-lime-600 hover:bg-lime-700',
    glowClass: 'bg-lime-300',
    benefits: [
      'AI业务咨询',
      '企业AI转型',
      '效能提升方案',
      '数字化升级'
    ]
  },
  {
    title: '创业孵化',
    price: 120,
    gradientClass: 'from-emerald-400 to-emerald-600',
    titleColor: 'text-emerald-600',
    priceColor: 'text-emerald-600',
    buttonClass: 'bg-emerald-600 hover:bg-emerald-700',
    glowClass: 'bg-emerald-300',
    benefits: [
      'AI创业项目评估',
      '商业模式设计',
      '团队组建指导',
      '融资对接服务'
    ]
  }
];

// 添加购买处理函数
const handlePurchase = (item: any) => {
  router.push({
    name: 'project-detail',
    params: { id: item.price } // 临时使用price作为id
  })
}

</script>

<style scoped>
/* 添加移动端滚动优化 */
@media (max-width: 640px) {
  .container {
    max-width: 100%;
    overflow-x: hidden;
  }
}

/* 添加漂浮动画 */
@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-10px) rotate(5deg);
  }
}

.animate-float {
  animation: float 3s ease-in-out infinite;
}
</style> 