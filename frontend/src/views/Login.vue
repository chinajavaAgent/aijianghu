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
                <label for="code" class="block text-sm mb-1">验证码</label>
                <div class="flex space-x-2">
                  <input
                    id="code"
                    v-model="form.code"
                    type="text"
                    required
                    class="input-primary flex-1"
                    placeholder="请输入验证码"
                    maxlength="6"
                  />
                  <button 
                    type="button" 
                    class="btn-primary whitespace-nowrap px-4"
                    :disabled="countdown > 0"
                    @click="handleSendCode"
                  >
                    {{ countdown > 0 ? `${countdown}s后重试` : '获取验证码' }}
                  </button>
                </div>
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
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import Icon from '../components/Icons.vue'

const router = useRouter()
const countdown = ref(0)

const form = reactive({
  phone: '',
  code: ''
})

const handleSendCode = async () => {
  // 验证手机号格式
  if (!/^1[3-9]\d{9}$/.test(form.phone)) {
    alert('请输入正确的手机号码')
    return
  }

  // 开始倒计时
  countdown.value = 60
  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)

  try {
    // TODO: 调用发送验证码API
    // await sendCode(form.phone)
  } catch (error) {
    console.error('发送验证码失败:', error)
  }
}

const handleLogin = async () => {
  try {
    // 验证手机号格式
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      alert('请输入正确的手机号码')
      return
    }
    // 验证验证码格式
    if (!/^\d{6}$/.test(form.code)) {
      alert('请输入6位数字验证码')
      return
    }
    // TODO: 调用登录API
    // await login(form)
    router.push('/')
  } catch (error) {
    console.error('登录失败:', error)
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

/* 禁用状态的按钮样式 */
.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style> 