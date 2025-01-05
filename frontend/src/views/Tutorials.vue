<template>
  <div class="tutorials min-h-screen bg-gray-50">
    <!-- 顶部导航 -->
    <div class="bg-white shadow">
      <div class="container mx-auto px-4 py-4">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <button @click="router.back()" class="p-2 hover:bg-gray-100 rounded-lg">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
              </svg>
            </button>
            <h1 class="text-xl font-bold">运营教程</h1>
          </div>
          <button @click="showAddContentModal = true" 
            class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
            添加内容
          </button>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="container mx-auto px-4 py-6">
      <!-- 内容类型切换 -->
      <div class="bg-white rounded-lg shadow mb-6">
        <div class="flex">
          <button v-for="tab in tabs" :key="tab.type"
            class="flex-1 px-4 py-3 text-center transition-colors"
            :class="currentTab === tab.type ? 'text-blue-600 border-b-2 border-blue-600 font-medium' : 'text-gray-600 hover:text-gray-800'"
            @click="currentTab = tab.type">
            {{ tab.name }}
            <span class="ml-2 text-sm text-gray-500">({{ getContentCount(tab.type) }})</span>
          </button>
        </div>
      </div>

      <!-- 内容列表 -->
      <div class="space-y-6">
        <!-- 教程内容 -->
        <template v-if="currentTab === 'tutorial'">
          <div v-for="item in filteredContent" :key="item.id" 
            class="bg-white rounded-lg shadow hover:shadow-md transition-shadow">
            <div class="p-6">
              <div class="flex justify-between items-start">
                <div class="w-full">
                  <h3 class="text-lg font-semibold mb-2 cursor-pointer hover:text-blue-600"
                    @click="showTutorialDetail(item)">
                    {{ item.title }}
                  </h3>
                  <p class="text-gray-600 mb-4">{{ item.description }}</p>
                  <div class="flex items-center text-sm text-gray-500">
                    <span>{{ item.date }}</span>
                    <span class="mx-2">·</span>
                    <span>{{ item.author }}</span>
                  </div>
                </div>
                <div class="flex space-x-2 ml-4">
                  <button @click="editContent(item)" 
                    class="p-2 text-blue-600 hover:bg-blue-50 rounded">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                    </svg>
                  </button>
                  <button @click="deleteContent(item)" 
                    class="p-2 text-red-600 hover:bg-red-50 rounded">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                    </svg>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </template>

        <!-- 视频内容 -->
        <template v-if="currentTab === 'video'">
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div v-for="item in filteredContent" :key="item.id" 
              class="bg-white rounded-lg shadow hover:shadow-md transition-shadow">
              <div class="aspect-w-16 aspect-h-9 relative">
                <img :src="(item as VideoContent).thumbnail" :alt="item.title" class="w-full h-full object-cover rounded-t-lg">
                <div class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 hover:opacity-100 transition-opacity">
                  <button @click="playVideo(item)" class="text-white">
                    <svg class="w-16 h-16" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M10 0a10 10 0 100 20 10 10 0 000-20zm4 10.75l-6 3.5A.75.75 0 016.5 13V7a.75.75 0 011.125-.65l6 3.5a.75.75 0 010 1.3z"></path>
                    </svg>
                  </button>
                </div>
              </div>
              <div class="p-4">
                <h3 class="font-semibold mb-2">{{ item.title }}</h3>
                <p class="text-sm text-gray-600 mb-4">{{ item.description }}</p>
                <div class="flex justify-between items-center">
                  <span class="text-sm text-gray-500">{{ (item as VideoContent).duration }}</span>
                  <div class="flex space-x-2">
                    <button @click="editContent(item)" class="text-blue-600 hover:text-blue-700">
                      编辑
                    </button>
                    <button @click="deleteContent(item)" class="text-red-600 hover:text-red-700">
                      删除
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </template>

        <!-- 图片内容 -->
        <template v-if="currentTab === 'image'">
          <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
            <div v-for="item in filteredContent" :key="item.id" 
              class="group relative bg-white rounded-lg shadow hover:shadow-md transition-shadow">
              <img :src="(item as ImageContent).url" :alt="item.title" 
                class="w-full aspect-square object-cover rounded-lg">
              <div class="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity rounded-lg">
                <div class="absolute bottom-0 left-0 right-0 p-4 text-white">
                  <h3 class="font-semibold mb-1">{{ item.title }}</h3>
                  <p class="text-sm opacity-90">{{ item.description }}</p>
                  <div class="flex justify-end space-x-2 mt-2">
                    <button @click="editContent(item)" 
                      class="p-2 hover:bg-white hover:bg-opacity-20 rounded">
                      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                      </svg>
                    </button>
                    <button @click="deleteContent(item)" 
                      class="p-2 hover:bg-white hover:bg-opacity-20 rounded">
                      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </template>
      </div>
    </div>

    <!-- 添加/编辑内容弹窗 -->
    <van-dialog
      v-model:show="showAddContentModal"
      :title="editingContent ? '编辑内容' : '添加内容'"
      class="rounded-lg"
      :show-cancel-button="true"
      @confirm="saveContent"
    >
      <div class="p-4 space-y-4">
        <!-- 内容类型选择 -->
        <div v-if="!editingContent">
          <label class="block text-sm font-medium text-gray-700 mb-1">内容类型</label>
          <select v-model="newContent.type" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
            <option v-for="tab in tabs" :key="tab.type" :value="tab.type">
              {{ tab.name }}
            </option>
          </select>
        </div>

        <!-- 通用字段 -->
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">标题</label>
          <input v-model="newContent.title" type="text" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">描述</label>
          <textarea v-model="newContent.description" rows="3"
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"></textarea>
        </div>

        <!-- 教程特有字段 -->
        <template v-if="newContent.type === 'tutorial'">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">教程内容</label>
            <div class="border rounded-lg">
              <div class="bg-gray-50 px-3 py-2 border-b">
                <div class="flex space-x-2">
                  <button @click="insertFormat('h2')" class="p-1 hover:bg-gray-200 rounded" title="标题">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"></path>
                    </svg>
                  </button>
                  <button @click="insertFormat('b')" class="p-1 hover:bg-gray-200 rounded" title="加粗">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                    </svg>
                  </button>
                  <button @click="insertFormat('ul')" class="p-1 hover:bg-gray-200 rounded" title="无序列表">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16m-7 6h7"></path>
                    </svg>
                  </button>
                  <button @click="insertFormat('ol')" class="p-1 hover:bg-gray-200 rounded" title="有序列表">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
                    </svg>
                  </button>
                </div>
              </div>
              <textarea v-model="newContent.content" rows="10"
                class="w-full px-3 py-2 focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                placeholder="在这里输入教程内容，支持HTML格式"></textarea>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">附件</label>
            <div class="space-y-2">
              <div v-for="(attachment, index) in newContent.attachments" :key="index"
                class="flex items-center p-2 bg-gray-50 rounded">
                <span class="text-sm text-gray-600">{{ attachment.name }}</span>
                <button @click="removeAttachment(index)" 
                  class="ml-auto text-red-600 text-sm hover:text-red-700">
                  删除
                </button>
              </div>
              <div class="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center">
                <input type="file" @change="handleAttachmentUpload" class="hidden" ref="attachmentInput">
                <button @click="$refs.attachmentInput.click()" 
                  class="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200">
                  添加附件
                </button>
              </div>
            </div>
          </div>
        </template>

        <!-- 视频特有字段 -->
        <template v-if="newContent.type === 'video'">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">视频链接</label>
            <input v-model="newContent.videoUrl" type="text" 
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">视频时长</label>
            <input v-model="newContent.duration" type="text" 
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              placeholder="例如：10:30">
          </div>
        </template>

        <!-- 图片特有字段 -->
        <div v-if="newContent.type === 'image'" class="space-y-2">
          <label class="block text-sm font-medium text-gray-700 mb-1">上传图片</label>
          <div class="border-2 border-dashed border-gray-300 rounded-lg p-4 text-center">
            <input type="file" accept="image/*" @change="handleImageUpload" class="hidden" ref="imageInput">
            <button @click="$refs.imageInput.click()" 
              class="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200">
              选择图片
            </button>
          </div>
        </div>
      </div>
    </van-dialog>

    <!-- 视频播放弹窗 -->
    <van-dialog
      v-model:show="showVideoModal"
      title="视频播放"
      class="rounded-lg"
      :show-cancel-button="false"
      :show-confirm-button="false"
    >
      <div class="aspect-w-16 aspect-h-9">
        <video v-if="currentVideo" 
          :src="currentVideo.videoUrl" 
          controls 
          class="w-full h-full object-contain">
        </video>
      </div>
    </van-dialog>

    <!-- 教程详情弹窗 -->
    <van-dialog
      v-model:show="showTutorialDetailModal"
      :title="currentTutorial?.title"
      class="tutorial-detail-dialog"
      :show-cancel-button="false"
      closeOnClickOverlay
      :style="{ maxWidth: '800px' }"
    >
      <div class="p-6">
        <div class="mb-4 text-sm text-gray-500">
          <span>{{ currentTutorial?.author }}</span>
          <span class="mx-2">·</span>
          <span>{{ currentTutorial?.date }}</span>
        </div>
        
        <div class="prose max-w-none">
          <div v-html="currentTutorial?.content || currentTutorial?.description"></div>
        </div>

        <div v-if="currentTutorial?.attachments?.length" class="mt-6">
          <h4 class="font-medium mb-2">附件资料</h4>
          <div class="space-y-2">
            <div v-for="(attachment, index) in currentTutorial?.attachments" 
              :key="index"
              class="flex items-center p-2 bg-gray-50 rounded">
              <svg class="w-5 h-5 text-gray-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
              </svg>
              <span class="text-sm text-gray-600">{{ attachment.name }}</span>
              <button class="ml-auto text-blue-600 text-sm hover:text-blue-700"
                @click="downloadAttachment(attachment)">
                下载
              </button>
            </div>
          </div>
        </div>
      </div>
    </van-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Dialog } from 'vant'

