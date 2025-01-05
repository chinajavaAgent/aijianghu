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
                  <span class="font-medium text-gray-800">{{ project.title || `项目 ${index + 1}` }}</span>
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
                    <label class="block text-sm font-medium text-gray-700 mb-1">
                      项目名称
                      <span class="text-red-500">*</span>
                    </label>
                    <input v-model="project.title" type="text"
                      class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
                      :class="{'border-red-500': !project.title?.trim()}"
                      placeholder="请输入项目名称（必填）">
                  </div>
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">
                      项目介绍
                      <span class="text-red-500">*</span>
                    </label>
                    <textarea v-model="project.description"
                      class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
                      :class="{'border-red-500': !project.description?.trim()}"
                      rows="3"
                      placeholder="请输入项目详细介绍（必填）"></textarea>
                  </div>
                  <div class="flex justify-end">
                    <button @click="submitProject(project)"
                      class="px-4 py-2 text-sm text-white bg-blue-600 rounded-lg hover:bg-blue-700">
                      保存基本信息
                    </button>
                  </div>
                </div>

                <!-- 成功案例 -->
                <div v-if="project.currentStep === 1">
                  <div class="space-y-4">
                    <div v-for="(caseItem, caseIndex) in project.cases" :key="caseIndex"
                      class="bg-white p-4 rounded-lg border border-gray-200">
                      <div class="flex justify-between items-start mb-2">
                        <span class="text-sm font-medium text-gray-700">案例 {{ caseIndex + 1 }}</span>
                        <div class="flex items-center space-x-2">
                          <button @click="submitCase(project, caseItem)"
                            class="text-sm text-blue-600 hover:text-blue-800">
                            提交
                          </button>
                          <button @click="removeCase(project, caseIndex)"
                            class="text-sm text-red-600 hover:text-red-800">
                            删除案例
                          </button>
                        </div>
                      </div>
                      <div class="space-y-2">
                        <div>
                          <label class="block text-sm font-medium text-gray-700 mb-1">
                            案例描述
                            <span class="text-red-500">*</span>
                          </label>
                          <textarea v-model="caseItem.description"
                            class="w-full px-3 py-2 border rounded-lg mb-2"
                            :class="{'border-red-500': !caseItem.description?.trim()}"
                            rows="2"
                            placeholder="请输入案例描述（必填）"></textarea>
                        </div>
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
                            @change="handleImageUpload($event, project, caseItem)">
                          <button @click="triggerImageUpload(index, caseIndex)"
                            class="w-full h-32 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700">
                            <i class="fas fa-camera text-2xl mb-2"></i>
                            <span class="text-sm">点击上传案例图片（选填）</span>
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
const openProjectDialog = async (tip: AiTips) => {
  currentTip.value = tip
  try {
    // 加载项目列表
    const response = await getProjects(1, 10)  // 使用分页参数
    projectForm.projects = response.data.records.map((project: Project) => ({  // 从分页对象中获取records
      ...project,
      isExpanded: true,
      currentStep: 0
    }))
    projectDialogVisible.value = true
  } catch (error) {
    console.error('加载项目列表失败:', error)
    showToast('加载失败，请重试')
  }
}

// 添加项目
const addProject = () => {
  const newProject: Project = {
    title: '',
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
    await Dialog.confirm({
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
  try {
    // 验证所有项目的必填字段
    for (const project of projectForm.projects) {
      if (!project.title?.trim()) {
        showToast('请输入项目名称')
        return
      }
      if (!project.description?.trim()) {
        showToast('请输入项目介绍')
        return
      }
      // 验证案例内容
      for (const caseItem of project.cases) {
        if (!caseItem.description?.trim()) {
          showToast('请输入案例内容')
          return
        }
      }
    }

    // 更新所有项目
    await Promise.all(projectForm.projects.map(async (project) => {
      if (project.id) {
        const updateData: ProjectUpdateDto = {
          title: project.title,
          description: project.description,
          videoUrl: project.videoUrl,
          status: project.status
        }
        await updateProject(project.id, updateData)
      } else {
        // 如果是新项目，则创建
        await createProject(project)
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
const addCase = (project: Project) => {
  if (!project.cases) {
    project.cases = []
  }
  const newCase: ProjectCase = {
    description: '',
    imageUrl: ''
  }
  project.cases.push(newCase)
}

// 删除案例
const removeCase = async (project: Project, index: number) => {
  const caseItem = project.cases[index]
  if (!project.id || !caseItem.id) return

  try {
    await Dialog.confirm({
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
const addBenefit = async (project: Project) => {
  if (!project.id) return
  
  try {
    const newBenefit = {
      content: ''
    }
    const response = await addProjectBenefit(project.id, newBenefit)
    project.benefits.push(response.data)
    showToast('添加成功')
  } catch (error) {
    console.error('添加福利失败:', error)
    showToast('添加失败，请重试')
  }
}

// 删除福利
const removeBenefit = async (project: Project, index: number) => {
  const benefit = project.benefits[index]
  if (!project.id || !benefit.id) return

  try {
    await Dialog.confirm({
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

// 提交单个案例
const submitCase = async (project: Project, caseItem: ProjectCase) => {
  try {
    if (!caseItem.description?.trim()) {
      showToast('请输入案例描述')
      return
    }

    if (project.id && caseItem.id) {
      // 更新已有案例
      await updateCase(project.id, caseItem.id, {
        description: caseItem.description,
        imageUrl: caseItem.imageUrl
      })
    } else if (project.id) {
      // 添加新案例
      const response = await addProjectCase(project.id, {
        description: caseItem.description,
        imageUrl: caseItem.imageUrl
      })
      Object.assign(caseItem, response.data)
    }
    showToast('保存成功')
  } catch (error) {
    console.error('保存案例失败:', error)
    showToast('保存失败，请重试')
  }
}

// 提交单个项目的基本信息
const submitProject = async (project: Project) => {
  try {
    if (!project.title?.trim()) {
      showToast('请输入项目名称')
      return
    }
    if (!project.description?.trim()) {
      showToast('请输入项目介绍')
      return
    }

    if (project.id) {
      // 更新已有项目
      await updateProject(project.id, {
        title: project.title,
        description: project.description,
        videoUrl: project.videoUrl
      })
    } else {
      // 创建新项目
      const response = await createProject(project)
      Object.assign(project, response.data)
    }
    showToast('保存成功')
  } catch (error) {
    console.error('保存项目失败:', error)
    showToast('保存失败，请重试')
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