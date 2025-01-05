<!-- AI锦囊列表页面 -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 py-8">
    <div class="container mx-auto px-4">
      <!-- 页面标题 -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-800">AI锦囊</h1>
        <p class="text-gray-600 mt-2">精选AI工具与资源，助你快速掌握AI技能</p>
      </div>

      <!-- 分类筛选 -->
      <div class="flex flex-wrap justify-center gap-4 mb-8">
        <button
          v-for="cat in categories"
          :key="cat"
          class="px-4 py-2 rounded-full text-sm font-medium transition-all duration-300"
          :class="[
            currentCategory === cat
              ? 'bg-gradient-to-r from-cyan-500 to-fuchsia-500 text-white shadow-lg'
              : 'bg-white text-gray-600 hover:shadow-md'
          ]"
          @click="changeCategory(cat)"
        >
          {{ cat }}
        </button>
      </div>

      <!-- 锦囊列表 -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="tip in tipsList"
          :key="tip.id"
          class="bg-white rounded-xl shadow-md hover:shadow-xl transition-all duration-300 overflow-hidden"
        >
          <div class="p-6">
            <h2 class="text-xl font-bold text-gray-800 mb-2">{{ tip.title }}</h2>
            <p class="text-gray-600 text-sm mb-4">{{ tip.description }}</p>
            <div class="flex items-center justify-between text-sm text-gray-500">
              <div class="flex items-center space-x-4">
                <span class="flex items-center">
                  <i class="fas fa-eye mr-1"></i>
                  {{ tip.views }}
                </span>
                <span class="flex items-center">
                  <i class="fas fa-heart mr-1"></i>
                  {{ tip.likes }}
                </span>
              </div>
              <span class="px-2 py-1 bg-cyan-50 text-cyan-600 rounded">
                {{ tip.category }}
              </span>
            </div>
          </div>
          <div class="px-6 py-4 bg-gray-50 flex justify-between items-center">
            <span class="text-sm text-gray-500">
              等级要求: Lv.{{ tip.requiredLevel }}
            </span>
            <router-link
              :to="'/tips/' + tip.id"
              class="px-4 py-2 bg-gradient-to-r from-cyan-500 to-fuchsia-500 text-white rounded-full text-sm hover:shadow-lg transition-all duration-300"
            >
              查看详情
            </router-link>
          </div>
        </div>
      </div>

      <!-- 分页 -->
      <div class="flex justify-center mt-8">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="total"
          :page-sizes="[12, 24, 36]"
          layout="total, sizes, prev, pager, next"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { AiTips, AiTipsCategory } from '@/types/tips'
import { getAiTipsList } from '@/api/tips'

// 分类列表
const categories = ['全部', 'AI工具', 'AI应用', 'AI变现', '流量获取', '其他'] as const
const currentCategory = ref<typeof categories[number]>('全部')

// 分页数据
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)
const tipsList = ref<AiTips[]>([])

// 获取锦囊列表
const fetchTipsList = async () => {
  try {
    const response = await getAiTipsList({
      page: currentPage.value,
      size: pageSize.value,
      category: currentCategory.value === '全部' ? undefined : currentCategory.value
    })
    tipsList.value = response.data.records
    total.value = response.data.total
  } catch (error) {
    console.error('获取锦囊列表失败:', error)
  }
}

// 切换分类
const changeCategory = (category: typeof categories[number]) => {
  currentCategory.value = category
  currentPage.value = 1
  fetchTipsList()
}

// 分页大小改变
const handleSizeChange = (val: number) => {
  pageSize.value = val
  fetchTipsList()
}

// 页码改变
const handleCurrentChange = (val: number) => {
  currentPage.value = val
  fetchTipsList()
}

// 页面加载时获取数据
onMounted(() => {
  fetchTipsList()
})
</script> 