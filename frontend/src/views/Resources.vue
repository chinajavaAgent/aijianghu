<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <div class="card">
      <!-- 头部搜索和筛选 -->
      <div class="p-6 border-b border-gray-200">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between">
          <h2 class="text-2xl font-bold text-gray-900 mb-4 sm:mb-0">资源列表</h2>
          <div class="flex flex-col sm:flex-row space-y-4 sm:space-y-0 sm:space-x-4">
            <div class="relative">
              <input
                type="text"
                class="input-primary pl-10"
                placeholder="搜索资源..."
              />
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
            </div>
            <select
              v-model="selectedCategory"
              class="input-primary"
            >
              <option value="">全部分类</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
        </div>
      </div>

      <!-- 资源列表 -->
      <div class="p-6">
        <div class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
          <div
            v-for="resource in resources"
            :key="resource.id"
            class="card hover:shadow-lg transition-shadow duration-300"
          >
            <div class="p-6">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-medium text-gray-900 truncate">{{ resource.title }}</h3>
                <span
                  class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
                  :class="{
                    'bg-green-100 text-green-800': resource.requiredLevel <= userStore.level,
                    'bg-red-100 text-red-800': resource.requiredLevel > userStore.level
                  }"
                >
                  Level {{ resource.requiredLevel }}
                </span>
              </div>
              <p class="text-sm text-gray-500 mb-4 line-clamp-2">{{ resource.description }}</p>
              <div class="flex items-center justify-between">
                <div class="flex items-center text-sm text-gray-500">
                  <svg class="h-5 w-5 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                  </svg>
                  {{ resource.downloads }} 次下载
                </div>
                <button
                  class="btn-primary"
                  :class="{ 'opacity-50 cursor-not-allowed': resource.requiredLevel > userStore.level }"
                  :disabled="resource.requiredLevel > userStore.level"
                  @click="downloadResource(resource)"
                >
                  下载
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-if="resources.length === 0" class="text-center py-12">
          <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
          </svg>
          <h3 class="mt-2 text-sm font-medium text-gray-900">暂无资源</h3>
          <p class="mt-1 text-sm text-gray-500">请稍后再来查看</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import type { Category, Resource } from '../types'

const userStore = useUserStore()
const selectedCategory = ref('')
const categories = ref<Category[]>([])
const resources = ref<Resource[]>([])

const loadCategories = async () => {
  try {
    // TODO: 调用获取分类列表API
    // categories.value = await getCategories()
  } catch (error) {
    console.error('获取分类列表失败:', error)
  }
}

const loadResources = async () => {
  try {
    // TODO: 调用获取资源列表API
    // resources.value = await getResources({ categoryId: selectedCategory.value })
  } catch (error) {
    console.error('获取资源列表失败:', error)
  }
}

const downloadResource = async (resource: Resource) => {
  try {
    if (userStore.level < resource.requiredLevel) {
      alert(`您的等级不足，需要达到 ${resource.requiredLevel} 级才能下载此资源`)
      return
    }
    // TODO: 调用下载资源API
    // await downloadResource(resource.id)
  } catch (error) {
    console.error('下载资源失败:', error)
  }
}

onMounted(() => {
  loadCategories()
  loadResources()
})
</script>

<style>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style> 