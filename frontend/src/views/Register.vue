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
                <label for="referrer" class="block text-sm mb-1">推荐人</label>
                <input
                  id="referrer"
                  v-model="form.referrer"
                  type="text"
                  class="input-primary"
                  placeholder="请输入推荐人（选填）"
                />
              </div>

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
                <label for="nickname" class="block text-sm mb-1">昵称</label>
                <input
                  id="nickname"
                  v-model="form.nickname"
                  type="text"
                  required
                  class="input-primary"
                  placeholder="请输入昵称"
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
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import Icon from '../components/Icons.vue'

const router = useRouter()

const form = reactive({
  wechat: '',
  phone: '',
  nickname: '',
  referrer: ''
})

const handleRegister = async () => {
  try {
    // 验证手机号格式
    if (!/^1[3-9]\d{9}$/.test(form.phone)) {
      alert('请输入正确的手机号码')
      return
    }
    // TODO: 调用注册API
    // await register(form)
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