import { defineStore } from 'pinia'
import type { User } from '../types/user'
import { getUserInfo } from '@/api/user'

interface UserState extends User {
  token: string;
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    id: 0,
    username: '',
    nickname: '',
    realName: '',
    phone: '',
    avatar: '',
    level: 1,
    points: 0,
    createTime: '',
    updateTime: '',
    token: ''
  }),
  
  getters: {
    isLoggedIn: (state): boolean => {
      return !!state.token && state.id > 0
    }
  },
  
  actions: {
    setUser(user: Partial<UserState>) {
      if (typeof user.level === 'undefined') {
        user.level = 1
      }
      Object.assign(this, user)
      if (user.token) {
        localStorage.setItem('token', user.token)
      }
      localStorage.setItem('userInfo', JSON.stringify(this.$state))
    },
    
    clearUser() {
      this.id = 0
      this.username = ''
      this.nickname = ''
      this.realName = ''
      this.phone = ''
      this.avatar = ''
      this.level = 1
      this.points = 0
      this.createTime = ''
      this.updateTime = ''
      this.token = ''
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    },

    initUserState() {
      const userInfo = localStorage.getItem('userInfo')
      const token = localStorage.getItem('token')
      if (userInfo) {
        try {
          const savedState = JSON.parse(userInfo)
          this.setUser(savedState)
        } catch (e) {
          this.clearUser()
        }
      } else if (token) {
        this.token = token
      }
    },

    async updateUserInfo() {
      if (!this.isLoggedIn || !this.id) return

      try {
        const response = await getUserInfo(this.id)
        if (response.data) {
          const token = this.token
          this.setUser({
            ...response.data,
            token
          })
          return true
        }
      } catch (error) {
        console.error('更新用户信息失败:', error)
        return false
      }
    }
  },

  persist: true
}) 