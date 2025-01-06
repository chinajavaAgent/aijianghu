import type { Project } from './project'

export interface AiTips {
  id: number
  title: string
  description: string
  price: number
  views: number
  likes: number
  category: string
  content: string
  requiredLevel: number
  projects: Project[]
  createdAt: string
  updatedAt: string
}

export interface AiTipsListParams {
  page: number
  size: number
  category?: string
  keyword?: string
}

export interface AiTipsListResponse {
  total: number
  list: AiTips[]
} 