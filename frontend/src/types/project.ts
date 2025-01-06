// 项目案例
export interface ProjectCase {
  id: number
  imageUrl: string
  description: string
}

// 项目福利
export interface ProjectBenefit {
  id: number
  content: string
}

// 项目信息
export interface Project {
  id: number
  tipId: number
  name: string
  description: string
  videoUrl?: string
  cases: ProjectCase[]
  benefits: ProjectBenefit[]
  createdAt: string
  updatedAt: string
}

// 项目表单数据
export interface ProjectFormData {
  projects: Project[]
} 