// 定义内容类型接口
interface BaseTutorialContent {
  id: number
  type: string
  title: string
  description: string
  projectId: number
  date?: string
  author?: string
}

interface TutorialContent extends BaseTutorialContent {
  type: 'tutorial'
  content: string
  attachments: { name: string; url: string }[]
}

interface VideoContent extends BaseTutorialContent {
  type: 'video'
  thumbnail: string
  videoUrl: string
  duration: string
}

interface ImageContent extends BaseTutorialContent {
  type: 'image'
  url: string
}

type Content = TutorialContent | VideoContent | ImageContent

const router = useRouter()
const route = useRoute()

// 内容类型标签
const tabs = [
  { type: 'tutorial', name: '教程文档' },
  { type: 'video', name: '视频内容' },
  { type: 'image', name: '图片内容' }
] as const

const currentTab = ref('tutorial')
const showAddContentModal = ref(false)
const showVideoModal = ref(false)
const editingContent = ref<Content | null>(null)
const currentVideo = ref<VideoContent | null>(null)
const showTutorialDetailModal = ref(false)
const currentTutorial = ref<TutorialContent | null>(null)

// 内容列表数据
const contentList = ref<Content[]>([
  {
    id: 1,
    type: 'tutorial',
    title: '新手入门指南',
    description: '详细介绍项目运营的基本流程和注意事项',
    content: `
      <h2>1. 项目概述</h2>
      <p>本项目旨在帮助运营人员快速上手，掌握核心运营技能。</p>
      
      <h2>2. 运营流程</h2>
      <ul>
        <li>内容策划与制作</li>
        <li>渠道投放与推广</li>
        <li>数据分析与优化</li>
        <li>用户运营与维护</li>
      </ul>
      
      <h2>3. 注意事项</h2>
      <p>在运营过程中，需要特别注意以下几点：</p>
      <ol>
        <li>严格遵守平台规范</li>
        <li>保护用户隐私数据</li>
        <li>及时响应用户反馈</li>
        <li>定期总结复盘改进</li>
      </ol>
    `,
    date: '2024-01-20',
    author: '运营主管',
    projectId: Number(route.query.projectId),
    attachments: [
      { name: '运营手册.pdf', url: '/files/manual.pdf' },
      { name: '数据分析模板.xlsx', url: '/files/template.xlsx' }
    ]
  },
  {
    id: 2,
    type: 'video',
    title: '项目推广技巧',
    description: '视频教程：如何有效推广你的项目',
    thumbnail: '/video-thumbnail.jpg',
    videoUrl: '/tutorial-video.mp4',
    duration: '15:30',
    projectId: Number(route.query.projectId)
  },
  {
    id: 3,
    type: 'image',
    title: '运营素材示例',
    description: '优秀运营素材展示',
    url: '/tutorial-image.jpg',
    projectId: Number(route.query.projectId)
  }
])

