import { defineStore } from 'pinia'

interface UserInfo {
  nickname?: string;
  phone?: string;
  [key: string]: any;
}

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    userInfo: JSON.parse(localStorage.getItem('userInfo') || 'null'),
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.token,
  },
  
  actions: {
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
    
    setUserInfo(userInfo: UserInfo) {
      this.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    
    clearUser() {
      this.token = null
      this.userInfo = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    },

    // 初始化用户状态
    initUserState() {
      const token = localStorage.getItem('token')
      const userInfo = localStorage.getItem('userInfo')
      
      if (token) {
        this.token = token
      }
      
      if (userInfo) {
        try {
          this.userInfo = JSON.parse(userInfo)
        } catch (e) {
          this.clearUser()
        }
      }
    }
  },
}) 