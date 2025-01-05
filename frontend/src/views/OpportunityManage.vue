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

          <!-- 项目列表 -->
          <div class="space-y-4">
            <div v-for="(project, index) in projectForm.projects" :key="index"
              class="bg-gray-50 rounded-lg border border-gray-200 overflow-hidden">
              <!-- 项目头部 -->
              <div class="flex justify-between items-center p-4 bg-white border-b border-gray-200">
                <div class="flex items-center space-x-3">
                  <span class="font-medium text-gray-800">{{ project.name || `项目 ${index + 1}` }}</span>
                  <span class="text-sm text-gray-500">{{ project.cases?.length || 0 }}个案例</span>
                  <span class="text-sm text-gray-500">{{ project.benefits?.length || 0 }}项福利</span>
                </div>
                <div class="flex items-center space-x-2">
                  <button @click="project.isExpanded = !project.isExpanded"
                    class="text-blue-600 hover:text-blue-800">
                    {{ project.isExpanded ? '收起' : '展开' }}
                  </button>
                  <button @click="removeProject(index)"
                    class="text-red-600 hover:text-red-800">
                    删除
                  </button>
                </div>
              </div>

              <!-- 项目详情（可展开/收起） -->
              <div v-show="project.isExpanded" class="p-4">
                <!-- 步骤导航 -->
                <div class="flex border-b border-gray-200 mb-4">
                  <button 
                    v-for="(step, stepIndex) in projectSteps" 
                    :key="stepIndex"
                    @click="project.currentStep = stepIndex"
                    :class="[
                      'px-4 py-2 text-sm font-medium -mb-px',
                      project.currentStep === stepIndex
                        ? 'text-blue-600 border-b-2 border-blue-600'
                        : 'text-gray-500 hover:text-gray-700'
                    ]"
                  >
                    {{ step }}
                  </button>
                </div>

                <!-- 基本信息 -->
                <div v-if="project.currentStep === 0" class="space-y-4">
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">项目名称</label>
                    <input v-model="project.name" type="text"
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
                </div>

                <!-- 成功案例 -->
                <div v-if="project.currentStep === 1">
                  <div class="space-y-4 max-h-[400px] overflow-y-auto pr-2">
                    <div v-for="(caseItem, caseIndex) in project.cases" :key="caseIndex"
                      class="bg-white p-4 rounded-lg border border-gray-200">
                      <div class="flex justify-between items-start mb-2">
                        <span class="text-sm font-medium text-gray-700">案例 {{ caseIndex + 1 }}</span>
                        <div class="flex items-center space-x-2">
                          <button @click="saveCase(project, caseItem, caseIndex)"
                            class="text-sm text-blue-600 hover:text-blue-800">
                            保存
                          </button>
                          <button @click="removeCase(project, caseIndex)"
                            class="text-sm text-red-600 hover:text-red-800">
                            删除
                          </button>
                        </div>
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
                        <div v-else class="border-2 border-dashed border-gray-300 rounded-lg p-2">
                          <input type="file" 
                            accept="image/*" 
                            class="hidden" 
                            :ref="el => { if (el) imageInputRefs[`${index}-${caseIndex}`] = (el as HTMLInputElement) }"
                            @change="handleImageUpload($event, project, caseItem)">
                          <button @click="triggerImageUpload(index, caseIndex)"
                            class="w-full h-24 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700">
                            <i class="fas fa-camera text-xl mb-1"></i>
                            <span class="text-xs">点击上传案例图片</span>
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
                <div v-if="project.currentStep === 2">
                  <div class="space-y-2">
                    <div v-if="project.videoUrl" class="relative">
                      <video 
                        :src="project.videoUrl" 
                        class="w-full rounded-lg" 
                        controls>
                      </video>
                      <button @click="project.videoUrl = ''"
                        class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center bg-red-500 text-white rounded-full hover:bg-red-600">
                        <i class="fas fa-times"></i>
                      </button>
                    </div>
                    <div v-else class="border-2 border-dashed border-gray-300 rounded-lg p-4">
                      <input type="file" 
                        accept="video/*" 
                        class="hidden" 
                        :ref="el => { if (el) videoInputRefs[index] = (el as HTMLInputElement) }"
                        @change="handleVideoUpload($event, project)">
                      <button @click="triggerVideoUpload(index)"
                        class="w-full h-32 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700">
                        <i class="fas fa-video text-2xl mb-2"></i>
                        <span class="text-sm">点击上传项目视频</span>
                      </button>
                    </div>
                  </div>
                </div>

                <!-- 项目福利 -->
                <div v-if="project.currentStep === 3">
                  <div class="space-y-2">
                    <div v-for="(benefit, benefitIndex) in project.benefits" :key="benefitIndex"
                      class="flex items-center space-x-2">
                      <input v-model="benefit.content" type="text"
                        class="flex-1 px-3 py-2 border rounded-lg"
                        placeholder="请输入福利内容">
                      <button @click="saveBenefit(project, benefit, benefitIndex)"
                        class="text-sm text-blue-600 hover:text-blue-800">
                        保存
                      </button>
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
import { showConfirmDialog, showToast } from 'vant'
import { createAiTips, updateAiTips, deleteAiTips, getAiTipsList } from '@/api/tips'
import type { AiTips } from '@/types/tips'
import {
  getProjects,
  createProject,
  updateProject,
  deleteProject,
  addCase as addProjectCase,
  updateCase,
  deleteCase,
  uploadCaseImage,
  uploadProjectVideo,
  addBenefit as addProjectBenefit,
  updateBenefit,
  deleteBenefit
} from '@/api/project'
import type { Project, ProjectCase, ProjectBenefit, ProjectUpdateDto } from '@/types/project'

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
  projects: [] as Array<Project>
})

