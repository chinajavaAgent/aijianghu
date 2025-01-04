<template>
  <div class="min-h-[calc(100vh-4rem)] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full">
      <div class="card p-8">
        <div class="text-center">
          <h2 class="text-3xl font-extrabold text-gray-900">登录账号</h2>
          <p class="mt-2 text-sm text-gray-600">
            或者
            <router-link to="/register" class="font-medium text-indigo-600 hover:text-indigo-500">
              注册新账号
            </router-link>
          </p>
        </div>
        <form class="mt-8 space-y-6" @submit.prevent="handleLogin">
          <div class="space-y-4">
            <div>
              <label for="phone" class="block text-sm font-medium text-gray-700">手机号码</label>
              <div class="mt-1">
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
            </div>
            <div>
              <label for="code" class="block text-sm font-medium text-gray-700">验证码</label>
              <div class="mt-1 flex space-x-3">
                <input
                  id="code"
                  v-model="form.code"
                  type="text"
                  required
                  class="input-primary"
                  placeholder="请输入验证码"
                  maxlength="6"
                />
                <button
                  type="button"
                  class="btn-secondary whitespace-nowrap"
                  :disabled="countdown > 0"
                  @click="sendCode"
                >
                  {{ countdown > 0 ? `${countdown}秒后重试` : '获取验证码' }}
                </button>
              </div>
            </div>
          </div>

          <div class="flex justify-center mt-8">
            <button type="submit" class="btn-3d w-48">
              登录
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const countdown = ref(0)

const form = reactive({
  phone: '',
  code: ''
})

const sendCode = async () => {
  try {
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      alert('请输入正确的手机号码')
      return
    }
    // TODO: 调用发送验证码API
    // await sendVerificationCode(form.phone)
    countdown.value = 60
    const timer = setInterval(() => {
      countdown.value--
      if (countdown.value <= 0) {
        clearInterval(timer)
      }
    }, 1000)
  } catch (error) {
    console.error('发送验证码失败:', error)
  }
}

const handleLogin = async () => {
  try {
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      alert('请输入正确的手机号码')
      return
    }
    if (!/^\d{6}$/.test(form.code)) {
      alert('请输入6位验证码')
      return
    }
    // TODO: 调用登录API
    // const res = await login(form)
    // userStore.setUser(res)
    router.push('/')
  } catch (error) {
    console.error('登录失败:', error)
  }
}
</script> 