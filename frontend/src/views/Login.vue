<template>
  <div class="min-h-screen bg-[#FFF8E1]">

    <!-- 主要内容 -->
    <div class="max-w-md mx-auto px-4 py-8">
      <div class="space-y-6">

        <!-- 登录表单 -->
        <div class="card p-6 space-y-6">
          <h2 class="heading-1 text-center">登录账号</h2>
          
          <form @submit.prevent="handleLogin" class="space-y-5">
            <div class="space-y-4">
              <div class="group">
                <label for="phone" class="block text-sm mb-1">手机号码</label>
                <input
                  id="phone"
                  v-model="form.phone"
                  type="tel"
                  required
                  class="input-primary"
                  placeholder="请输入手机号码"
                  pattern="[0-9]{11}"
                  title="请输入11位手机号码"
                />
              </div>

              <div class="group">
                <label for="password" class="block text-sm mb-1">密码</label>
                <input
                  id="password"
                  v-model="form.password"
                  type="password"
                  required
                  class="input-primary"
                  placeholder="请输入密码"
                  minlength="6"
                  maxlength="20"
                />
              </div>
            </div>

            <button type="submit" class="btn-primary w-full py-3">
              登录
            </button>

            <div class="flex justify-center space-x-4 text-sm">
              <router-link to="/register" class="text-accent-color">
                注册新账号
              </router-link>
              <span class="text-gray-300">|</span>
              <a href="#" class="text-accent-color">
                忘记密码
              </a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { login } from '../api/user'
import toast from '../utils/toast'
import { useUserStore } from '@/store/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const form = reactive({
  phone: '',
  password: ''
})

const handleLogin = async () => {
  try {
    // 验证手机号格式
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      toast.error('请输入正确的手机号码')
      return
    }

    // 验证密码格式
    if (form.password.length < 6) {
      toast.error('密码长度不能少于6位')
      return
    }

    // 调用登录API
    const response = await login(form)
    
    // 保存用户信息
    userStore.setUser({
      ...response.data.user,
      token: response.data.token
    })
    
    // 登录成功后跳转
    toast.success('登录成功')
    const redirect = route.query.redirect
    router.push(redirect?.toString() || '/')
  } catch (error) {
    console.error('登录失败:', error)
    toast.error('登录失败，请检查账号密码')
  }
}
</script>

<style scoped>
.group label {
  color: var(--text-secondary);
}

.group input::placeholder {
  color: var(--text-tertiary);
}
</style> 