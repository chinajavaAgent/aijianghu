<template>
  <div class="operation-materials">
    <div class="header">
      <h1 class="text-xl font-bold mb-4">运营物料管理</h1>
    </div>
    
    <!-- 项目选择器 -->
    <div class="mb-6">
      <div class="bg-white rounded-lg p-4 shadow-sm">
        <h2 class="text-lg font-semibold mb-3">选择项目</h2>
        <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
          <div v-for="project in projects" 
            :key="project.id"
            class="project-card p-3 rounded-lg border-2 cursor-pointer transition-all duration-200"
            :class="selectedProject === project.id ? 'border-blue-500 bg-blue-50' : 'border-gray-200 hover:border-blue-300'"
            @click="selectProject(project.id)">
            <div class="flex items-center space-x-2">
              <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center">
                <span class="text-blue-600 font-medium">{{ project.name.charAt(0) }}</span>
              </div>
              <span class="text-sm font-medium">{{ project.name }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 物料卡片列表 -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
      <div class="material-card" @click="navigateTo('/poster-generator', selectedProject)">
        <div class="icon-wrapper bg-blue-100 text-blue-600">
          <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
          </svg>
        </div>
        <h3 class="text-lg font-semibold">海报生成</h3>
        <p class="text-sm text-gray-600">生成分享海报和宣传图片</p>
      </div>
      
      <div class="material-card" @click="navigateTo('/qr-generator', selectedProject)">
        <div class="icon-wrapper bg-purple-100 text-purple-600">
          <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v1m6 11h2m-6 0h-2v4m0-11v3m0 0h.01M12 12h4.01M16 20h4M4 12h4m12 0h.01M5 8h2a1 1 0 001-1V5a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1zm12 0h2a1 1 0 001-1V5a1 1 0 00-1-1h-2a1 1 0 00-1 1v2a1 1 0 001 1zM5 20h2a1 1 0 001-1v-2a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1z"></path>
          </svg>
        </div>
        <h3 class="text-lg font-semibold">二维码生成</h3>
        <p class="text-sm text-gray-600">生成带有自定义样式的二维码</p>
      </div>

      <div class="material-card" @click="navigateTo('/copywriting', selectedProject)">
        <div class="icon-wrapper bg-green-100 text-green-600">
          <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
          </svg>
        </div>
        <h3 class="text-lg font-semibold">运营话术</h3>
        <p class="text-sm text-gray-600">常用运营话术和文案模板</p>
      </div>

      <div class="material-card" @click="navigateTo('/tutorials', selectedProject)">
        <div class="icon-wrapper bg-yellow-100 text-yellow-600">
          <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
          </svg>
        </div>
        <h3 class="text-lg font-semibold">运营教程</h3>
        <p class="text-sm text-gray-600">项目运营指南和技巧</p>
      </div>
    </div>
    
    <!-- 最近使用的物料 -->
    <div class="recent-materials" v-if="selectedProject">
      <h2 class="text-lg font-semibold mb-4">最近使用</h2>
      <div class="space-y-4">
        <div v-for="(item, index) in filteredRecentMaterials" :key="index" class="recent-item">
          <div class="flex items-center justify-between p-4 bg-white rounded-lg shadow">
            <div class="flex items-center">
              <img :src="item.thumbnail" alt="缩略图" class="w-12 h-12 object-cover rounded">
              <div class="ml-4">
                <h4 class="font-medium">{{ item.title }}</h4>
                <p class="text-sm text-gray-500">{{ item.date }}</p>
              </div>
            </div>
            <button class="text-blue-600" @click="continueEdit(item)">继续编辑</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const selectedProject = ref<number | null>(null)

// 项目列表数据
const projects = [
  { id: 1, name: 'AI锦囊' },
  { id: 2, name: 'AI群江湖' },
  { id: 3, name: '创业项目' },
  { id: 4, name: '副业项目' }
]

// 最近使用的物料数据
const recentMaterials = ref([
  {
    title: '项目介绍海报',
    date: '2024-01-20',
    thumbnail: '/placeholder.png',
    type: 'poster',
    id: 1,
    projectId: 1
  },
  {
    title: '推广二维码',
    date: '2024-01-19', 
    thumbnail: '/placeholder.png',
    type: 'qrcode',
    id: 2,
    projectId: 1
  },
  {
    title: 'AI江湖海报',
    date: '2024-01-18',
    thumbnail: '/placeholder.png',
    type: 'poster',
    id: 3,
    projectId: 2
  },
  {
    title: '新人引导话术',
    date: '2024-01-17',
    thumbnail: '/placeholder.png',
    type: 'copywriting',
    id: 4,
    projectId: 1
  },
  {
    title: '项目运营指南',
    date: '2024-01-16',
    thumbnail: '/placeholder.png',
    type: 'tutorial',
    id: 5,
    projectId: 2
  }
])

// 根据选中的项目过滤最近使用的物料
const filteredRecentMaterials = computed(() => {
  if (!selectedProject.value) return []
  return recentMaterials.value.filter(item => item.projectId === selectedProject.value)
})

const selectProject = (projectId: number) => {
  selectedProject.value = projectId
}

const navigateTo = (path: string, projectId: number | null) => {
  if (!projectId) {
    alert('请先选择项目')
    return
  }
  router.push({
    path,
    query: { projectId }
  })
}

const continueEdit = (item: any) => {
  const pathMap = {
    poster: '/poster-generator',
    qrcode: '/qr-generator',
    copywriting: '/copywriting',
    tutorial: '/tutorials'
  }
  const path = pathMap[item.type as keyof typeof pathMap]
  router.push({ 
    path, 
    query: { 
      id: item.id,
      projectId: item.projectId
    }
  })
}
</script>

<style scoped>
.operation-materials {
  padding: 20px;
}

.material-card {
  @apply p-6 bg-white rounded-lg shadow cursor-pointer transition-transform hover:scale-105;
}

.icon-wrapper {
  @apply w-12 h-12 rounded-full flex items-center justify-center mb-4;
}

.recent-item {
  @apply transition-transform hover:scale-[1.02];
}
</style> 