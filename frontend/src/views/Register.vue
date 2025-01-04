<template>
  <div class="min-h-screen bg-[#FFF8E1]">

    <!-- 主要内容 -->
    <div class="max-w-md mx-auto px-4 py-8">
      <div class="space-y-6">

        <!-- 注册表单 -->
        <div class="card p-6 space-y-6">
          <h2 class="heading-1 text-center">注册账号</h2>
          
          <!-- 推荐人信息展示 -->
          <div class="bg-[#FFF3CD] rounded-lg p-4">
            <div class="flex items-center justify-between">
              <span class="text-sm text-[#856404]">推荐人</span>
              <span class="font-medium text-[#856404]">{{ referrer || '暂无推荐人' }}</span>
            </div>
          </div>
          
          <form @submit.prevent="handleRegister" class="space-y-5">
            <div class="space-y-4">
              <div class="group">
                <label for="wechat" class="block text-sm mb-1">微信号</label>
                <input
                  id="wechat"
                  v-model="form.wechat"
                  type="text"
                  required
                  class="input-primary"
                  placeholder="请输入微信号"
                />
              </div>

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
                  placeholder="请输入密码（6-20位字符）"
                  minlength="6"
                  maxlength="20"
                />
              </div>

              <div class="group">
                <label for="confirmPassword" class="block text-sm mb-1">确认密码</label>
                <input
                  id="confirmPassword"
                  v-model="form.confirmPassword"
                  type="password"
                  required
                  class="input-primary"
                  placeholder="请再次输入密码"
                  minlength="6"
                  maxlength="20"
                />
              </div>

              <div class="group">
                <label for="realName" class="block text-sm mb-1">真实姓名</label>
                <input
                  id="realName"
                  v-model="form.realName"
                  type="text"
                  required
                  class="input-primary"
                  placeholder="请输入真实姓名"
                />
              </div>
            </div>

            <button type="submit" class="btn-primary w-full py-3">
              注册
            </button>

            <p class="text-small text-center">
              注册即表示同意
              <a href="#" class="text-accent-color">服务条款</a>
              和
              <a href="#" class="text-accent-color">隐私政策</a>
            </p>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { register, checkPhone } from '../api/user'
import type { RegisterRequest } from '../types/user'

const router = useRouter()
const route = useRoute()

// 从路由参数中获取推荐人信息
const referrer = ref(route.query.referrer as string || '')

const form = reactive<RegisterRequest>({
  wechat: '',
  phone: '',
  realName: '',
  password: '',
  confirmPassword: '',
  referrerId: undefined
})

const handleRegister = async () => {
  try {
    // 验证手机号格式
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      alert('请输入正确的手机号码')
      return
    }

    // 验证密码长度
    if (form.password.length < 6) {
      alert('密码长度不能少于6位')
      return
    }

    // 验证两次密码是否一致
    if (form.password !== form.confirmPassword) {
      alert('两次输入的密码不一致')
      return
    }

    // 检查手机号是否已被注册
    const { data: phoneExists } = await checkPhone(form.phone)
    if (phoneExists) {
      alert('该手机号已被注册')
      return
    }

    // 如果有推荐人，转换为数字ID
    if (referrer.value) {
      form.referrerId = parseInt(referrer.value)
    }

    // 调用注册API
    await register(form)
    alert('注册成功')
    router.push('/login')
  } catch (error) {
    console.error('注册失败:', error)
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