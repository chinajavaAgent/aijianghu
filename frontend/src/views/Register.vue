<template>
  <div class="min-h-screen bg-[#FFF8E1]">

    <!-- 主要内容 -->
    <div class="max-w-md mx-auto px-4 py-8">
      <div class="space-y-6">

        <!-- 注册表单 -->
        <div class="card p-6 space-y-6">
          <h2 class="heading-1 text-center">注册账号</h2>
          
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

            <div class="text-center space-y-4">
              <p class="text-small">
                注册即表示同意
                <a href="#" class="text-accent-color">服务条款</a>
                和
                <a href="#" class="text-accent-color">隐私政策</a>
              </p>

              <p class="text-sm text-gray-600">
                已有账号？
                <router-link to="/login" class="text-accent-color hover:underline">
                  立即登录
                </router-link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { register, checkPhone } from '../api/user'
import type { RegisterRequest } from '../types/user'
import toast from '../utils/toast'

const router = useRouter()
const route = useRoute()

// 从URL和缓存中获取分享码
const shareCode = ref(route.query.shareCode as string || '')
const cachedShareCode = ref(localStorage.getItem('shareCode') || '')

// 在组件挂载时处理分享码
onMounted(() => {
  // 如果URL中有分享码，保存到缓存中
  if (shareCode.value) {
    localStorage.setItem('shareCode', shareCode.value)
    cachedShareCode.value = shareCode.value
  }
})

const form = reactive<RegisterRequest>({
  wechat: '',
  phone: '',
  realName: '',
  password: '',
  confirmPassword: '',
  referrerId: undefined,
  shareCode: undefined // 初始化时不设置shareCode
})

// 在注册时设置最新的shareCode
const handleRegister = async () => {
  try {
    // 验证手机号格式
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      toast.error('请输入正确的手机号码')
      return
    }

    // 验证密码长度
    if (form.password.length < 6) {
      toast.error('密码长度不能少于6位')
      return
    }

    // 验证两次密码是否一致
    if (form.password !== form.confirmPassword) {
      toast.error('两次输入的密码不一致')
      return
    }

    // 检查手机号是否已被注册
    const { data: phoneExists } = await checkPhone(form.phone)
    if (phoneExists) {
      toast.error('该手机号已被注册')
      return
    }

    // 设置注册时的分享码（优先使用URL中的，其次使用缓存中的）
    form.shareCode = shareCode.value || cachedShareCode.value

    // 调用注册API
    const response = await register(form)
    if (response.code === 200) {
      // 注册成功后清除分享码缓存
      localStorage.removeItem('shareCode')
      toast.success('注册成功')
      router.push('/login')
    }
  } catch (error: any) {
    console.error('注册失败:', error)
    toast.error(error.message || '注册失败，请稍后重试')
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