import { defineStore } from 'pinia'
import type { User } from '../types'

interface UserState extends User {}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    id: 0,
    username: '',
    nickname: '',
    level: 1,
    points: 0,
    avatar: '',
    token: ''
  }),
  
  actions: {
    setUser(user: Partial<UserState>) {
      Object.assign(this, user)
    },
    
    clearUser() {
      this.id = 0
      this.username = ''
      this.nickname = ''
      this.level = 1
      this.points = 0
      this.avatar = ''
      this.token = ''
    }
  },
  
  persist: true
}) 