// 新内容表单数据
interface NewContent {
  type: 'tutorial' | 'video' | 'image'
  title: string
  description: string
  content: string
  videoUrl: string
  duration: string
  imageUrl: string
  attachments: { name: string; url: string }[]
}

const newContent = ref<NewContent>({
  type: 'tutorial',
  title: '',
  description: '',
  content: '',
  videoUrl: '',
  duration: '',
  imageUrl: '',
  attachments: []
})

// 根据当前标签筛选内容
const filteredContent = computed(() => {
  const projectId = Number(route.query.projectId)
  
  switch (currentTab.value) {
    case 'tutorial':
      return contentList.value.filter((item): item is TutorialContent => 
        item.type === 'tutorial' && item.projectId === projectId
      )
    case 'video':
      return contentList.value.filter((item): item is VideoContent => 
        item.type === 'video' && item.projectId === projectId
      )
    case 'image':
      return contentList.value.filter((item): item is ImageContent => 
        item.type === 'image' && item.projectId === projectId
      )
    default:
      return []
  }
})

// 获取各类型内容数量
const getContentCount = (type: string) => {
  return contentList.value.filter(item => 
    item.type === type && 
    item.projectId === Number(route.query.projectId)
  ).length
}

// 类型守卫函数
const isTutorialContent = (content: Content): content is TutorialContent => {
  return content.type === 'tutorial'
}