// 项目步骤
const projectSteps = ['基本信息', '成功案例', '项目视频', '项目福利']

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  // 清空表单数据
  Object.assign(form, {
    id: null,
    title: '',
    description: '',
    price: 0,
    requiredLevel: 1
  })
  // 清空项目相关数据
  projectForm.projects = []
  currentTip.value = null
  showDialog.value = true
}

// 编辑锦囊
const editTip = (tip: AiTips) => {
  isEditing.value = true
  // 设置表单数据
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
const openProjectDialog = async (tip: AiTips) => {
  currentTip.value = tip
  try {
    // 加载项目列表，传入tipId参数
    const response = await getProjects(1, 10, tip.id)
    if (response.data && response.data.records) {
      projectForm.projects = response.data.records.map((project: Project) => ({
        ...project,
        isExpanded: true,
        currentStep: 0
      }))
    } else {
      projectForm.projects = []
    }
    projectDialogVisible.value = true
  } catch (error) {
    console.error('加载项目列表失败:', error)
    showToast('加载失败，请重试')
  }
}

// 添加项目
const addProject = () => {
  if (!currentTip.value) return
  
  const newProject: Project = {
    tipId: currentTip.value.id,
    name: '',
    description: '',
    videoUrl: '',
    status: 0,
    views: 0,
    likes: 0,
    cases: [],
    benefits: [],
    isExpanded: true,
    currentStep: 0
  }
  projectForm.projects.push(newProject)
}

// 删除项目
const removeProject = async (index: number) => {
  const project = projectForm.projects[index]
  if (!project.id) return

  try {
    await showConfirmDialog({
      title: '确认删除',
      message: '确定要删除这个项目吗？'
    })
    await deleteProject(project.id)
    projectForm.projects.splice(index, 1)
    showToast('删除成功')
  } catch (error) {
    console.error('删除项目失败:', error)
    if (error !== 'cancel') {
      showToast('删除失败，请重试')
    }
  }
}

// 提交项目表单
const handleProjectSubmit = async () => {
  if (!currentTip.value) return
  
  try {
    // 更新所有项目
    await Promise.all(projectForm.projects.map(async (project) => {
      if (project.id) {
        const updateData = {
          name: project.name,
          description: project.description,
          videoUrl: project.videoUrl,
          status: project.status
        }
        await updateProject(project.id, updateData)
      } else {
        // 如果是新项目，则创建，并确保设置tipId
        const newProject = {
          ...project,
          tipId: currentTip.value!.id
        }
        await createProject(newProject)
      }
    }))
    showToast('保存成功')
    projectDialogVisible.value = false
  } catch (error) {
    console.error('保存项目失败:', error)
    showToast('保存失败，请重试')
  }
}

// 删除锦囊
const deleteTip = async (id: number) => {
  try {
    await showConfirmDialog({
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
    
    // 清空所有相关数据
    Object.assign(form, {
      id: null,
      title: '',
      description: '',
      price: 0,
      requiredLevel: 1
    })
    projectForm.projects = []
    currentTip.value = null
    isEditing.value = false
    showDialog.value = false
    
    // 重新加载列表
    loadTipsList()
  } catch (error) {
    console.error('操作失败:', error)
    showToast('操作失败，请重试')
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
const addCase = async (project: Project) => {
  // 只在本地添加一个临时案例对象，不调用接口
  const newCase: ProjectCase = {
    description: '',
    imageUrl: ''
  }
  project.cases.push(newCase)
}

// 保存案例
const saveCase = async (project: Project, caseItem: ProjectCase, index: number) => {
  if (!project.id) return
  
  try {
    if (!caseItem.description) {
      showToast('请输入案例描述')
      return
    }
    
    // 如果是新案例，则调用添加接口
    if (!caseItem.id) {
      const response = await addProjectCase(project.id, caseItem)
      // 用返回的数据更新本地案例对象
      Object.assign(project.cases[index], response.data)
      showToast('添加成功')
    } else {
      // 如果是已有案例，则调用更新接口
      await updateCase(project.id, caseItem.id, caseItem)
      showToast('更新成功')
    }
  } catch (error) {
    console.error('保存案例失败:', error)
    showToast('保存失败，请重试')
  }
}

// 删除案例
const removeCase = async (project: Project, index: number) => {
  const caseItem = project.cases[index]
  
  // 如果案例没有id，说明还未保存到数据库，直接在前端删除
  if (!caseItem.id) {
    project.cases.splice(index, 1)
    return
  }

  // 如果案例已经保存到数据库，需要调用接口删除
  if (!project.id) return

  try {
    await showConfirmDialog({
      title: '确认删除',
      message: '确定要删除这个案例吗？'
    })
    await deleteCase(project.id, caseItem.id)
    project.cases.splice(index, 1)
    showToast('删除成功')
  } catch (error) {
    console.error('删除案例失败:', error)
    if (error !== 'cancel') {
      showToast('删除失败，请重试')
    }
  }
}

// 添加福利
const addBenefit = (project: Project) => {
  // 只在本地添加一个临时福利对象，不调用接口
  const newBenefit: ProjectBenefit = {
    content: ''
  }
  project.benefits.push(newBenefit)
}

// 保存福利
const saveBenefit = async (project: Project, benefit: ProjectBenefit, index: number) => {
  if (!project.id) return
  
  try {
    if (!benefit.content) {
      showToast('请输入福利内容')
      return
    }
    
    // 如果是新福利，则调用添加接口
    if (!benefit.id) {
      const response = await addProjectBenefit(project.id, benefit)
      // 用返回的数据更新本地福利对象
      Object.assign(project.benefits[index], response.data)
      showToast('添加成功')
    } else {
      // 如果是已有福利，则调用更新接口
      await updateBenefit(project.id, benefit.id, benefit)
      showToast('更新成功')
    }
  } catch (error) {
    console.error('保存福利失败:', error)
    showToast('保存失败，请重试')
  }
}

// 删除福利
const removeBenefit = async (project: Project, index: number) => {
  const benefit = project.benefits[index]
  
  // 如果福利没有id，说明还未保存到数据库，直接在前端删除
  if (!benefit.id) {
    project.benefits.splice(index, 1)
    return
  }

  // 如果福利已经保存到数据库，需要调用接口删除
  if (!project.id) return

  try {
    await showConfirmDialog({
      title: '确认删除',
      message: '确定要删除这个福利吗？'
    })
    await deleteBenefit(project.id, benefit.id)
    project.benefits.splice(index, 1)
    showToast('删除成功')
  } catch (error) {
    console.error('删除福利失败:', error)
    if (error !== 'cancel') {
      showToast('删除失败，请重试')
    }
  }
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
const handleImageUpload = async (event: Event, project: Project, caseItem: ProjectCase) => {
  const input = event.target as HTMLInputElement
  if (!input.files || !input.files[0] || !project.id || !caseItem.id) return

  try {
    const file = input.files[0]
    const response = await uploadCaseImage(project.id, caseItem.id, file)
    caseItem.imageUrl = response.data.url
    showToast('上传成功')
  } catch (error) {
    console.error('上传图片失败:', error)
    showToast('上传失败，请重试')
  }
}

// 视频上传相关
const videoInputRefs = ref<{ [key: string]: HTMLInputElement }>({})

// 触发视频上传
const triggerVideoUpload = (projectIndex: number) => {
  const inputRef = videoInputRefs.value[projectIndex]
  if (inputRef) {
    inputRef.click()
  }
}

// 处理视频上传
const handleVideoUpload = async (event: Event, project: Project) => {
  const input = event.target as HTMLInputElement
  if (!input.files || !input.files[0] || !project.id) return

  try {
    const file = input.files[0]
    const response = await uploadProjectVideo(project.id, file)
    project.videoUrl = response.data.url
    showToast('上传成功')
  } catch (error) {
    console.error('上传视频失败:', error)
    showToast('上传失败，请重试')
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
    height: 60vh;
    display: flex;
    flex-direction: column;
    
    .van-dialog__header {
      flex-shrink: 0;
      padding: 16px;
      border-bottom: 1px solid #eee;
    }
    
    .van-dialog__content {
      flex: 1;
      overflow-y: auto;
      padding: 0;
      
      /* 滚动条样式 */
      &::-webkit-scrollbar {
        width: 6px;
      }
      
      &::-webkit-scrollbar-track {
        background: #f1f1f1;
        border-radius: 3px;
      }
      
      &::-webkit-scrollbar-thumb {
        background: #888;
        border-radius: 3px;
      }
      
      &::-webkit-scrollbar-thumb:hover {
        background: #555;
      }
    }
    
    .van-dialog__footer {
      flex-shrink: 0;
      border-top: 1px solid #eee;
    }
  }
}

/* 案例列表滚动条样式 */
.space-y-4::-webkit-scrollbar {
  width: 6px;
}

.space-y-4::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.space-y-4::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 3px;
}

.space-y-4::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style> 