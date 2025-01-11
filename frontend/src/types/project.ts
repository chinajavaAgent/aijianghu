// 项目案例
export interface ProjectCase {
  id?: number;
  projectId?: number;
  description: string;
  imageUrl: string;
  createTime?: string;
  updateTime?: string;
}

// 项目福利
export interface ProjectBenefit {
  id?: number;
  projectId?: number;
  content: string;
  createTime?: string;
  updateTime?: string;
}

// 项目信息
export interface Project {
  id: number;
  tipId: number;
  name: string;
  description: string;
  icon: string;
  detail: string;
  videoUrl?: string;
  status: number;
  views: number;
  likes: number;
  cases: ProjectCase[];
  benefits: ProjectBenefit[];
  createdAt: string;
  updatedAt: string;
}

export interface ProjectUpdateDto {
  name: string;
  description: string;
  videoUrl?: string;
  status?: number;
  cases: ProjectCase[];
  benefits: ProjectBenefit[];
}

export interface Order {
  id: number;
  tipsId: number;
  title: string;
  reviewer: string;
  reviewerPhone: string;
  reviewerWechat: string;
  credit: number;
  price: number;
  applyTime: string;
  approveTime?: string;
  status: number;
  phone?: string;
  wechat?: string;
  records?: Order[];
  introduction: string;
}

// 项目表单数据
export interface ProjectFormData {
  projects: Project[];
} 