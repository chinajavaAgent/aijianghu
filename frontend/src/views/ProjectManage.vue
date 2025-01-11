<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white/80 backdrop-blur-sm shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4 flex items-center">
        <button @click="router.back()" class="mr-4 text-gray-600">
          <i class="fas fa-arrow-left"></i>
        </button>
        <h1 class="text-xl font-bold text-gray-800 py-4">{{ tipTitle }} - 项目管理</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 项目列表 -->
      <div class="bg-white/80 backdrop-blur-sm rounded-xl shadow-md p-6">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-lg font-semibold">项目列表</h2>
          <button @click="router.push({ name: 'ProjectEdit', params: { tipId, tipTitle } })" 
            class="px-4 py-2 bg-gradient-to-r from-blue-500 to-blue-600 text-white rounded-lg hover:from-blue-600 hover:to-blue-700 transition-all shadow-sm">
            添加项目
          </button>
        </div>

        <!-- 项目列表 -->
        <div class="space-y-6">
          <div v-for="(project, index) in projects" :key="project?.id || index" 
            class="bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow border border-gray-100 p-6">
            <div class="flex justify-between items-start">
              <div class="flex-1">
                <div class="flex items-center space-x-3 mb-3">
                  <h3 class="text-lg font-semibold text-gray-800">{{ project.name }}</h3>
                  <div class="flex items-center space-x-2">
                    <span class="text-sm text-gray-500">
                      <i class="fas fa-eye mr-1"></i>{{ project.views }}
                    </span>
                    <span class="text-sm text-gray-500">
                      <i class="fas fa-heart mr-1"></i>{{ project.likes }}
                    </span>
                  </div>
                </div>
                <p class="text-gray-600 mb-4">{{ project.description }}</p>
                
                <!-- 项目内容区域 -->
                <div class="space-y-4">
                  <!-- 案例展示 -->
                  <div v-if="project.cases && project.cases.length > 0">
                    <h4 class="font-medium text-gray-700 mb-2">成功案例</h4>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                      <div v-for="case_ in project.cases" :key="case_.id" 
                        class="bg-gray-50 rounded-lg p-3">
                        <img v-if="case_.imageUrl" :src="case_.imageUrl" 
                          class="w-full h-48 object-cover rounded-lg mb-2" 
                          alt="案例图片">
                        <p class="text-sm text-gray-600">{{ case_.description }}</p>
                      </div>
                    </div>
                  </div>

                  <!-- 项目视频 -->
                  <div v-if="project.videoUrl">
                    <h4 class="font-medium text-gray-700 mb-2">项目视频</h4>
                    <video :src="project.videoUrl" 
                      class="w-full rounded-lg" 
                      controls>
                    </video>
                  </div>

                  <!-- 项目福利 -->
                  <div v-if="project.benefits && project.benefits.length > 0">
                    <h4 class="font-medium text-gray-700 mb-2">项目福利</h4>
                    <ul class="list-disc list-inside space-y-1">
                      <li v-for="benefit in project.benefits" :key="benefit.id" 
                        class="text-gray-600">
                        {{ benefit.content }}
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              
              <div class="flex flex-col space-y-2 ml-4">
                <button @click="router.push({ name: 'ProjectEdit', params: { tipId, tipTitle, id: project.id } })" 
                  class="px-3 py-1 text-sm text-blue-600 border border-blue-600 rounded-lg hover:bg-blue-50 transition-colors">
                  编辑
                </button>
                <button @click="deleteProject(project.id)" 
                  class="px-3 py-1 text-sm text-red-600 border border-red-600 rounded-lg hover:bg-red-50 transition-colors">
                  删除
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑项目弹窗 -->
    <van-dialog
      v-model:show="showDialog"
      :title="isEditing ? '编辑项目' : '添加项目'"
      show-cancel-button
      @confirm="handleSubmit"
      class="project-dialog"
    >
      <div class="p-4">
        <div class="space-y-4">
          <!-- 基本信息 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">项目名称</label>
            <input v-model="form.name" type="text"
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="请输入项目名称">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">项目介绍</label>
            <textarea v-model="form.description"
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
              rows="3"
              placeholder="请输入项目详细介绍"></textarea>
          </div>

          <!-- 案例管理 -->
          <div>
            <div class="flex justify-between items-center mb-2">
              <label class="block text-sm font-medium text-gray-700">成功案例</label>
              <button @click="addCase" 
                class="text-sm text-blue-600 hover:text-blue-800">
                添加案例
              </button>
            </div>
            <div class="space-y-4">
              <div v-for="(case_, index) in form.cases" :key="index"
                class="bg-gray-50 p-4 rounded-lg">
                <div class="flex justify-between items-start mb-2">
                  <span class="text-sm font-medium text-gray-700">案例 {{ index + 1 }}</span>
                  <button @click="removeCase(index)"
                    class="text-sm text-red-600 hover:text-red-800">
                    删除
                  </button>
                </div>
                <textarea v-model="case_.description"
                  class="w-full px-3 py-2 border rounded-lg mb-2"
                  rows="2"
                  placeholder="请输入案例描述"></textarea>
                <div class="space-y-2">
                  <div v-if="case_.imageUrl" class="relative">
                    <img :src="case_.imageUrl" 
                      class="w-full h-48 object-cover rounded-lg" 
                      alt="案例图片">
                    <button @click="case_.imageUrl = ''"
                      class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center bg-red-500 text-white rounded-full hover:bg-red-600">
                      <i class="fas fa-times"></i>
                    </button>
                  </div>
                  <div v-else>
                    <input type="file" 
                      accept="image/*" 
                      class="hidden" 
                      :ref="(el: any) => { if (el) imageInputRefs[index] = el as HTMLInputElement }"
                      @change="handleImageUpload($event, case_)">
                    <button @click="triggerImageUpload(index)"
                      class="w-full h-24 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700 border-2 border-dashed border-gray-300 rounded-lg">
                      <i class="fas fa-camera text-xl mb-1"></i>
                      <span class="text-xs">点击上传案例图片</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 视频上传 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">项目视频</label>
            <div v-if="form.videoUrl" class="relative">
              <video :src="form.videoUrl" 
                class="w-full rounded-lg" 
                controls>
              </video>
              <button @click="form.videoUrl = ''"
                class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center bg-red-500 text-white rounded-full hover:bg-red-600">
                <i class="fas fa-times"></i>
              </button>
            </div>
            <div v-else>
              <input type="file" 
                accept="video/*" 
                class="hidden" 
                ref="videoInput"
                @change="handleVideoUpload">
              <button @click="triggerVideoUpload"
                class="w-full h-32 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700 border-2 border-dashed border-gray-300 rounded-lg">
                <i class="fas fa-video text-2xl mb-2"></i>
                <span class="text-sm">点击上传项目视频</span>
              </button>
            </div>
          </div>

          <!-- 福利管理 -->
          <div>
            <div class="flex justify-between items-center mb-2">
              <label class="block text-sm font-medium text-gray-700">项目福利</label>
              <button @click="addBenefit" 
                class="text-sm text-blue-600 hover:text-blue-800">
                添加福利
              </button>
            </div>
            <div class="space-y-2">
              <div v-for="(benefit, index) in form.benefits" :key="index"
                class="flex items-center space-x-2">
                <input v-model="benefit.content" type="text"
                  class="flex-1 px-3 py-2 border rounded-lg"
                  placeholder="请输入福利内容">
                <button @click="removeBenefit(index)"
                  class="text-sm text-red-600 hover:text-red-800">
                  删除
                </button>
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
import { useRoute, useRouter } from 'vue-router'
import { showConfirmDialog, showToast } from 'vant'
import {
  getProjects,
  createProject,
  updateProject,
  deleteProject as deleteProjectApi,
  uploadCaseImage,
  uploadProjectVideo
} from '@/api/project'
import type { Project, ProjectCase, ProjectBenefit } from '@/types/project'
import type { PageResponse } from '@/types/common'

