<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <h1 class="text-xl font-bold text-gray-800 py-4">锦囊管理员维护</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 管理功能区 -->
      <div class="bg-white rounded-xl shadow-md p-6 mb-6">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-lg font-semibold">管理员列表</h2>
          <button class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
            添加管理员
          </button>
        </div>

        <!-- 管理员列表 -->
        <div class="space-y-4">
          <div v-for="admin in admins" :key="admin.id" 
            class="border rounded-lg p-4 hover:shadow-md transition-shadow">
            <div class="flex justify-between items-start">
              <div>
                <h3 class="font-medium text-lg">{{ admin.name }}</h3>
                <p class="text-gray-600 text-sm mt-1">角色：{{ admin.role }}</p>
                <p class="text-gray-600 text-sm">手机：{{ admin.phone }}</p>
              </div>
              <div class="flex space-x-2">
                <button class="px-3 py-1 text-sm text-blue-600 border border-blue-600 rounded hover:bg-blue-50">
                  编辑
                </button>
                <button class="px-3 py-1 text-sm text-red-600 border border-red-600 rounded hover:bg-red-50">
                  删除
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 模拟数据
const admins = ref([
  {
    id: 1,
    name: '管理员1',
    role: '超级管理员',
    phone: '13800138001'
  },
  {
    id: 2,
    name: '管理员2',
    role: '内容管理员',
    phone: '13800138002'
  },
  {
    id: 3,
    name: '管理员3',
    role: '运营管理员',
    phone: '13800138003'
  }
])
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 