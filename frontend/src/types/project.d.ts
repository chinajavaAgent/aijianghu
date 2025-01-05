export interface Project {
  id?: number;
  tipId?: number;
  name: string;  // 项目名称
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

export interface ProjectCase {
  id?: number;
  projectId?: number;
  description: string;
  imageUrl: string;
  createTime?: string;
  updateTime?: string;
}

export interface ProjectBenefit {
  id?: number;
  projectId?: number;
  content: string;
  createTime?: string;
  updateTime?: string;
}

export interface ProjectUpdateDto {
  name?: string;  // 项目名称
  description?: string;
  videoUrl?: string;
  status?: number;
  coverImage?: string;
}

export interface ProjectCreateDto {
  name: string;  // 项目名称
  description: string;
  videoUrl?: string;
  status: number;
  views: number;
  likes: number;
  cases: ProjectCase[];
  benefits: ProjectBenefit[];
} 