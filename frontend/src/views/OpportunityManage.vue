<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white/80 backdrop-blur-sm shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <h1 class="text-xl font-bold text-gray-800 py-4">AI锦囊管理</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 锦囊列表 -->
      <div class="bg-white/80 backdrop-blur-sm rounded-xl shadow-md p-6">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-lg font-semibold">锦囊列表</h2>
          <button @click="showDialog = true" 
            class="px-4 py-2 bg-gradient-to-r from-blue-500 to-blue-600 text-white rounded-lg hover:from-blue-600 hover:to-blue-700 transition-all shadow-sm">
            添加锦囊
          </button>
        </div>

        <!-- 锦囊列表 -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="tip in tipsList" :key="tip.id" 
            class="bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow border border-gray-100">
            <div class="p-6">
              <div class="flex justify-between items-start">
                <div class="flex-1">
                  <div class="flex items-center space-x-3 mb-3">
                    <h3 class="text-lg font-semibold text-gray-800">{{ tip.title }}</h3>
                    <div class="flex items-center px-3 py-1 bg-gradient-to-r from-amber-500 to-orange-500 rounded-lg shadow-sm">
                      <span class="text-lg text-white mr-1">¥</span>
                      <span class="text-white font-semibold">{{ tip.price }}</span>
                    </div>
                    <div class="px-3 py-1 bg-blue-50 rounded-lg">
                      <span class="text-blue-600 font-medium">
                        <i class="fas fa-crown mr-1"></i>
                        Lv.{{ tip.requiredLevel }}
                      </span>
                    </div>
                  </div>
                  <p class="text-gray-600 text-sm">{{ tip.description }}</p>
                </div>
                <div class="flex flex-col space-y-2">
                  <button @click="editTip(tip)" 
                    class="px-3 py-1 text-sm text-blue-600 border border-blue-600 rounded-lg hover:bg-blue-50 transition-colors">
                    编辑
                  </button>
                  <button @click="deleteTip(tip.id)" 
                    class="px-3 py-1 text-sm text-red-600 border border-red-600 rounded-lg hover:bg-red-50 transition-colors">
                    删除
                  </button>
                  <button @click="openProjectDialog(tip)" 
                    class="px-3 py-1 text-sm text-green-600 border border-green-600 rounded-lg hover:bg-green-50 transition-colors">
                    项目
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑锦囊弹窗 -->
    <van-dialog
      v-model:show="showDialog"
      :title="isEditing ? '编辑锦囊' : '添加锦囊'"
      show-cancel-button
      @confirm="handleSubmit"
    >
      <div class="p-4 space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">锦囊名称</label>
          <input v-model="form.title" type="text" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="请输入锦囊名称">
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">锦囊描述</label>
          <textarea v-model="form.description" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            rows="3" placeholder="请输入锦囊描述"></textarea>
        </div>
        <div class="grid grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">价格（元）</label>
            <input v-model="form.price" type="number" step="0.01" min="0" 
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="请输入价格">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">所需等级</label>
            <input v-model="form.requiredLevel" type="number" min="1" 
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="请输入所需等级">
          </div>
        </div>
      </div>
    </van-dialog>

    <!-- 项目管理弹窗 -->
    <van-dialog
      v-model:show="projectDialogVisible"
      title="项目管理"
      show-cancel-button
      @confirm="handleProjectSubmit"
    >
      <div class="p-4">
        <div class="mb-4">
          <div class="flex justify-between items-center mb-2">
            <h3 class="font-medium">项目列表</h3>
            <button @click="addProject" 
              class="text-sm text-blue-600 hover:text-blue-800">
              添加项目
            </button>
          </div>
          <div class="space-y-3">
            <div v-for="(project, index) in projectForm.projects" :key="index"
              class="bg-gray-50 rounded-lg p-3">
              <div class="flex justify-between items-start mb-2">
                <input v-model="project.title" type="text"
                  class="flex-1 px-2 py-1 border rounded mr-2"
                  placeholder="项目名称">
                <button @click="removeProject(index)"
                  class="text-sm text-red-600 hover:text-red-800">
                  删除
                </button>
              </div>
              <textarea v-model="project.description"
                class="w-full px-2 py-1 border rounded mt-2"
                rows="2"
                placeholder="项目描述"></textarea>
            </div>
          </div>
        </div>
      </div>
    </van-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { Dialog, showToast } from 'vant'
import { createAiTips, updateAiTips, deleteAiTips, getAiTipsList } from '@/api/tips'
import type { AiTips } from '@/types/tips'

// 锦囊列表数据
const tipsList = ref<AiTips[]>([])

// 添加/编辑锦囊相关
const showDialog = ref(false)
const isEditing = ref(false)
const form = reactive({
  id: null as number | null,
  title: '',
  description: '',
  price: 0,
  requiredLevel: 1
})

// 项目管理相关
const projectDialogVisible = ref(false)
const currentTip = ref<AiTips | null>(null)
const projectForm = reactive({
  projects: [] as Array<{
    title: string
    description: string
  }>
})

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  Object.assign(form, {
    id: null,
    title: '',
    description: '',
    price: 0,
    requiredLevel: 1
  })
  showDialog.value = true
}

// 编辑锦囊
const editTip = (tip: AiTips) => {
  isEditing.value = true
  Object.assign(form, {
    id: tip.id,
    title: tip.title,
    description: tip.description,
    price: tip.price,
    requiredLevel: tip.requiredLevel
  })
  showDialog.value = true
}

// 显示项目管理
const openProjectDialog = (tip: AiTips) => {
  currentTip.value = tip
  // 加载项目列表
  projectForm.projects = tip.content ? JSON.parse(tip.content) : []
  projectDialogVisible.value = true
}

// 添加项目
const addProject = () => {
  projectForm.projects.push({
    title: '',
    description: ''
  })
}

// 删除项目
const removeProject = (index: number) => {
  projectForm.projects.splice(index, 1)
}

// 提交项目表单
const handleProjectSubmit = async () => {
  if (!currentTip.value) return
  
  try {
    await updateAiTips(currentTip.value.id, {
      content: JSON.stringify(projectForm.projects)
    })
    showToast('保存成功')
    projectDialogVisible.value = false
    loadTipsList()
  } catch (error) {
    console.error('保存项目失败:', error)
  }
}

// 删除锦囊
const deleteTip = async (id: number) => {
  try {
    await Dialog.confirm({
      title: '确认删除',
      message: '确定要删除这个锦囊吗？'
    })
    await deleteAiTips(id)
    showToast('删除成功')
    loadTipsList()
  } catch (error) {
    console.error('删除失败:', error)
  }
}

// 提交锦囊表单
const handleSubmit = async () => {
  try {
    if (!form.title) {
      showToast('请输入锦囊名称')
      return false
    }

    const tipData = {
      title: form.title,
      description: form.description,
      price: form.price,
      requiredLevel: form.requiredLevel,
      status: 1 // 默认发布状态
    }

    if (isEditing.value && form.id) {
      await updateAiTips(form.id, tipData)
      showToast('更新成功')
    } else {
      await createAiTips(tipData)
      showToast('创建成功')
    }
    showDialog.value = false
    loadTipsList()
  } catch (error) {
    console.error('操作失败:', error)
  }
}

// 加载锦囊列表
const loadTipsList = async () => {
  try {
    const response = await getAiTipsList({
      page: 1,
      size: 100
    })
    tipsList.value = response.data.records || []
  } catch (error) {
    console.error('加载锦囊列表失败:', error)
    showToast('加载失败，请重试')
  }
}

// 页面加载时获取数据
onMounted(() => {
  loadTipsList()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
}
</style> 