const route = useRoute()
const router = useRouter()
const tipId = Number(route.params.tipId)
const tipTitle = route.params.tipTitle as string

// 项目列表数据
const projects = ref<Project[]>([])

// 添加/编辑项目相关
const showDialog = ref(false)
const isEditing = ref(false)
const form = reactive({
  id: null as number | null,
  name: '',
  description: '',
  videoUrl: '',
  cases: [] as ProjectCase[],
  benefits: [] as ProjectBenefit[]
})

// 显示添加对话框
const addProject = () => {
  isEditing.value = false
  // 清空表单数据
  Object.assign(form, {
    id: null,
    name: '',
    description: '',
    videoUrl: '',
    cases: [],
    benefits: []
  })
  showDialog.value = true
}

// 编辑项目
const editProject = (project: Project) => {
  isEditing.value = true
  // 设置表单数据
  Object.assign(form, {
    id: project.id,
    name: project.name,
    description: project.description,
    videoUrl: project.videoUrl,
    cases: [...project.cases],
    benefits: [...project.benefits]
  })
  showDialog.value = true
}

// 删除项目
const deleteProject = async (id: number) => {
  try {
    await showConfirmDialog({
      title: '确认删除',
      message: '确定要删除这个项目吗？'
    })
    const response = await deleteProjectApi(id)
    if (response?.data?.code === 200) {
      showToast('删除成功')
      loadProjects()
    } else {
      showToast(response?.data?.message || '删除失败')
    }
  } catch (error) {
    console.error('删除项目失败:', error)
    if (error !== 'cancel') {
      showToast('删除失败，请重试')
    }
  }
}

