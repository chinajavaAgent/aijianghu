export interface Category {
  id: number
  name: string
  description: string
}

export interface Resource {
  id: number
  title: string
  description: string
  content: string
  categoryId: number
  requiredLevel: number
  downloads: number
}

export interface User {
  id: number
  phone: string
  wechat: string
  nickname: string
  level: number
  points: number
  avatar: string
  token: string
  referrer?: string
}

export interface LoginParams {
  phone: string
  code: string
}

export interface RegisterParams {
  phone: string
  wechat: string
  nickname: string
  referrer?: string
} 