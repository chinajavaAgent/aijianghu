<!-- AI锦囊列表页面 -->
<template>
  <div class="min-h-screen font-ma-shan bg-cover bg-center bg-fixed relative bg-blend-soft-light"
    style="background-image: url('https://wechat-group-all.oss-cn-hangzhou.aliyuncs.com/image/header_back.png')">
    <!-- 添加一个浅色遮罩层 -->
    <div class="absolute inset-0 bg-white/85 backdrop-blur-[1px]"></div>

    <!-- 页面内容 -->
    <div class="container mx-auto px-4 py-8 sm:py-12 relative z-10">
      <!-- 页面标题区域 -->
      <div class="max-w-3xl mx-auto text-center mb-12">
        <h1 class="text-4xl sm:text-5xl font-bold text-[#7A9D96] font-ma-shan mb-4">
          AI经纪人修炼机缘
        </h1>
        <p class="text-lg text-[#4A4940]/80 font-ma-shan mb-8">
          神秘强者留下十七份机缘，待有缘人
        </p>
        <!-- 修炼步骤说明 -->
        <div class="inline-flex flex-col gap-3 bg-white/50 backdrop-blur-sm rounded-2xl p-6 shadow-sm">
          <div class="flex items-center gap-4 text-[#4A4940]/90">
            <div class="flex items-center gap-2 min-w-[100px] justify-end font-medium text-[#7A9D96]">
              <span class="w-2 h-2 rounded-full bg-[#C7DDBB]"></span>
              <span>第一步</span>
            </div>
            <div class="text-left">按数字大小逐一开启</div>
          </div>
          <div class="flex items-center gap-4 text-[#4A4940]/90">
            <div class="flex items-center gap-2 min-w-[100px] justify-end font-medium text-[#7A9D96]">
              <span class="w-2 h-2 rounded-full bg-[#C7DDBB]"></span>
              <span>第二步</span>
            </div>
            <div class="text-left">遇一位守关强者，习得其能力</div>
          </div>
          <div class="flex items-center gap-4 text-[#4A4940]/90">
            <div class="flex items-center gap-2 min-w-[100px] justify-end font-medium text-[#7A9D96]">
              <span class="w-2 h-2 rounded-full bg-[#C7DDBB]"></span>
              <span>第三步</span>
            </div>
            <div class="text-left">成为新任守关者，继续修炼</div>
          </div>
        </div>
      </div>

      <!-- 标签页切换 -->
      <div class="flex justify-center mb-8">
        <div class="inline-flex bg-white rounded-lg p-1 shadow-sm">
          <button 
            v-for="tab in tabs" 
            :key="tab.value"
            @click="currentTab = tab.value"
            class="px-6 py-2 rounded-md text-sm font-medium transition-colors duration-200 font-ma-shan"
            :class="[
              currentTab === tab.value 
                ? 'bg-[#7A9D96] text-white' 
                : 'text-[#7A9D96] hover:bg-[#F5F7F4]'
            ]"
          >
            {{ tab.label }}
            <span class="ml-1 text-xs opacity-80 font-ma-shan">
              ({{ getFilteredTips(tab.value).length }})
            </span>
          </button>
        </div>
      </div>

      <!-- 锦囊列表 -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <div v-for="tip in getFilteredTips(currentTab)" :key="tip.id"
          class="group bg-white rounded-2xl shadow-sm hover:shadow-md transition-shadow duration-300">
          <!-- 卡片头部 -->
          <div class="aspect-[2/1] rounded-t-2xl relative overflow-hidden p-4 transition-all duration-300"
            :style="{ 
              background: getCardColor(tip.requiredLevel),
              boxShadow: getCardShadow(tip.requiredLevel)
            }">
            <!-- 图标容器 -->
            <div class="absolute inset-0 flex items-center justify-center">
              <img src="https://canyin-app.oss-cn-guangzhou.aliyuncs.com/images/money.png" 
                alt="金元宝" 
                class="w-24 h-24 object-contain rounded-2xl opacity-90 transition-all duration-300 ease-in-out transform group-hover:scale-110 group-hover:opacity-100 bounce-animation"/>
            </div>
            <!-- 价格标签 -->
            <div class="relative z-10">
              <div class="inline-block bg-white/30 backdrop-blur-sm px-3 py-1 rounded-full">
                <span class="text-base font-medium font-ma-shan"
                  :style="{ color: getTextColor(tip.requiredLevel) }">￥{{ tip.price }}</span>
              </div>
            </div>
            <!-- 境界标签 -->
            <div class="absolute bottom-4 left-4 right-4 flex justify-between items-center">
              <div class="flex items-center gap-2 bg-white/30 backdrop-blur-sm px-3 py-1 rounded-full">
                <i class="fas" :class="[isLocked(tip) ? 'fa-lock' : 'fa-lock-open']"
                  :style="{ color: getTextColor(tip.requiredLevel) }"></i>
                <span class="text-sm font-ma-shan"
                  :style="{ color: getTextColor(tip.requiredLevel) }">{{ getRealmName(tip.requiredLevel) }}</span>
              </div>
            </div>
          </div>
          
          <!-- 卡片内容 -->
          <div class="p-6">
            <h3 class="text-lg font-medium text-[#4A4940] mb-4 font-ma-shan">{{ tip.title }}</h3>
            
            <!-- 温馨提醒 -->
            <div class="bg-[#F5F7F4] rounded-lg p-4 mb-5">
              <p class="text-sm text-[#4A4940]/90 font-ma-shan">
                <span class="font-medium text-[#7A9D96]">温馨提醒：</span>
                {{ getRealmTip(tip.requiredLevel) }}
              </p>
            </div>

            <button class="w-full py-3 rounded-lg text-white font-medium text-sm transition-colors duration-200
              bg-[#7A9D96] hover:bg-[#6B8E87] font-ma-shan">
              {{ getButtonText(tip) }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { getAiTipsList, getAiTipsDetail } from '@/api/tips'
import { getUserOrders } from '@/api/order'
import { getUserInfo } from '@/api/user'
import type { AiTips } from '@/types/tips'
import type { Order } from '@/types/order'
import { showToast } from 'vant'
import { useUserStore } from '@/store/user'
import type { User } from '@/types/user'

const router = useRouter()
const tipsList = ref<AiTips[]>([])
const showProjectDialog = ref(false)
const selectedTipProjects = ref<any[]>([])
const userStore = useUserStore()
const pendingOrders = ref<Order[]>([])
const userLevel = ref(0)
const currentTab = ref('all')

const tabs = [
  { value: 'all', label: '全部' },
  { value: 'unlocked', label: '已解锁' },
  { value: 'locked', label: '未解锁' }
]

// 计算用户是否登录
const isLoggedIn = computed(() => {
  return !!userStore.$state.token && userStore.$state.id > 0
})

// 加载锦囊列表
const loadTipsList = async () => {
  try {
    const response = await getAiTipsList({
      page: 1,
      size: 100
    })
    tipsList.value = response.data.records || []
    
    // 如果用户已登录，加载待审核订单
    if (isLoggedIn.value) {
      await loadPendingOrders()
    }
  } catch (error) {
    console.error('加载锦囊列表失败:', error)
    showToast('加载失败，请重试')
  }
}

// 加载待审核订单
const loadPendingOrders = async () => {
  try {
    const response = await getUserOrders(userStore.$state.id, {
      page: 1,
      size: 100,
      status: 0 // 待审核状态
    })
    pendingOrders.value = (response.data as any).records || []
  } catch (error) {
    console.error('加载待审核订单失败:', error)
  }
}

// 获取样式类
const getGradientClass = (tip: AiTips) => {
  return 'from-gray-800 to-gray-900'
}

const getGlowClass = (tip: AiTips) => {
  return 'bg-white/20'
}

const getTitleColorClass = (tip: AiTips) => {
  return 'text-gray-800'
}

const getPriceColorClass = getTitleColorClass

const getButtonClass = (tip: AiTips) => {
  return 'bg-gray-800 hover:bg-gray-900'
}

// 境界映射
const REALM_MAPPING = [
  { level: 1, name: '搬血境', tip: '搬血初成，需稳扎稳打，按部就班，切忌急功近利，方能血气充盈，根基稳固。' },
  { level: 2, name: '洞天境', tip: '初入洞天，莫急莫躁，循序渐进，方能稳固根基，洞开新天地。' },
  { level: 3, name: '化灵境', tip: '灵性初显，需静心养神，以灵驭气，切勿心浮气躁，致灵性受损。' },
  { level: 4, name: '铭文境', tip: '铭文刻道，需心细如发，一丝不苟，错一字则满盘皆输，耐心是关键。' },
  { level: 5, name: '列阵境', tip: '阵法千变，需熟记于心，灵活运用，遇敌时方能临危不乱，布阵御敌。' },
  { level: 6, name: '尊者境', tip: '身为尊者，威严自重，待人接物需有度，切勿恃强凌弱，失了尊者风范。' },
  { level: 7, name: '神火境', tip: '神火炽烈，需谨慎掌控，以防灼伤自身，同时也要以火炼心，淬炼道心。' },
  { level: 8, name: '真一境', tip: '真一归元，需摒弃杂念，一心向道，方能领悟真一之妙，突破瓶颈。' },
  { level: 9, name: '圣祭境', tip: '圣祭之路，需虔诚祭炼，心怀敬畏，以圣心行祭事，方得圣力加持。' },
  { level: 10, name: '天神境', tip: '天神高远，需仰望星空，志存高远，不断追求，方能触摸神之领域。' },
  { level: 11, name: '虚道境', tip: '虚道缥缈，需心如止水，洞察虚无，以虚御实，方能领悟虚道真谛。' },
  { level: 12, name: '斩我境', tip: '斩我断念，需狠心决绝，斩断执念，方能突破自我，重塑道心。' },
  { level: 13, name: '遁一境', tip: '遁一归隐，需隐于无形，藏于虚无，以一遁万，方能遁入无尽虚空。' },
  { level: 14, name: '至尊境', tip: '至尊之位，需心怀天下，以德服人，方能统领群雄，成就至尊霸业。' },
  { level: 15, name: '仙王境', tip: '仙王临世，需仙心不泯，慈悲为怀，以仙力庇佑众生，方得仙王之名。' },
  { level: 16, name: '仙帝境', tip: '仙帝之尊，需掌控仙界，运筹帷幄，以帝道统御仙途，方成仙帝霸业。' }
]

// 获取境界名称
const getRealmName = (level: number) => {
  const realm = REALM_MAPPING.find(r => r.level === level)
  return realm ? realm.name : '未知境界'
}

// 获取境界提示
const getRealmTip = (level: number) => {
  const realm = REALM_MAPPING.find(r => r.level === level)
  return realm ? realm.tip : ''
}

// 获取按钮文本
const getButtonText = (tip: AiTips) => {
  if (!tip.projects || tip.projects.length === 0) {
    return '此处空空如也'
  }
  
  // 检查用户是否登录
  if (!isLoggedIn.value) {
    return '需先拜入门墙'
  }
  
  // 检查是否有待审核订单
  const hasPendingOrder = pendingOrders.value.some(order => order.tipsId === tip.id)
  if (hasPendingOrder) {
    return '掌门正在审核'
  }
  
  // 使用实时获取的用户等级
  if (userLevel.value < tip.requiredLevel) {
    const nextRealm = getRealmName(tip.requiredLevel-1)
    return `需修炼至${nextRealm}`
  }
  
  // 用户等级已满足要求
  return '已解锁'
}

// 添加购买处理函数
const handlePurchase = async (tip: AiTips) => {
  try {
    if (!tip.id) {
      showToast('锦囊ID无效')
      return
    }
    
    // 检查用户是否登录
    if (!isLoggedIn.value) {
      router.push('/login')
      return
    }
    
    // 检查是否有待审核订单
    const hasPendingOrder = pendingOrders.value.some(order => order.tipsId === tip.id)
    if (hasPendingOrder) {
      router.push('/orders')
      return
    }
    
    // 检查用户等级
    if (userStore.$state.level < tip.requiredLevel) {
      // 跳转到锦囊详情页面
      router.push(`/tips/${tip.id}`)
      return
    }
    
    // 跳转到锦囊详情页面
    router.push(`/tips/${tip.id}`)
  } catch (error) {
    console.error('获取项目信息失败:', error)
    showToast('获取项目信息失败，请重试')
  }
}

// 处理项目选择
const handleProjectSelect = (project: any) => {
  if (!project.tipId) {
    showToast('项目信息不完整')
    return
  }
  router.push(`/tips/${project.tipId}`)
}

// 获取最新的用户信息
const fetchUserInfo = async () => {
  if (!isLoggedIn.value) return
  try {
    const response = await getUserInfo(userStore.$state.id)
    // 更新本地用户等级
    userLevel.value = response.data.level
    // 同步更新store中的用户等级
    userStore.$patch({
      level: response.data.level
    })
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

// 判断锦囊是否已解锁
const isLocked = (tip: AiTips) => {
  // 如果用户未登录，则锁定
  if (!isLoggedIn.value) {
    return true
  }
  
  // 如果有待审核订单，则锁定
  const hasPendingOrder = pendingOrders.value.some(order => order.tipsId === tip.id)
  if (hasPendingOrder) {
    return true
  }
  
  // 如果用户等级不足，则锁定
  return userLevel.value < tip.requiredLevel
}

// 从URL参数中获取初始tab
const initTabFromUrl = () => {
  const urlParams = new URLSearchParams(window.location.search)
  const tabParam = urlParams.get('tab')
  if (tabParam && tabs.some(tab => tab.value === tabParam)) {
    currentTab.value = tabParam
  }
}

// 页面加载时获取数据
onMounted(async () => {
  initTabFromUrl() // 初始化tab
  if (isLoggedIn.value) {
    // 先获取用户信息，确保等级是最新的
    await fetchUserInfo()
  }
  await loadTipsList()
})

const getFilteredTips = (tab: string) => {
  if (tab === 'all') {
    return tipsList.value
  } else if (tab === 'unlocked') {
    return tipsList.value.filter(tip => !isLocked(tip))
  } else if (tab === 'locked') {
    return tipsList.value.filter(tip => isLocked(tip))
  }
  return []
}

// 获取卡片背景色
const getCardColor = (level: number) => {
  // 使用更深的颜色，降低透明度以配合磨砂效果
  const baseColors = [
    'rgba(255,102,102,0.65)',  // 更深的桃红色
    'rgba(200,220,220,0.65)',  // 更深的凝脂色
    'rgba(255,190,60,0.65)',   // 更深的缃叶色
    'rgba(20,60,100,0.65)'     // 更深的群青色
  ]
  
  const colorIndex = ((level - 1) % 4)
  return baseColors[colorIndex]
}

// 获取文字颜色
const getTextColor = (level: number) => {
  const colorIndex = ((level - 1) % 4)
  // 群青和凝脂背景使用不同的文字颜色
  if (colorIndex === 3) return '#E8F0F0'  // 群青背景用浅色
  if (colorIndex === 1) return '#1B4B7C'  // 凝脂背景用深色
  return '#2A3F54'  // 其他背景用中性色
}

// 获取卡片阴影
const getCardShadow = (level: number) => {
  const baseColors = {
    0: '255,102,102',  // 桃红
    1: '200,220,220',  // 凝脂
    2: '255,190,60',   // 缃叶
    3: '20,60,100'     // 群青
  }
  
  const colorIndex = ((level - 1) % 4)
  const rgb = baseColors[colorIndex as keyof typeof baseColors]
  return `0 12px 36px rgba(${rgb},0.4), 0 8px 16px rgba(${rgb},0.2)`
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

.font-ma-shan {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 添加卡片磨砂玻璃效果 */
.group {
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  background-blend-mode: overlay;
  transition: all 0.3s ease;
  border: 1px solid rgba(255, 255, 255, 0.35);
  background-color: rgba(255, 255, 255, 0.25);
  box-shadow: 
    0 8px 32px rgba(0, 0, 0, 0.15),
    0 4px 16px rgba(0, 0, 0, 0.1),
    inset 0 2px 3px rgba(255, 255, 255, 0.25);
  transform: perspective(1000px) translateZ(0);
  will-change: transform;
}

.group:hover {
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  transform: perspective(1000px) translateZ(20px) translateY(-5px);
  border-color: rgba(255, 255, 255, 0.5);
  background-color: rgba(255, 255, 255, 0.3);
  box-shadow: 
    0 16px 48px rgba(0, 0, 0, 0.2),
    0 8px 24px rgba(0, 0, 0, 0.15),
    inset 0 2px 6px rgba(255, 255, 255, 0.3);
}

/* 卡片头部磨砂效果 */
.aspect-\[2\/1\] {
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  background-blend-mode: soft-light;
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 
    inset 0 2px 4px rgba(255, 255, 255, 0.2),
    0 4px 8px rgba(0, 0, 0, 0.1);
}

@keyframes bounce-up {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.bounce-animation {
  animation: bounce-up 2s ease-in-out infinite;
}

.group:hover .bounce-animation {
  animation-play-state: running;
}

/* 移动端优化 */
@media (max-width: 640px) {
  .container {
    padding-left: 1rem;
    padding-right: 1rem;
  }
  
  .grid {
    gap: 1.5rem;
  }
}

/* 磨砂玻璃效果 */
.bg-white {
  background: rgba(255, 255, 255, 0.25) !important;
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.35);
  box-shadow: 
    0 8px 32px rgba(31, 38, 135, 0.2),
    0 4px 16px rgba(31, 38, 135, 0.15),
    inset 0 2px 3px rgba(255, 255, 255, 0.2);
}

/* 温馨提醒区域磨砂效果 */
.bg-\[#F5F7F4\] {
  background: rgba(245, 247, 244, 0.65) !important;
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.35);
  box-shadow: 
    0 8px 32px rgba(31, 38, 135, 0.2),
    0 4px 16px rgba(31, 38, 135, 0.15),
    inset 0 2px 3px rgba(255, 255, 255, 0.2);
}
</style> 