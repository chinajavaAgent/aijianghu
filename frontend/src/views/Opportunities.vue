<!-- AI锦囊列表页面 -->
<template>
  <div class="min-h-screen bg-fixed bg-cover bg-center" 
       style="background-image: url('https://wechat-group-all.oss-cn-hangzhou.aliyuncs.com/image/header_back.png');">
    <!-- 整体背景遮罩 -->
    <div class="min-h-screen bg-gradient-to-b from-black/30 via-white/80 to-white/90">
      <div class="container mx-auto px-4">
        <!-- 页面标题区域 -->
        <div class="flex flex-col sm:flex-row items-center justify-between mb-8 gap-4 pt-8">
          <!-- 左侧标题和说明 -->
          <div class="flex flex-col items-center sm:items-end w-full sm:w-auto">
            <h1 class="text-4xl sm:text-5xl font-bold text-white font-ma-shan mb-4 text-center sm:text-right relative" 
                style="text-shadow: 2px 2px 4px rgba(0,0,0,0.3);">
              AI经纪人修炼机缘
              <div class="absolute -bottom-2 left-0 right-0 h-1 bg-gradient-to-r from-transparent via-white to-transparent"></div>
            </h1>
            <p class="text-lg text-white/90 font-ma-shan mb-3 text-center sm:text-right">
              神秘强者留下十七份机缘，待有缘人
            </p>
            <div class="text-base text-white/80 font-ma-shan tracking-wider flex flex-col items-center sm:items-end gap-2">
              <p class="flex items-center gap-2 flex-row-reverse">
                <span>按数字大小逐一开启</span>
                <span class="bg-white/10 text-white px-3 py-1 rounded-full border border-white/30">壹至十七关</span>
              </p>
              <p class="flex items-center gap-2 flex-row-reverse">
                <span>遇一位守关强者，习得其能力</span>
                <span class="bg-white/10 text-white px-3 py-1 rounded-full border border-white/30">每一关</span>
              </p>
              <p class="flex items-center gap-2 flex-row-reverse">
                <span>成为新任守关者，继续修炼</span>
                <span class="bg-white/10 text-white px-3 py-1 rounded-full border border-white/30">突破后</span>
              </p>
            </div>
          </div>
        </div>

        <!-- 标签页切换 -->
        <div class="flex justify-center mb-6">
          <div class="bg-white/90 backdrop-blur-sm rounded-full shadow-md p-1 inline-flex space-x-1">
            <button 
              v-for="tab in tabs" 
              :key="tab.value"
              @click="currentTab = tab.value"
              class="px-6 py-2 rounded-full text-sm font-medium transition-colors duration-200"
              :class="[
                currentTab === tab.value 
                  ? 'bg-gray-800 text-white' 
                  : 'text-gray-600 hover:bg-gray-100'
              ]"
            >
              {{ tab.label }}
              <span class="ml-1 text-xs" :class="currentTab === tab.value ? 'text-white' : 'text-gray-400'">
                ({{ getFilteredTips(tab.value).length }})
              </span>
            </button>
          </div>
        </div>

        <!-- 锦囊列表 -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-8">
          <div v-for="tip in getFilteredTips(currentTab)" :key="tip.id"
            class="bg-white/95 backdrop-blur-sm rounded-xl sm:rounded-2xl shadow-lg overflow-hidden transform hover:scale-105 transition-transform duration-300 border border-gray-100/50">
            <div class="relative">
              <!-- 金元宝背景 -->
              <div class="h-36 sm:h-48 bg-gradient-to-br relative overflow-hidden"
                :class="getGradientClass(tip)">
                <!-- 装饰纹理 -->
                <div class="absolute inset-0 opacity-10 bg-[url('https://api.dicebear.com/7.x/shapes/svg?seed=dragon')]"></div>
                <div class="absolute inset-0 flex items-center justify-center">
                  <!-- 光效背景 -->
                  <div class="absolute inset-0 opacity-20">
                    <div class="absolute inset-0 animate-pulse" 
                      :class="getGlowClass(tip)"></div>
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
              <div class="absolute top-3 right-3 sm:top-4 sm:right-4 bg-white/90 backdrop-blur-sm px-3 sm:px-4 py-1 rounded-full shadow-md border border-gray-100/50">
                <span class="text-base sm:text-lg font-bold" :class="getTitleColorClass(tip)">￥{{ tip.price }}</span>
              </div>
              
              <!-- 境界标签 -->
              <div class="absolute bottom-3 left-3 backdrop-blur-sm px-3 py-1 rounded-full shadow-md border border-gray-100/50"
                :class="[
                  isLocked(tip) ? 'bg-gray-100/90' : 'bg-green-100/90',
                  'flex items-center space-x-1'
                ]">
                <i class="fas" :class="[isLocked(tip) ? 'fa-lock text-gray-500' : 'fa-lock-open text-green-500']"></i>
                <span class="text-sm" :class="[isLocked(tip) ? 'text-gray-500' : 'text-green-500']">
                  {{ getRealmName(tip.requiredLevel) }}
                </span>
              </div>
            </div>
            
            <div class="p-4 sm:p-6 relative">
              <!-- 装饰边框 -->
              <div class="absolute inset-0 border-2 border-gray-100/50 m-2 rounded-lg pointer-events-none"></div>
              
              <h3 class="text-lg sm:text-xl font-bold mb-2 sm:mb-3 relative" :class="getTitleColorClass(tip)">{{ tip.title }}</h3>
              
              <!-- 温馨提醒 -->
              <div class="bg-yellow-50/80 backdrop-blur-sm rounded-lg p-3 mb-4 relative border border-yellow-100/50">
                <!-- 装饰角 -->
                <div class="absolute w-2 h-2 border-t-2 border-l-2 border-yellow-200/50 top-0 left-0"></div>
                <div class="absolute w-2 h-2 border-t-2 border-r-2 border-yellow-200/50 top-0 right-0"></div>
                <div class="absolute w-2 h-2 border-b-2 border-l-2 border-yellow-200/50 bottom-0 left-0"></div>
                <div class="absolute w-2 h-2 border-b-2 border-r-2 border-yellow-200/50 bottom-0 right-0"></div>
                
                <p class="text-sm text-yellow-700 font-ma-shan relative">
                  <span class="ml-1">温馨提醒：</span>
                  {{ getRealmTip(tip.requiredLevel) }}
                </p>
              </div>

              <button class="w-full py-2.5 sm:py-3 rounded-lg text-white font-semibold text-sm sm:text-base transition-colors relative overflow-hidden group"
                :class="getButtonClass(tip)"
                @click="handlePurchase(tip)">
                <!-- 按钮装饰效果 -->
                <div class="absolute inset-0 bg-white/10 transform -skew-x-12 translate-x-full group-hover:translate-x-[-180%] transition-transform duration-700"></div>
                <span class="relative">{{ getButtonText(tip) }}</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 项目选择对话框 -->
      <van-dialog
        v-model:show="showProjectDialog"
        title="选择项目"
        :show-cancel-button="true"
        cancel-button-text="关闭"
        confirm-button-text=""
        class="project-select-dialog"
      >
        <div class="space-y-4 p-4">
          <div v-for="project in selectedTipProjects" :key="project.id"
            class="p-4 bg-gray-50 rounded-lg cursor-pointer hover:bg-gray-100"
            @click="handleProjectSelect(project)">
            <div class="font-medium text-gray-800 mb-1">{{ project.name }}</div>
            <div class="text-sm text-gray-600">{{ project.description }}</div>
          </div>
        </div>
      </van-dialog>
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
  const index = (tip.id || 0) % 10
  const gradients = [
    'from-blue-400 to-blue-600',
    'from-red-400 to-red-600',
    'from-yellow-400 to-yellow-600',
    'from-pink-400 to-pink-600',
    'from-indigo-400 to-indigo-600',
    'from-cyan-400 to-cyan-600',
    'from-teal-400 to-teal-600',
    'from-orange-400 to-orange-600',
    'from-lime-400 to-lime-600',
    'from-emerald-400 to-emerald-600'
  ]
  return gradients[index]
}

const getGlowClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const glows = [
    'bg-blue-300',
    'bg-red-300',
    'bg-yellow-300',
    'bg-pink-300',
    'bg-indigo-300',
    'bg-cyan-300',
    'bg-teal-300',
    'bg-orange-300',
    'bg-lime-300',
    'bg-emerald-300'
  ]
  return glows[index]
}

const getTitleColorClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const colors = [
    'text-blue-600',
    'text-red-600',
    'text-yellow-600',
    'text-pink-600',
    'text-indigo-600',
    'text-cyan-600',
    'text-teal-600',
    'text-orange-600',
    'text-lime-600',
    'text-emerald-600'
  ]
  return colors[index]
}

const getPriceColorClass = getTitleColorClass

const getButtonClass = (tip: AiTips) => {
  const index = (tip.id || 0) % 10
  const buttons = [
    'bg-blue-600 hover:bg-blue-700',
    'bg-red-600 hover:bg-red-700',
    'bg-yellow-600 hover:bg-yellow-700',
    'bg-pink-600 hover:bg-pink-700',
    'bg-indigo-600 hover:bg-indigo-700',
    'bg-cyan-600 hover:bg-cyan-700',
    'bg-teal-600 hover:bg-teal-700',
    'bg-orange-600 hover:bg-orange-700',
    'bg-lime-600 hover:bg-lime-700',
    'bg-emerald-600 hover:bg-emerald-700'
  ]
  return buttons[index]
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
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');

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

/* 统一字体设置 */
.font-ma-shan {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 优化字间距 */
.tracking-wider {
  letter-spacing: 0.05em;
}

/* 优化标题阴影 */
h1.text-4xl {
  text-shadow: 
    0 2px 4px rgba(0,0,0,0.2),
    0 4px 8px rgba(0,0,0,0.1);
}

/* 优化文字阴影效果 */
.text-white {
  text-shadow: 
    0 2px 4px rgba(0,0,0,0.3),
    0 4px 8px rgba(0,0,0,0.2);
}

/* 所有文字都使用毛笔字体 */
h1, h2, h3, p, button, div {
  font-family: 'Ma Shan Zheng', cursive;
}

/* 移动端优化 */
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
</style> 