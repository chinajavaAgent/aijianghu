// 项目案例
export interface ProjectCase {
  id?: number
  description: string
  imageUrl: string
  createTime?: string
  updateTime?: string
}

// 项目福利
export interface ProjectBenefit {
  id?: number
  content: string
  createTime?: string
  updateTime?: string
}

// 项目信息
export interface Project {
  id?: number
  tipId: number
  name: string
  description: string
  videoUrl: string
  cases: ProjectCase[]
  benefits: ProjectBenefit[]
  createTime?: string
  updateTime?: string
  isExpanded?: boolean
  currentStep?: number
  views?: number
  likes?: number
}

// 项目表单数据
export interface ProjectFormData {
  projects: Project[]
} 