// 项目类型定义
export interface Project {
  id?: number;
  tipId?: number;
  name: string;
  description: string;
  coverImage?: string;
  videoUrl?: string;
  status: number;
  startTime?: string;
  endTime?: string;
  authorId?: number;
  views: number;
  likes: number;
  createTime?: string;
  updateTime?: string;
  cases: ProjectCase[];
  benefits: ProjectBenefit[];
  // 前端专用字段
  isExpanded?: boolean;
  currentStep?: number;
}

// 项目案例类型定义
export interface ProjectCase {
  id?: number;
  projectId?: number;
  description: string;
  imageUrl: string;
  createTime?: string;
  updateTime?: string;
}

// 项目福利类型定义
export interface ProjectBenefit {
  id?: number;
  projectId?: number;
  content: string;
  createTime?: string;
  updateTime?: string;
}

// 用于更新项目时的部分字段
export interface ProjectUpdateDto {
  name?: string;
  description?: string;
  videoUrl?: string;
  status?: number;
  coverImage?: string;
} 