// 提交项目表单
const handleSubmit = async () => {
  try {
    if (!form.name) {
      showToast('请输入项目名称')
      return false
    }

    const projectData = {
      tipId,
      name: form.name,
      description: form.description,
      videoUrl: form.videoUrl || '',
      status: 0,
      cases: form.cases || [],
      benefits: form.benefits || []
    }

    if (isEditing.value && form.id) {
      const response = await updateProject(form.id, projectData)
      if (response?.data?.code === 200) {
        showToast('更新成功')
        showDialog.value = false
        loadProjects()
      } else {
        showToast(response?.data?.message || '更新失败')
      }
    } else {
      const response = await createProject(projectData)
      if (response?.data?.code === 200) {
        showToast('创建成功')
        showDialog.value = false
        loadProjects()
      } else {
        showToast(response?.data?.message || '创建失败')
      }
    }
  } catch (error) {
    console.error('操作失败:', error)
    showToast('操作失败，请重试')
  }
}

// 加载项目列表
const loadProjects = async () => {
  try {
    const response = await getProjects(1, 100, tipId)
    // 添加多层空值检查
    if (response?.data?.records) {
      projects.value = response.data.records
    } else {
      console.warn('返回数据结构不符合预期:', response)
      projects.value = []
    }
  } catch (error) {
    console.error('加载项目列表失败:', error)
    showToast('加载失败，请重试')
    projects.value = [] // 出错时设置为空数组
  }
}

// 案例相关操作
const addCase = () => {
  form.cases.push({
    description: '',
    imageUrl: ''
  })
}

const removeCase = (index: number) => {
  form.cases.splice(index, 1)
}

// 福利相关操作
const addBenefit = () => {
  form.benefits.push({
    content: ''
  })
}

const removeBenefit = (index: number) => {
  form.benefits.splice(index, 1)
}

// 图片上传相关
const imageInputRefs = ref<{ [key: string]: HTMLInputElement | null }>({})

const triggerImageUpload = (index: number) => {
  const inputRef = imageInputRefs.value[index]
  if (inputRef) {
    inputRef.click()
  }
}

const handleImageUpload = async (event: Event, caseItem: ProjectCase) => {
  const input = event.target as HTMLInputElement
  if (!input.files || !input.files[0] || !form.id) return

  try {
    const file = input.files[0]
    const response = await uploadCaseImage(form.id, caseItem.id!, file)
    if (response?.data?.code === 200 && response.data.data) {
      caseItem.imageUrl = response.data.data.toString()
      showToast('上传成功')
    } else {
      showToast(response?.data?.message || '上传失败')
    }
  } catch (error) {
    console.error('上传图片失败:', error)
    showToast('上传失败，请重试')
  }
}

// 视频上传相关
const videoInput = ref<HTMLInputElement | null>(null)

const triggerVideoUpload = () => {
  if (videoInput.value) {
    videoInput.value.click()
  }
}

const handleVideoUpload = async (event: Event) => {
  const input = event.target as HTMLInputElement
  if (!input.files || !input.files[0] || !form.id) return

  try {
    const file = input.files[0]
    const response = await uploadProjectVideo(form.id, file)
    if (response?.data?.code === 200 && response.data.data) {
      form.videoUrl = response.data.data.toString()
      showToast('上传成功')
    } else {
      showToast(response?.data?.message || '上传失败')
    }
  } catch (error) {
    console.error('上传视频失败:', error)
    showToast('上传失败，请重试')
  }
}

// 页面加载时获取数据
onMounted(() => {
  if (!tipId) {
    showToast('参数错误')
    router.back()
    return
  }
  loadProjects()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
}

.project-dialog {
  :deep(.van-dialog) {
    width: 90%;
    max-width: 800px;
    height: 80vh;
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
</style> 