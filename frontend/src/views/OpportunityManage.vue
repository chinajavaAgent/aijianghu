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
      class="project-dialog"
    >
      <div class="p-4">
        <div class="mb-4">
          <div class="flex justify-between items-center mb-4">
            <h3 class="font-medium text-lg">项目列表</h3>
            <button @click="addProject" 
              class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors">
              添加项目
            </button>
          </div>
          <div class="space-y-6">
            <div v-for="(project, index) in projectForm.projects" :key="index"
              class="bg-gray-50 rounded-lg p-6 border border-gray-200">
              <div class="flex justify-between items-start mb-4">
                <h4 class="font-medium text-gray-800">项目 {{ index + 1 }}</h4>
                <button @click="removeProject(index)"
                  class="text-sm text-red-600 hover:text-red-800">
                  删除项目
                </button>
              </div>
              
              <!-- 项目基本信息 -->
              <div class="space-y-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-1">项目名称</label>
                  <input v-model="project.title" type="text"
                    class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
                    placeholder="请输入项目名称">
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-1">项目介绍</label>
                  <textarea v-model="project.description"
                    class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
                    rows="3"
                    placeholder="请输入项目详细介绍"></textarea>
                </div>

                <!-- 成功案例 -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">成功案例</label>
                  <div class="space-y-4">
                    <div v-for="(caseItem, caseIndex) in project.cases" :key="caseIndex"
                      class="bg-white p-4 rounded-lg border border-gray-200">
                      <div class="flex justify-between items-start mb-2">
                        <span class="text-sm text-gray-600">案例 {{ caseIndex + 1 }}</span>
                        <button @click="removeCase(project, caseIndex)"
                          class="text-sm text-red-600 hover:text-red-800">
                          删除案例
                        </button>
                      </div>
                      <textarea v-model="caseItem.description"
                        class="w-full px-3 py-2 border rounded-lg mb-2"
                        rows="2"
                        placeholder="请输入案例描述"></textarea>
                      <div class="space-y-2">
                        <div v-if="caseItem.imageUrl" class="relative">
                          <img :src="caseItem.imageUrl" 
                            class="w-full h-48 object-cover rounded-lg" 
                            alt="案例图片">
                          <button @click="caseItem.imageUrl = ''"
                            class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center bg-red-500 text-white rounded-full hover:bg-red-600">
                            <i class="fas fa-times"></i>
                          </button>
                        </div>
                        <div v-else class="border-2 border-dashed border-gray-300 rounded-lg p-4">
                          <input type="file" 
                            accept="image/*" 
                            class="hidden" 
                            :ref="el => { if (el) imageInputRefs[`${index}-${caseIndex}`] = (el as HTMLInputElement) }"
                            @change="handleImageUpload($event, caseItem)">
                          <button @click="triggerImageUpload(index, caseIndex)"
                            class="w-full h-32 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700">
                            <i class="fas fa-camera text-2xl mb-2"></i>
                            <span class="text-sm">点击上传案例图片</span>
                          </button>
                        </div>
                      </div>
                    </div>
                    <button @click="addCase(project)"
                      class="w-full px-4 py-2 text-sm text-blue-600 border border-blue-600 rounded-lg hover:bg-blue-50">
                      添加案例
                    </button>
                  </div>
                </div>

                <!-- 项目视频 -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-1">项目视频</label>
                  <div class="flex items-center space-x-2">
                    <input v-model="project.videoUrl" type="text"
                      class="flex-1 px-3 py-2 border rounded-lg"
                      placeholder="请输入视频URL">
                    <button class="px-3 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200">
                      上传视频
                    </button>
                  </div>
                </div>

                <!-- 项目福利 -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">项目福利</label>
                  <div class="space-y-2">
                    <div v-for="(benefit, benefitIndex) in project.benefits" :key="benefitIndex"
                      class="flex items-center space-x-2">
                      <input v-model="project.benefits[benefitIndex]" type="text"
                        class="flex-1 px-3 py-2 border rounded-lg"
                        placeholder="请输入福利内容">
                      <button @click="removeBenefit(project, benefitIndex)"
                        class="text-sm text-red-600 hover:text-red-800">
                        删除
                      </button>
                    </div>
                    <button @click="addBenefit(project)"
                      class="w-full px-4 py-2 text-sm text-blue-600 border border-blue-600 rounded-lg hover:bg-blue-50">
                      添加福利
                    </button>
                  </div>
                </div>
              </div>
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
    cases: Array<{
      description: string
      imageUrl: string
    }>
    videoUrl: string
    benefits: string[]
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
    description: '',
    cases: [],
    videoUrl: '',
    benefits: []
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

// 添加案例
const addCase = (project: any) => {
  project.cases.push({
    description: '',
    imageUrl: ''
  })
}

// 删除案例
const removeCase = (project: any, index: number) => {
  project.cases.splice(index, 1)
}

// 添加福利
const addBenefit = (project: any) => {
  project.benefits.push('')
}

// 删除福利
const removeBenefit = (project: any, index: number) => {
  project.benefits.splice(index, 1)
}

// 图片上传相关
const imageInputRefs = ref<{ [key: string]: HTMLInputElement }>({})

// 触发图片上传
const triggerImageUpload = (projectIndex: number, caseIndex: number) => {
  const inputRef = imageInputRefs.value[`${projectIndex}-${caseIndex}`]
  if (inputRef) {
    inputRef.click()
  }
}

// 处理图片上传
const handleImageUpload = (event: Event, caseItem: any) => {
  const input = event.target as HTMLInputElement
  if (input.files && input.files[0]) {
    const file = input.files[0]
    const reader = new FileReader()
    reader.onload = (e) => {
      if (e.target?.result) {
        caseItem.imageUrl = e.target.result as string
      }
    }
    reader.readAsDataURL(file)
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
}

.project-dialog {
  :deep(.van-dialog) {
    width: 90%;
    max-width: 800px;
  }
}
</style> 