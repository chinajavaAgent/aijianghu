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
          <button @click="showAddDialog = true" 
            class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
            添加管理员
          </button>
        </div>

        <!-- 管理员列表 -->
        <div class="space-y-4">
          <div v-for="admin in admins" :key="admin.id" 
            class="border rounded-lg p-4 hover:shadow-md transition-shadow">
            <div class="flex justify-between items-start">
              <div class="flex items-start space-x-4">
                <img :src="admin.wechatQrCode" alt="微信二维码" 
                  class="w-16 h-16 object-cover rounded-lg" 
                  v-if="admin.wechatQrCode">
                <div>
                  <h3 class="font-medium text-lg">{{ admin.realName }}</h3>
                  <p class="text-gray-600 text-sm mt-1">手机：{{ admin.phone }}</p>
                  <p class="text-gray-600 text-sm">微信号：{{ admin.wechatId }}</p>
                </div>
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

    <!-- 添加管理员弹窗 -->
    <div v-if="showAddDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-md">
        <h2 class="text-xl font-bold mb-4">添加管理员</h2>
        <form @submit.prevent="handleSubmit" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">手机号码</label>
            <input v-model="formData.phone" type="tel" required
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="请输入手机号码">
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">微信号</label>
            <input v-model="formData.wechatId" type="text" required
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="请输入微信号">
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">真实姓名</label>
            <input v-model="formData.realName" type="text" required
              class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="请输入真实姓名">
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">微信二维码</label>
            <div class="flex items-center space-x-2">
              <input ref="fileInput" type="file" accept="image/*" @change="handleFileChange"
                class="hidden">
              <button type="button" @click="$refs.fileInput.click()"
                class="px-4 py-2 border rounded-lg hover:bg-gray-50">
                选择图片
              </button>
              <span v-if="formData.wechatQrCode" class="text-sm text-gray-600">
                已选择图片
              </span>
            </div>
            <img v-if="previewUrl" :src="previewUrl" alt="预览"
              class="mt-2 w-32 h-32 object-cover rounded-lg">
          </div>

          <div class="flex justify-end space-x-2 mt-6">
            <button type="button" @click="showAddDialog = false"
              class="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded-lg">
              取消
            </button>
            <button type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
              确认添加
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import 'element-plus/dist/index.css'
import type { Admin, AdminFormData } from '@/types/admin'

// 弹窗显示控制
const showAddDialog = ref(false)

// 表单数据
const formData = ref<AdminFormData>({
  phone: '',
  wechatId: '',
  realName: '',
  wechatQrCode: null
})

// 图片预览
const previewUrl = ref('')
const fileInput = ref<HTMLInputElement | null>(null)

// 处理文件选择
const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files && target.files[0]) {
    formData.value.wechatQrCode = target.files[0]
    previewUrl.value = URL.createObjectURL(target.files[0])
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    const formDataToSend = new FormData()
    formDataToSend.append('phone', formData.value.phone)
    formDataToSend.append('wechatId', formData.value.wechatId)
    formDataToSend.append('realName', formData.value.realName)
    if (formData.value.wechatQrCode) {
      formDataToSend.append('wechatQrCode', formData.value.wechatQrCode)
    }

    await axios.post('/api/admin/add', formDataToSend, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    ElMessage.success('添加管理员成功')
    showAddDialog.value = false
    // 重置表单
    formData.value = {
      phone: '',
      wechatId: '',
      realName: '',
      wechatQrCode: null
    }
    previewUrl.value = ''
    
    // 刷新管理员列表
    loadAdminList()
  } catch (error) {
    ElMessage.error('添加管理员失败')
    console.error('添加管理员失败:', error)
  }
}

// 加载管理员列表
const admins = ref<Admin[]>([])
const loadAdminList = async () => {
  try {
    const response = await axios.get('/api/admin/list')
    admins.value = response.data
  } catch (error) {
    console.error('获取管理员列表失败:', error)
    ElMessage.error('获取管理员列表失败')
  }
}

// 初始化
onMounted(() => {
  loadAdminList()
})
</script>

<style scoped>
.container {
  max-width: 768px;
}
</style> 