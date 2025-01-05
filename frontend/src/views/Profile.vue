<template>
  <div class="min-h-screen bg-[#FFF8E1] pb-20">
    <div class="container mx-auto px-4 py-6">
      <!-- 用户信息卡片 -->
      <div class="bg-white rounded-xl shadow-md p-6 mb-6">
        <div class="flex items-center">
          <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center">
            <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
            </svg>
          </div>
          <div class="ml-4">
            <h2 class="text-xl font-bold text-gray-800">{{ userStore.userInfo?.nickname || '未设置昵称' }}</h2>
            <p class="text-gray-600 mt-1">手机号：{{ userStore.userInfo?.phone || '未绑定' }}</p>
          </div>
        </div>
      </div>

      <!-- 功能列表 -->
      <div class="bg-white rounded-xl shadow-md overflow-hidden">
        <div class="divide-y">
          <!-- 我的订单 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="checkLogin(() => router.push('/orders'))">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-orange-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">我的订单</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>

          <!-- 账号设置 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="checkLogin(() => {})">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">账号设置</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>

          <!-- 联系客服 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="checkLogin(showCustomerService)">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">联系客服</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>

          <!-- AI锦囊管理 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="checkLogin(() => router.push('/opportunity-manage'))">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">AI锦囊管理</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>

          <!-- 运营物料 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="checkLogin(() => router.push('/operation-materials'))">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-yellow-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">运营物料</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>

          <!-- 锦囊管理员维护 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="checkLogin(() => router.push('/admin-manage'))">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">锦囊管理员维护</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>

          <!-- 退出登录 -->
          <div class="flex items-center justify-between p-4 hover:bg-gray-50" @click="handleLogout">
            <div class="flex items-center">
              <div class="w-10 h-10 bg-red-100 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
                </svg>
              </div>
              <span class="ml-3 text-gray-700">退出登录</span>
            </div>
            <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>
        </div>
      </div>
    </div>

    <!-- 客服弹窗 -->
    <van-dialog
      v-model:show="showServiceDialog"
      title="联系客服"
      :show-confirm-button="false"
      :close-on-click-overlay="true"
      :close-on-popstate="true"
      class="rounded-lg"
    >
      <div class="p-6 text-center">
        <div class="flex justify-center mb-4">
          <svg class="w-16 h-16 text-green-500" fill="currentColor" viewBox="0 0 24 24">
            <path d="M8.2 9.6c-.4 0-.7.1-.9.3-.2.2-.3.5-.3.8 0 .4.1.7.3.9.2.2.5.3.9.3.3 0 .6-.1.8-.3.2-.2.3-.5.3-.9 0-.3-.1-.6-.3-.8-.2-.2-.5-.3-.8-.3zm7.6 0c-.4 0-.7.1-.9.3-.2.2-.3.5-.3.8 0 .4.1.7.3.9.2.2.5.3.9.3.3 0 .6-.1.8-.3.2-.2.3-.5.3-.9 0-.3-.1-.6-.3-.8-.2-.2-.5-.3-.8-.3zm-3.8 10.2c-4.8 0-8.7-3.9-8.7-8.7 0-4.8 3.9-8.7 8.7-8.7 4.8 0 8.7 3.9 8.7 8.7 0 4.8-3.9 8.7-8.7 8.7zm0-15.9c-4 0-7.2 3.2-7.2 7.2 0 4 3.2 7.2 7.2 7.2 4 0 7.2-3.2 7.2-7.2 0-4-3.2-7.2-7.2-7.2z"/>
          </svg>
        </div>
        <p class="text-lg font-medium text-gray-900 mb-2">微信号：AI群江湖</p>
        <p class="text-sm text-gray-500 mb-4">扫码或搜索添加客服微信</p>
        <div class="flex justify-center">
          <img src="https://learn-app.oss-cn-hangzhou.aliyuncs.com/image/qrcode.jpg" alt="客服微信二维码" class="w-48 h-48 object-cover rounded-lg shadow-sm">
        </div>
        <p class="text-xs text-gray-400 mt-4">工作时间：周一至周日 9:00-22:00</p>
      </div>
    </van-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Dialog, showToast } from 'vant'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 客服弹窗控制
const showServiceDialog = ref(false)

// 检查登录状态
const checkLogin = (callback: () => void) => {
  const token = localStorage.getItem('token')
  if (!token) {
    router.push({
      path: '/login',
      query: { redirect: route.fullPath }
    })
    return
  }
  callback()
}

// 显示客服弹窗
const showCustomerService = () => {
  showServiceDialog.value = true
}

const handleLogout = () => {
  Dialog.confirm({
    title: '提示',
    message: '确定要退出登录吗？',
  })
    .then(() => {
      userStore.clearUser()
      router.push('/login')
    })
    .catch(() => {
      // 取消退出
    })
}
</script>

<style scoped>
.container {
  max-width: 768px;
}

:deep(.van-dialog) {
  border-radius: 0.75rem;
}

:deep(.van-dialog__header) {
  padding: 1rem;
  font-weight: 600;
  font-size: 1.125rem;
}
</style> 