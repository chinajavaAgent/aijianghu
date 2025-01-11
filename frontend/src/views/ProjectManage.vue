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
          <button @click="addProject" 
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
                  <img v-if="project.icon" :src="project.icon" 
                    class="w-10 h-10 object-cover rounded-lg" 
                    alt="项目图标">
                  <h3 class="text-lg font-semibold text-gray-800">{{ project.name }}</h3>
                </div>
                <p class="text-gray-600 mb-4">{{ project.description }}</p>
                <div class="prose prose-sm max-w-none" v-html="project.detail"></div>
              </div>
              
              <div class="flex flex-col space-y-2 ml-4">
                <button @click="editProject(project)" 
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
            <label class="block text-sm font-medium text-gray-700 mb-1">项目描述</label>
            <textarea v-model="form.description"
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
              rows="3"
              placeholder="请输入项目简短描述"></textarea>
          </div>

          <!-- 项目图标 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">项目图标</label>
            <div v-if="form.icon" class="relative w-20 h-20">
              <img :src="form.icon" 
                class="w-full h-full object-cover rounded-lg" 
                alt="项目图标">
              <button @click="form.icon = ''"
                class="absolute -top-2 -right-2 w-6 h-6 flex items-center justify-center bg-red-500 text-white rounded-full hover:bg-red-600">
                <i class="fas fa-times"></i>
              </button>
            </div>
            <div v-else>
              <input type="file" 
                accept="image/*" 
                class="hidden" 
                ref="iconInput"
                @change="handleIconUpload">
              <button @click="triggerIconUpload"
                class="w-20 h-20 flex flex-col items-center justify-center text-gray-500 hover:text-gray-700 border-2 border-dashed border-gray-300 rounded-lg">
                <i class="fas fa-image text-xl mb-1"></i>
                <span class="text-xs">上传图标</span>
              </button>
            </div>
          </div>

          <!-- 项目详情 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">项目详情</label>
            <div class="border rounded-lg">
              <Editor
                v-model="form.detail"
                :init="{
                  height: 300,
                  menubar: false,
                  plugins: [
                    'advlist autolink lists link image charmap print preview anchor',
                    'searchreplace visualblocks code fullscreen',
                    'insertdatetime media table paste code help wordcount'
                  ],
                  toolbar:
                    'undo redo | formatselect | bold italic backcolor | \
                    alignleft aligncenter alignright alignjustify | \
                    bullist numlist outdent indent | removeformat | help'
                }"
              />
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
import Editor from '@tinymce/tinymce-vue'
import {
  getProjects,
  createProject,
  updateProject,
  deleteProject as deleteProjectApi,
  uploadProjectIcon
} from '@/api/project'
import type { Project } from '@/types/project'
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
  icon: '',
  detail: ''
})

// 显示添加对话框
const addProject = () => {
  isEditing.value = false
  // 清空表单数据
  Object.assign(form, {
    id: null,
    name: '',
    description: '',
    icon: '',
    detail: ''
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
    icon: project.icon,
    detail: project.detail
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
      icon: form.icon || '',
      detail: form.detail || ''
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
    if (response?.data?.data?.records) {
      projects.value = response.data.data.records
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

// 图标上传相关
const iconInput = ref<HTMLInputElement | null>(null)

const triggerIconUpload = () => {
  if (iconInput.value) {
    iconInput.value.click()
  }
}

const handleIconUpload = async (event: Event) => {
  const input = event.target as HTMLInputElement
  if (!input.files || !input.files[0]) return

  try {
    const file = input.files[0]
    const response = await uploadProjectIcon(file)
    if (response?.data?.code === 200 && response.data.data) {
      form.icon = response.data.data.toString()
      showToast('上传成功')
    } else {
      showToast(response?.data?.message || '上传失败')
    }
  } catch (error) {
    console.error('上传图标失败:', error)
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

:deep(.prose) {
  max-width: none;
  
  img {
    @apply rounded-lg;
  }
}
</style> 