const isVideoContent = (content: Content): content is VideoContent => {
  return content.type === 'video'
}

const isImageContent = (content: Content): content is ImageContent => {
  return content.type === 'image'
}

// 修改播放视频函数
const playVideo = (video: Content) => {
  if (isVideoContent(video)) {
    currentVideo.value = video
    showVideoModal.value = true
  }
}

// 修改显示教程详情函数
const showTutorialDetail = (tutorial: Content) => {
  if (isTutorialContent(tutorial)) {
    currentTutorial.value = tutorial
    showTutorialDetailModal.value = true
  }
}

// 修改编辑内容函数
const editContent = (item: Content) => {
  editingContent.value = item
  const { id, projectId, date, author, ...rest } = item
  
  if (isTutorialContent(item)) {
    newContent.value = {
      ...newContent.value,
      type: 'tutorial',
      title: item.title,
      description: item.description,
      content: item.content,
      attachments: [...item.attachments],
      videoUrl: '',
      duration: '',
      imageUrl: ''
    }
  } else if (isVideoContent(item)) {
    newContent.value = {
      ...newContent.value,
      type: 'video',
      title: item.title,
      description: item.description,
      content: '',
      attachments: [],
      videoUrl: item.videoUrl,
      duration: item.duration,
      imageUrl: ''
    }
  } else if (isImageContent(item)) {
    newContent.value = {
      ...newContent.value,
      type: 'image',
      title: item.title,
      description: item.description,
      content: '',
      attachments: [],
      videoUrl: '',
      duration: '',
      imageUrl: item.url
    }
  }
  
  showAddContentModal.value = true
}

// 删除内容
const deleteContent = (item: Content) => {
  Dialog.confirm({
    title: '确认删除',
    message: '确定要删除这个内容吗？',
  }).then(() => {
    contentList.value = contentList.value.filter(content => content.id !== item.id)
  })
}

