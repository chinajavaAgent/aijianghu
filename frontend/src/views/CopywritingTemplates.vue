<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex items-center mb-6">
      <button @click="router.back()" 
        class="mr-4 p-2 hover:bg-gray-100 rounded-lg transition-colors">
        <svg class="w-6 h-6 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
        </svg>
      </button>
      <h1 class="text-2xl font-bold">文案模板</h1>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
      <!-- 左侧模板列表 -->
      <div class="space-y-4">
        <div class="bg-white rounded-xl shadow-sm p-4">
          <div class="flex items-center space-x-2 mb-4">
            <input type="text" v-model="searchKeyword" 
              class="flex-1 px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="搜索模板">
          </div>
          <div class="space-y-2">
            <button v-for="category in categories" :key="category.id"
              class="w-full px-4 py-2 text-left rounded-lg transition-colors"
              :class="selectedCategory === category.id ? 'bg-blue-50 text-blue-600' : 'hover:bg-gray-50'"
              @click="selectedCategory = category.id">
              {{ category.name }}
              <span class="float-right text-sm text-gray-500">{{ category.count }}</span>
            </button>
          </div>
        </div>
      </div>

      <!-- 中间模板内容 -->
      <div class="space-y-4">
        <div v-for="template in filteredTemplates" :key="template.id"
          class="bg-white rounded-xl shadow-sm p-6 cursor-pointer hover:shadow-md transition-shadow"
          @click="selectTemplate(template)">
          <h3 class="font-medium mb-2">{{ template.title }}</h3>
          <p class="text-gray-600 text-sm line-clamp-2">{{ template.content }}</p>
          <div class="flex items-center justify-between mt-4">
            <div class="flex items-center space-x-2">
              <span class="text-xs px-2 py-1 bg-blue-50 text-blue-600 rounded">{{ template.category }}</span>
              <span class="text-xs text-gray-500">使用次数：{{ template.useCount }}</span>
            </div>
            <button class="text-blue-600 hover:text-blue-700"
              @click.stop="copyTemplate(template)">
              复制文案
            </button>
          </div>
        </div>
      </div>

      <!-- 右侧编辑区 -->
      <div class="space-y-4">
        <div class="bg-white rounded-xl shadow-sm p-6">
          <h2 class="text-lg font-semibold mb-4">编辑文案</h2>
          <div class="space-y-4">
            <div v-if="selectedTemplate">
              <textarea v-model="editingContent" rows="10"
                class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"></textarea>
              <div class="flex justify-end space-x-2 mt-4">
                <button @click="resetTemplate" 
                  class="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded-lg transition-colors">
                  重置
                </button>
                <button @click="copyEditedTemplate" 
                  class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                  复制文案
                </button>
              </div>
            </div>
            <div v-else class="text-center text-gray-500 py-8">
              请从左侧选择模板进行编辑
            </div>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-sm p-6">
          <h2 class="text-lg font-semibold mb-4">变量说明</h2>
          <div class="space-y-2 text-sm text-gray-600">
            <p>使用 {变量名} 格式插入变量，例如：</p>
            <ul class="list-disc list-inside space-y-1">
              <li>{产品名称} - 替换为具体产品名</li>
              <li>{价格} - 替换为具体价格</li>
              <li>{日期} - 替换为具体日期</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const searchKeyword = ref('')
const selectedCategory = ref('all')
const selectedTemplate = ref<any>(null)
const editingContent = ref('')

// 分类数据
const categories = [
  { id: 'all', name: '全部模板', count: 12 },
  { id: 'product', name: '产品介绍', count: 4 },
  { id: 'promotion', name: '活动推广', count: 3 },
  { id: 'notice', name: '通知公告', count: 2 },
  { id: 'greeting', name: '问候语', count: 3 }
]

// 模板数据
const templates = [
  {
    id: 1,
    title: '产品介绍模板',
    content: '【{产品名称}】\n价格：{价格}元\n产品特点：\n1. 优质材料，精工制作\n2. 时尚设计，实用美观\n3. 售后无忧，品质保证\n\n限时优惠，欢迎咨询！',
    category: '产品介绍',
    useCount: 128
  },
  {
    id: 2,
    title: '活动推广模板',
    content: '🎉重磅活动来袭！\n\n{活动名称}\n活动时间：{开始日期}至{结束日期}\n活动内容：\n✨ {优惠内容1}\n✨ {优惠内容2}\n\n🎁更多惊喜等你来！',
    category: '活动推广',
    useCount: 256
  },
  {
    id: 3,
    title: '通知公告模板',
    content: '【重要通知】\n\n亲爱的{称谓}：\n\n{通知内容}\n\n如有疑问，请联系客服。\n\n{公司名称}\n{日期}',
    category: '通知公告',
    useCount: 89
  }
]

// 根据搜索关键词和分类筛选模板
const filteredTemplates = computed(() => {
  return templates.filter(template => {
    const matchKeyword = template.title.includes(searchKeyword.value) || 
                        template.content.includes(searchKeyword.value)
    const matchCategory = selectedCategory.value === 'all' || 
                         template.category === categories.find(c => c.id === selectedCategory.value)?.name
    return matchKeyword && matchCategory
  })
})

// 选择模板
const selectTemplate = (template: any) => {
  selectedTemplate.value = template
  editingContent.value = template.content
}

// 复制模板内容
const copyTemplate = (template: any) => {
  navigator.clipboard.writeText(template.content)
  ElMessage.success('文案已复制到剪贴板')
}

// 复制编辑后的内容
const copyEditedTemplate = () => {
  navigator.clipboard.writeText(editingContent.value)
  ElMessage.success('文案已复制到剪贴板')
}

// 重置模板内容
const resetTemplate = () => {
  if (selectedTemplate.value) {
    editingContent.value = selectedTemplate.value.content
  }
}
</script> 