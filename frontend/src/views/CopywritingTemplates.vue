<template>
  <div class="copywriting-templates min-h-screen bg-gradient-to-br from-yellow-100 via-yellow-50 to-white">
    <!-- 顶部导航 -->
    <div class="bg-white/70 backdrop-blur-md shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4 py-4">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <button @click="router.back()" class="p-2 hover:bg-yellow-100/50 rounded-lg transition-colors">
              <svg class="w-6 h-6 text-yellow-800" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
              </svg>
            </button>
            <h1 class="text-xl font-bold text-yellow-800">运营话术</h1>
          </div>
          <button @click="showAddModal = true" 
            class="px-6 py-2 bg-yellow-500 text-white rounded-full hover:bg-yellow-600 transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
            <span class="flex items-center">
              <svg class="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
              </svg>
              添加话术
            </span>
          </button>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="container mx-auto px-4 py-6">
      <!-- 话术类型选择 -->
      <div class="bg-white/60 backdrop-blur-md rounded-2xl shadow-lg mb-6 p-1">
        <div class="flex flex-wrap">
          <button v-for="category in categories" :key="category.type"
            class="px-6 py-3 text-center transition-all duration-300 rounded-xl whitespace-nowrap mx-1"
            :class="currentCategory === category.type ? 'bg-yellow-500 text-white shadow-md' : 'text-yellow-800 hover:bg-yellow-100/50'"
            @click="currentCategory = category.type">
            {{ category.name }}
            <span class="ml-2 text-sm opacity-75">({{ getCategoryCount(category.type) }})</span>
          </button>
        </div>
      </div>

      <!-- 话术列表 -->
      <div class="space-y-4">
        <div v-for="template in filteredTemplates" :key="template.id"
          class="bg-white/60 backdrop-blur-md rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1">
          <div class="p-6">
            <div class="flex justify-between items-start">
              <div class="flex-1">
                <div class="flex items-center space-x-3 mb-3">
                  <span class="px-3 py-1 text-sm rounded-full shadow-sm"
                    :class="getCategoryStyle(template.category)">
                    {{ getCategoryName(template.category) }}
                  </span>
                  <h3 class="text-lg font-semibold text-yellow-900">{{ template.title }}</h3>
                </div>
                <div class="text-gray-700 whitespace-pre-line mb-4 bg-white/40 rounded-xl p-4">{{ template.content }}</div>
                <div class="flex items-center text-sm text-yellow-700">
                  <span class="font-medium">{{ template.author }}</span>
                  <span class="mx-2">·</span>
                  <span>{{ template.date }}</span>
                  <span class="mx-2">·</span>
                  <span>使用次数：{{ template.useCount }}</span>
                </div>
              </div>
              <div class="flex space-x-2 ml-4">
                <button @click="copyTemplate(template)" 
                  class="p-2 text-yellow-600 hover:bg-yellow-100/50 rounded-lg transition-all duration-300">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 5H6a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2v-1M8 5a2 2 0 002 2h2a2 2 0 002-2M8 5a2 2 0 012-2h2a2 2 0 012 2m0 0h2a2 2 0 012 2v3m2 4H10m0 0l3-3m-3 3l3 3"></path>
                  </svg>
                </button>
                <button @click="editTemplate(template)" 
                  class="p-2 text-yellow-600 hover:bg-yellow-100/50 rounded-lg transition-all duration-300">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                  </svg>
                </button>
                <button @click="deleteTemplate(template)" 
                  class="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-all duration-300">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑话术弹窗 -->
    <van-dialog
      v-model:show="showAddModal"
      :title="editingTemplate ? '编辑话术' : '添加话术'"
      class="rounded-2xl overflow-hidden"
      :show-cancel-button="true"
      confirm-button-color="#EAB308"
      @confirm="saveTemplate"
    >
      <div class="p-6 space-y-4 bg-gradient-to-b from-yellow-50 to-white">
        <div>
          <label class="block text-sm font-medium text-yellow-800 mb-1">话术类型</label>
          <select v-model="newTemplate.category" 
            class="w-full px-4 py-2 border border-yellow-200 rounded-xl focus:ring-2 focus:ring-yellow-500 focus:border-yellow-500 bg-white/70">
            <option v-for="category in categories" :key="category.type" :value="category.type">
              {{ category.name }}
            </option>
          </select>
        </div>

        <div>
          <label class="block text-sm font-medium text-yellow-800 mb-1">标题</label>
          <input v-model="newTemplate.title" type="text" 
            class="w-full px-4 py-2 border border-yellow-200 rounded-xl focus:ring-2 focus:ring-yellow-500 focus:border-yellow-500 bg-white/70">
        </div>

        <div>
          <label class="block text-sm font-medium text-yellow-800 mb-1">话术内容</label>
          <textarea v-model="newTemplate.content" rows="6"
            class="w-full px-4 py-2 border border-yellow-200 rounded-xl focus:ring-2 focus:ring-yellow-500 focus:border-yellow-500 bg-white/70"
            placeholder="请输入话术内容，支持换行"></textarea>
        </div>

        <div>
          <label class="block text-sm font-medium text-yellow-800 mb-1">使用场景</label>
          <textarea v-model="newTemplate.scenario" rows="3"
            class="w-full px-4 py-2 border border-yellow-200 rounded-xl focus:ring-2 focus:ring-yellow-500 focus:border-yellow-500 bg-white/70"
            placeholder="描述该话术适用的场景"></textarea>
        </div>
      </div>
    </van-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Dialog } from 'vant'
import { useClipboard } from '@vueuse/core'

