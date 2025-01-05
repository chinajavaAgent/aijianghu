<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <!-- 顶部导航栏 -->
    <div class="bg-white shadow-sm sticky top-0 z-10">
      <div class="container mx-auto px-4">
        <h1 class="text-xl font-bold text-gray-800 py-4">AI锦囊管理</h1>
      </div>
    </div>

    <div class="container mx-auto px-4 py-6">
      <!-- 锦囊列表 -->
      <div class="bg-white rounded-xl shadow-md p-6 mb-6">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-lg font-semibold">锦囊列表</h2>
          <button @click="showAddPackageDialog = true" 
            class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
            添加锦囊
          </button>
        </div>

        <!-- 锦囊列表 -->
        <div class="space-y-6">
          <div v-for="pack in packages" :key="pack.id" 
            class="border rounded-lg p-6 hover:shadow-md transition-shadow">
            <!-- 锦囊基本信息 -->
            <div class="flex justify-between items-start mb-4">
              <div>
                <h3 class="text-xl font-medium">{{ pack.title }}</h3>
                <p class="text-gray-600 mt-1">价格：￥{{ pack.price }}</p>
              </div>
              <div class="flex space-x-2">
                <button @click="editPackage(pack)" 
                  class="px-3 py-1 text-sm text-blue-600 border border-blue-600 rounded hover:bg-blue-50">
                  编辑
                </button>
                <button @click="deletePackage(pack.id)" 
                  class="px-3 py-1 text-sm text-red-600 border border-red-600 rounded hover:bg-red-50">
                  删除
                </button>
              </div>
            </div>

            <!-- 项目列表 -->
            <div class="space-y-4">
              <div v-for="project in pack.projects" :key="project.id" 
                class="bg-gray-50 rounded-lg p-4">
                <div class="flex justify-between items-start mb-3">
                  <h4 class="font-medium">{{ project.title }}</h4>
                  <div class="flex space-x-2">
                    <button @click="editProject(pack.id, project)" 
                      class="px-2 py-1 text-xs text-blue-600 hover:text-blue-800">
                      编辑
                    </button>
                    <button @click="deleteProject(pack.id, project.id)" 
                      class="px-2 py-1 text-xs text-red-600 hover:text-red-800">
                      删除
                    </button>
                  </div>
                </div>
                <div class="grid grid-cols-2 gap-4 text-sm">
                  <div>
                    <p class="text-gray-600">项目介绍：{{ project.introduction || '未设置' }}</p>
                  </div>
                  <div>
                    <p class="text-gray-600">成功案例：{{ project.cases?.length || 0 }}个</p>
                  </div>
                  <div>
                    <p class="text-gray-600">视频教程：{{ project.videos?.length || 0 }}个</p>
                  </div>
                  <div>
                    <p class="text-gray-600">项目福利：{{ project.benefits?.length || 0 }}项</p>
                  </div>
                </div>
              </div>
              <!-- 添加项目按钮 -->
              <button @click="showAddProjectDialog(pack.id)" 
                class="w-full py-2 border-2 border-dashed border-gray-300 rounded-lg text-gray-500 hover:border-blue-500 hover:text-blue-500 transition-colors">
                + 添加项目
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑锦囊弹窗 -->
    <van-dialog
      v-model:show="showAddPackageDialog"
      :title="editingPackage ? '编辑锦囊' : '添加锦囊'"
      show-cancel-button
      @confirm="handlePackageSubmit"
    >
      <div class="p-4">
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">锦囊名称</label>
          <input v-model="packageForm.title" type="text" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="请输入锦囊名称">
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">价格</label>
          <input v-model="packageForm.price" type="number" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="请输入价格">
        </div>
      </div>
    </van-dialog>

    <!-- 添加/编辑项目弹窗 -->
    <van-dialog
      v-model:show="showProjectDialog"
      :title="editingProject ? '编辑项目' : '添加项目'"
      show-cancel-button
      @confirm="handleProjectSubmit"
    >
      <div class="p-4">
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">项目名称</label>
          <input v-model="projectForm.title" type="text" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            placeholder="请输入项目名称">
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">项目介绍</label>
          <textarea v-model="projectForm.introduction" 
            class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            rows="3" placeholder="请输入项目介绍"></textarea>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">成功案例</label>
          <div v-for="(case_, index) in projectForm.cases" :key="index" class="flex items-center mb-2">
            <input v-model="case_.title" type="text" 
              class="flex-1 px-3 py-2 border rounded-lg mr-2"
              placeholder="案例标题">
            <button @click="projectForm.cases.splice(index, 1)" 
              class="text-red-600 hover:text-red-800">删除</button>
          </div>
          <button @click="projectForm.cases.push({ title: '' })" 
            class="text-blue-600 hover:text-blue-800 text-sm">
            + 添加案例
          </button>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">视频教程</label>
          <div v-for="(video, index) in projectForm.videos" :key="index" class="flex flex-col mb-2">
            <input v-model="video.title" type="text" 
              class="w-full px-3 py-2 border rounded-lg mb-2"
              placeholder="视频标题">
            <div class="flex items-center">
              <input v-model="video.url" type="text" 
                class="flex-1 px-3 py-2 border rounded-lg mr-2"
                placeholder="视频链接">
              <button @click="projectForm.videos.splice(index, 1)" 
                class="text-red-600 hover:text-red-800 whitespace-nowrap">删除</button>
            </div>
          </div>
          <button @click="projectForm.videos.push({ title: '', url: '' })" 
            class="text-blue-600 hover:text-blue-800 text-sm">
            + 添加视频
          </button>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">项目福利</label>
          <div v-for="(benefit, index) in projectForm.benefits" :key="index" class="flex items-center mb-2">
            <input v-model="projectForm.benefits[index]" type="text" 
              class="flex-1 px-3 py-2 border rounded-lg mr-2"
              placeholder="福利内容">
            <button @click="projectForm.benefits.splice(index, 1)" 
              class="text-red-600 hover:text-red-800">删除</button>
          </div>
          <button @click="projectForm.benefits.push('')" 
            class="text-blue-600 hover:text-blue-800 text-sm">
            + 添加福利
          </button>
        </div>
      </div>
    </van-dialog>
  </div>
