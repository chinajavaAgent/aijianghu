import { defineStore } from 'pinia'
import type { User } from '../types/user'

interface UserState extends User {
  token: string;
}

export const useUserStore = defineStore({
  id: 'user',
  state: (): UserState => ({
    id: 0,
    username: '',
    nickname: '',
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
      localStorage.setItem('userInfo', JSON.stringify(this.$state))
    },
    
    clearUser() {
      this.id = 0
      this.username = ''
      this.nickname = ''
      this.phone = ''
      this.avatar = ''
      this.level = 1
      this.points = 0
      this.createTime = ''
      this.updateTime = ''
      this.token = ''
      localStorage.removeItem('userInfo')
    },

    initUserState() {
      const userInfo = localStorage.getItem('userInfo')
      if (userInfo) {
        try {
          const savedState = JSON.parse(userInfo)
          this.setUser(savedState)
        } catch (e) {
          this.clearUser()
        }
      }
    }
  },

  persist: {
    enabled: true,
    strategies: [
      {
        key: 'userInfo',
        storage: localStorage
      }
    ]
  }
}) 