const router = useRouter()
const route = useRoute()
const { copy } = useClipboard()

// 话术类型定义
const categories = [
  { type: 'traffic', name: '引流话术', color: 'bg-blue-100 text-blue-800' },
  { type: 'deal', name: '成交话术', color: 'bg-green-100 text-green-800' },
  { type: 'partner', name: '合伙人引流话术', color: 'bg-purple-100 text-purple-800' },
  { type: 'service', name: '客服话术', color: 'bg-yellow-100 text-yellow-800' },
  { type: 'community', name: '社群运营话术', color: 'bg-pink-100 text-pink-800' }
]

interface Template {
  id: number
  category: string
  title: string
  content: string
  scenario: string
  author: string
  date: string
  useCount: number
  projectId: number
}

const currentCategory = ref('traffic')
const showAddModal = ref(false)
const editingTemplate = ref<Template | null>(null)

// 话术模板列表
const templates = ref<Template[]>([
  {
    id: 1,
    category: 'traffic',
    title: '朋友圈引流话术',
    content: '🔥 重磅推荐！\n想要实现财务自由，掌握正确的方法是关键。\n我们为您准备了一套完整的副业指南，让您轻松开启赚钱之旅。\n\n✨ 课程亮点：\n✅ 零基础起步\n✅ 灵活时间\n✅ 专业指导\n✅ 快速见效\n\n🎁 限时优惠中，抓住机会！\n详情咨询，请私信。',
    scenario: '适用于朋友圈日常引流，突出课程价值和稀缺性',
    author: '运营主管',
    date: '2024-01-20',
    useCount: 128,
    projectId: Number(route.query.projectId)
  },
  {
    id: 2,
    category: 'deal',
    title: '课程成交话术',
    content: '您好，看到您对我们的课程很感兴趣。\n其实现在正是开始的最好时机，因为：\n1. 市场需求大，竞争还不激烈\n2. 我们提供全程指导，确保您学会为止\n3. 现在报名还能享受早鸟优惠\n\n要不要我给您详细介绍一下课程内容？',
    scenario: '适用于私聊咨询时的课程推荐',
    author: '销售主管',
    date: '2024-01-19',
    useCount: 256,
    projectId: Number(route.query.projectId)
  },
  {
    id: 3,
    category: 'partner',
    title: '合伙人招募话术',
    content: '🤝 商机寻找合伙人\n\n你是否也在寻找：\n✅ 稳定的副业收入\n✅ 灵活的工作时间\n✅ 专业的团队支持\n\n我们提供：\n💫 完整的运营体系\n💫 专业的培训指导\n💫 持续的技术支持\n\n机会难得，只招募3名合伙人\n有兴趣了解更多吗？',
    scenario: '适用于招募合伙人时的初步沟通',
    author: '合伙人总监',
    date: '2024-01-18',
    useCount: 96,
    projectId: Number(route.query.projectId)
  }
])

// 新模板数据
const newTemplate = ref({
  category: 'traffic',
  title: '',
  content: '',
  scenario: ''
})

// 根据当前类型筛选话术
const filteredTemplates = computed(() => {
  return templates.value.filter(template => 
    template.category === currentCategory.value &&
    template.projectId === Number(route.query.projectId)
  )
})

// 获取各类型话术数量
const getCategoryCount = (type: string) => {
  return templates.value.filter(template => 
    template.category === type &&
    template.projectId === Number(route.query.projectId)
  ).length
}

// 获取类型名称
const getCategoryName = (type: string) => {
  return categories.find(category => category.type === type)?.name || type
}

// 获取类型样式
const getCategoryStyle = (type: string) => {
  return categories.find(category => category.type === type)?.color || 'bg-gray-100 text-gray-800'
}

// 复制话术
const copyTemplate = async (template: Template) => {
  await copy(template.content)
  Dialog.alert({
    message: '话术已复制到剪贴板'
  })
  // 更新使用次数
  const index = templates.value.findIndex(t => t.id === template.id)
  if (index !== -1) {
    templates.value[index] = {
      ...template,
      useCount: template.useCount + 1
    }
  }
}

// 编辑话术
const editTemplate = (template: Template) => {
  editingTemplate.value = template
  newTemplate.value = {
    category: template.category,
    title: template.title,
    content: template.content,
    scenario: template.scenario
  }
  showAddModal.value = true
}

// 删除话术
const deleteTemplate = (template: Template) => {
  Dialog.confirm({
    title: '确认删除',
    message: '确定要删除这个话术吗？',
  }).then(() => {
    templates.value = templates.value.filter(t => t.id !== template.id)
  })
}

// 保存话术
const saveTemplate = () => {
  if (editingTemplate.value) {
    const index = templates.value.findIndex(t => t.id === editingTemplate.value!.id)
    if (index !== -1) {
      templates.value[index] = {
        ...templates.value[index],
        ...newTemplate.value
      }
    }
  } else {
    templates.value.push({
      id: templates.value.length + 1,
      ...newTemplate.value,
      author: '当前用户',
      date: new Date().toISOString().split('T')[0],
      useCount: 0,
      projectId: Number(route.query.projectId)
    })
  }
  
  showAddModal.value = false
  editingTemplate.value = null
  newTemplate.value = {
    category: 'traffic',
    title: '',
    content: '',
    scenario: ''
  }
}
</script>

<style scoped>
.copywriting-templates {
  min-height: calc(100vh - 60px);
}

/* 添加玻璃拟态效果 */
.backdrop-blur-md {
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
}

/* 优化滚动条样式 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: rgba(234, 179, 8, 0.3);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(234, 179, 8, 0.5);
}
</style> 