</template> 

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { Dialog, showToast } from 'vant'

interface Case {
  title: string
}

interface Video {
  title: string
  url: string
}

interface Project {
  id: number
  title: string
  introduction: string
  cases: Case[]
  videos: Video[]
  benefits: string[]
}

interface Package {
  id: number
  title: string
  price: number
  projects: Project[]
}

// 锦囊列表数据
const packages = ref<Package[]>([
  {
    id: 1,
    title: 'AI写作变现',
    price: 10,
    projects: [
      {
        id: 1,
        title: '新手入门',
        introduction: '零基础快速入门AI写作',
        cases: [
          { title: '新手小白月入过万' }
        ],
        videos: [
          { title: 'ChatGPT写作入门教程', url: 'https://example.com/video1' }
        ],
        benefits: ['ChatGPT高级账号', '爆款文案模板100+']
      }
    ]
  }
])

// 添加/编辑锦囊相关
const showAddPackageDialog = ref(false)
const editingPackage = ref(false)
const packageForm = reactive({
  id: null as number | null,
  title: '',
  price: 0
})

// 添加/编辑项目相关
const showProjectDialog = ref(false)
const editingProject = ref(false)
const currentPackageId = ref<number | null>(null)
const projectForm = reactive({
  id: null as number | null,
  title: '',
  introduction: '',
  cases: [] as Case[],
  videos: [] as Video[],
  benefits: [] as string[]
})

// 锦囊相关方法
const editPackage = (pack: Package) => {
  editingPackage.value = true
  packageForm.id = pack.id
  packageForm.title = pack.title
  packageForm.price = pack.price
  showAddPackageDialog.value = true
}

const deletePackage = (id: number) => {
  Dialog.confirm({
    title: '确认删除',
    message: '确定要删除这个锦囊吗？'
  }).then(() => {
    const index = packages.value.findIndex(p => p.id === id)
    if (index !== -1) {
      packages.value.splice(index, 1)
      showToast('删除成功')
    }
  })
}

const handlePackageSubmit = () => {
  if (!packageForm.title || packageForm.price <= 0) {
    showToast('请填写完整信息')
    return
  }

  if (editingPackage.value) {
    // 更新锦囊
    const index = packages.value.findIndex(p => p.id === packageForm.id)
    if (index !== -1) {
      packages.value[index] = { 
        ...packages.value[index], 
        title: packageForm.title,
        price: packageForm.price
      }
    }
  } else {
    // 添加锦囊
    packages.value.push({
      id: Date.now(),
      title: packageForm.title,
      price: packageForm.price,
      projects: []
    })
  }

  showToast('保存成功')
  resetPackageForm()
}

const resetPackageForm = () => {
  packageForm.id = null
  packageForm.title = ''
  packageForm.price = 0
  editingPackage.value = false
  showAddPackageDialog.value = false
}

// 项目相关方法
const showAddProjectDialog = (packageId: number) => {
  currentPackageId.value = packageId
  showProjectDialog.value = true
}

const editProject = (packageId: number, project: Project) => {
  editingProject.value = true
  currentPackageId.value = packageId
  projectForm.id = project.id
  projectForm.title = project.title
  projectForm.introduction = project.introduction
  projectForm.cases = [...project.cases]
  projectForm.videos = [...project.videos]
  projectForm.benefits = [...project.benefits]
  showProjectDialog.value = true
}

const deleteProject = (packageId: number, projectId: number) => {
  Dialog.confirm({
    title: '确认删除',
    message: '确定要删除这个项目吗？'
  }).then(() => {
    const packageIndex = packages.value.findIndex(p => p.id === packageId)
    if (packageIndex !== -1) {
      const projectIndex = packages.value[packageIndex].projects.findIndex(p => p.id === projectId)
      if (projectIndex !== -1) {
        packages.value[packageIndex].projects.splice(projectIndex, 1)
        showToast('删除成功')
      }
    }
  })
}

const handleProjectSubmit = () => {
  if (!projectForm.title || !projectForm.introduction) {
    showToast('请填写完整信息')
    return
  }

  const packageIndex = packages.value.findIndex(p => p.id === currentPackageId.value)
  if (packageIndex === -1) return

  if (editingProject.value) {
    // 更新项目
    const projectIndex = packages.value[packageIndex].projects.findIndex(p => p.id === projectForm.id)
    if (projectIndex !== -1) {
      packages.value[packageIndex].projects[projectIndex] = {
        id: projectForm.id!,
        title: projectForm.title,
        introduction: projectForm.introduction,
        cases: [...projectForm.cases],
        videos: [...projectForm.videos],
        benefits: [...projectForm.benefits]
      }
    }
  } else {
    // 添加项目
    packages.value[packageIndex].projects.push({
      id: Date.now(),
      title: projectForm.title,
      introduction: projectForm.introduction,
      cases: [...projectForm.cases],
      videos: [...projectForm.videos],
      benefits: [...projectForm.benefits]
    })
  }

  showToast('保存成功')
  resetProjectForm()
}

const resetProjectForm = () => {
  projectForm.id = null
  projectForm.title = ''
  projectForm.introduction = ''
  projectForm.cases = []
  projectForm.videos = []
  projectForm.benefits = []
  editingProject.value = false
  currentPackageId.value = null
  showProjectDialog.value = false
}
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 