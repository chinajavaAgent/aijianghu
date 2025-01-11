<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-fuchsia-50 to-yellow-50 pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white/80 backdrop-blur-sm shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4 flex items-center">
        <button @click="router.back()" class="mr-4 text-gray-600">
          <i class="fas fa-arrow-left"></i>
        </button>
        <h1 class="text-xl font-bold text-gray-800 py-4">{{ isEditing ? '编辑项目' : '新增项目' }}</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <div class="bg-white/80 backdrop-blur-sm rounded-xl shadow-md p-6">
        <div class="space-y-6">
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
            <div v-if="form.coverImage" class="relative w-20 h-20">
              <img :src="form.coverImage" 
                class="w-full h-full object-cover rounded-lg" 
                alt="项目图标">
              <button @click="form.coverImage = ''"
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
            <div class="border rounded-lg overflow-hidden">
              <Toolbar
                class="border-b"
                :editor="editorRef"
                :defaultConfig="toolbarConfig"
                :mode="mode"
              />
              <Editor
                class="h-[500px]"
                v-model="form.detail"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="handleCreated"
                @onChange="handleChange"
              />
            </div>
          </div>

          <!-- 提交按钮 -->
          <div class="flex justify-end space-x-4">
            <button @click="router.back()" 
              class="px-6 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50">
              取消
            </button>
            <button @click="handleSubmit" 
              class="px-6 py-2 bg-gradient-to-r from-blue-500 to-blue-600 text-white rounded-lg hover:from-blue-600 hover:to-blue-700">
              保存
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, shallowRef, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { showToast } from 'vant'
import '@wangeditor/editor/dist/css/style.css'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import {
  getProjectById,
  createProject,
  updateProject,
  uploadProjectIcon,
  uploadEditorImage
} from '@/api/project'
import type { Project } from '@/types/project'
import type { IDomEditor, IEditorConfig, IToolbarConfig } from '@wangeditor/editor'

const route = useRoute()
const router = useRouter()
const tipId = Number(route.params.tipId)
const projectId = route.params.id ? Number(route.params.id) : null
const isEditing = ref(!!projectId)

// 表单数据
const form = reactive({
  id: null as number | null,
  name: '',
  description: '',
  coverImage: '',
  detail: ''
})

// 加载项目数据
const loadProject = async () => {
  if (!projectId) return
  
  try {
    const response = await getProjectById(projectId)
    console.log(response.data)
    if (response.code === 200 && response.data) {
      const project = response.data
      Object.assign(form, {
        id: project.id,
        name: project.name,
        description: project.description,
        coverImage: project.coverImage,
        detail: project.detail
      })
    } else {
      showToast('加载失败')
      router.back()
    }
  } catch (error) {
    console.error('加载项目失败:', error)
    showToast('加载失败')
    router.back()
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    if (!form.name) {
      showToast('请输入项目名称')
      return
    }

    const projectData: Partial<Project> = {
      tipId,
      name: form.name,
      description: form.description,
      coverImage: form.coverImage || '',
      detail: form.detail || '',
      status: 1,  // 默认状态
      views: 0,   // 默认浏览量
      likes: 0,   // 默认点赞数
      cases: [],  // 默认案例列表
      benefits: [] // 默认福利列表
    }

    if (isEditing.value && form.id) {
      const response = await updateProject(form.id, projectData)
      if (response.code === 200) {
        showToast('更新成功')
        router.back()
      } else {
        showToast(response.message || '更新失败')
      }
    } else {
      const response = await createProject(projectData as Project)
      if (response.code === 200) {
        showToast('创建成功')
        router.back()
      } else {
        showToast(response.message || '创建失败')
      }
    }
  } catch (error) {
    console.error('操作失败:', error)
    showToast('操作失败，请重试')
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
    if (response.code === 200 && response.data) {
      const imageUrl = response.data.data
      form.coverImage = imageUrl
      // 如果是编辑模式，立即更新项目信息
      if (isEditing.value && form.id) {
        const updateResult = await updateProject(form.id, { coverImage: imageUrl })
        if (updateResult.code === 200) {
          showToast('图标上传成功')
        } else {
          showToast(updateResult.message || '图标保存失败')
        }
      } else {
        showToast('图标上传成功')
      }
    } else {
      showToast(response.message || '上传失败')
    }
  } catch (error) {
    console.error('上传图标失败:', error)
    showToast('上传失败，请重试')
  }
}

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef<IDomEditor>()

// 编辑器配置
const toolbarConfig: Partial<IToolbarConfig> = {
  excludeKeys: [
    'group-video',
    'insertTable',
    'group-indent',
    'headerSelect',
    'fullScreen'
  ]
}

const editorConfig: Partial<IEditorConfig> = {
  placeholder: '请输入项目详情...',
  MENU_CONF: {
    uploadImage: {
      fieldName: 'file',
      maxFileSize: 5 * 1024 * 1024,
      maxNumberOfFiles: 10,
      allowedFileTypes: ['image/*'],
      // 自定义上传
      customUpload: async (file: File, insertFn: any) => {
        try {
          const response = await uploadEditorImage(file)
          if (response.code === 200 && response.data) {
            // 直接插入图片URL
            insertFn(response.data)
          } else {
            showToast(response.message || '上传失败')
          }
        } catch (err) {
          console.error('上传图片失败:', err)
          showToast('上传失败，请重试')
        }
      }
    }
  }
}

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const mode = 'default'

const handleCreated = (editor: IDomEditor) => {
  editorRef.value = editor
}

const handleChange = (editor: IDomEditor) => {
  form.detail = editor.getHtml()
}

// 页面加载时获取数据
onMounted(() => {
  if (!tipId) {
    showToast('参数错误')
    router.back()
    return
  }
  if (projectId) {
    loadProject()
  }
})
</script>

<style scoped>
.container {
  max-width: 1200px;
}

:deep(.tox-tinymce) {
  border-radius: 0.5rem;
}
</style> 