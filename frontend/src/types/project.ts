// 项目信息
export interface Project {
  id?: number;
  tipId: number;
  name: string;
  description: string;
  icon?: string;
  detail: string;
  createdAt?: string;
  updatedAt?: string;
}

// 项目更新数据
export interface ProjectUpdateDto {
  name: string;
  description: string;
  icon?: string;
  detail: string;
} 