// 保存内容
const saveContent = () => {
  const { type, title, description } = newContent.value
  const baseContent = {
    title,
    description,
    projectId: Number(route.query.projectId),
    date: new Date().toISOString().split('T')[0],
    author: '当前用户'
  }

  let content: Content
  
  switch (type) {
    case 'tutorial':
      content = {
        ...baseContent,
        id: editingContent.value?.id || contentList.value.length + 1,
        type: 'tutorial',
        content: newContent.value.content,
        attachments: [...newContent.value.attachments]
      }
      break
    case 'video':
      content = {
        ...baseContent,
        id: editingContent.value?.id || contentList.value.length + 1,
        type: 'video',
        thumbnail: '/video-thumbnail.jpg', // 实际项目中需要处理缩略图
        videoUrl: newContent.value.videoUrl,
        duration: newContent.value.duration
      }
      break
    case 'image':
      content = {
        ...baseContent,
        id: editingContent.value?.id || contentList.value.length + 1,
        type: 'image',
        url: newContent.value.imageUrl
      }
      break
  }

  if (editingContent.value) {
    const index = contentList.value.findIndex(item => item.id === editingContent.value!.id)
    if (index !== -1) {
      contentList.value[index] = content
    }
  } else {
    contentList.value.push(content)
  }
  
  showAddContentModal.value = false
  editingContent.value = null
  newContent.value = {
    type: 'tutorial',
    title: '',
    description: '',
    content: '',
    videoUrl: '',
    duration: '',
    imageUrl: '',
    attachments: []
  }
}

// 处理图片上传
const handleImageUpload = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.files && input.files[0]) {
    const reader = new FileReader()
    reader.onload = (e) => {
      newContent.value.imageUrl = e.target?.result as string
    }
    reader.readAsDataURL(input.files[0])
  }
}

// 下载附件
const downloadAttachment = (attachment: any) => {
  // 实际项目中需要调用后端API下载附件
  console.log('下载附件:', attachment)
}

// 插入格式化文本
const insertFormat = (type: string) => {
  let template = ''
  switch (type) {
    case 'h2':
      template = '\n<h2>标题</h2>\n'
      break
    case 'b':
      template = '<strong>加粗文本</strong>'
      break
    case 'ul':
      template = '\n<ul>\n  <li>列表项1</li>\n  <li>列表项2</li>\n</ul>\n'
      break
    case 'ol':
      template = '\n<ol>\n  <li>列表项1</li>\n  <li>列表项2</li>\n</ol>\n'
      break
  }
  
  const textarea = document.querySelector('textarea') as HTMLTextAreaElement
  const start = textarea.selectionStart
  const end = textarea.selectionEnd
  const text = newContent.value.content
  
  newContent.value.content = text.substring(0, start) + template + text.substring(end)
  
  // 设置光标位置
  setTimeout(() => {
    textarea.focus()
    textarea.setSelectionRange(start + template.length, start + template.length)
  }, 0)
}

// 处理附件上传
const handleAttachmentUpload = (event: Event) => {
  const input = event.target as HTMLInputElement
  if (input.files && input.files[0]) {
    const file = input.files[0]
    // 实际项目中这里需要调用上传API
    newContent.value.attachments.push({
      name: file.name,
      url: URL.createObjectURL(file)
    })
  }
}

// 移除附件
const removeAttachment = (index: number) => {
  newContent.value.attachments.splice(index, 1)
}
</script>

<style scoped>
.aspect-w-16 {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 */
}

.aspect-w-16 > * {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}

.aspect-square {
  aspect-ratio: 1 / 1;
}

.tutorial-detail-dialog {
  :deep(.van-dialog__content) {
    max-height: 70vh;
    overflow-y: auto;
  }
}

/* 富文本内容样式 */
.prose {
  h2 {
    font-size: 1.5rem;
    font-weight: 600;
    margin-top: 2rem;
    margin-bottom: 1rem;
  }
  
  p {
    margin-bottom: 1rem;
    line-height: 1.6;
  }
  
  ul, ol {
    margin-bottom: 1rem;
    padding-left: 1.5rem;
  }
  
  li {
    margin-bottom: 0.5rem;
  }
  
  ul {
    list-style-type: disc;
  }
  
  ol {
    list-style-type: decimal;
  }
}

/* 富文本编辑器样式 */
textarea {
  font-family: monospace;
  line-height: 1.6;
  resize: vertical;
}
</style> 