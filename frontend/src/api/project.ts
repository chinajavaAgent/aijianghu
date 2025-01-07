import request from '@/utils/request'
import type { Project, ProjectCase, ProjectBenefit } from '@/types/project'
import type { ApiResponse } from '@/utils/request'

// 获取项目列表
export const getProjects = (page: number, size: number, tipId?: number) => {
  return request.get<ApiResponse<Project[]>>('/api/projects', {
    params: {
      page,
      size,
      tipId
    }
  })
}

// 获取项目详情
export const getProjectById = (id: number) => {
  return request.get<ApiResponse<{ projects: Project[], tipId: number }>>(`/api/projects/${id}`)
}

// 创建项目
export const createProject = (data: Project) => {
  return request.post<ApiResponse<Project>>('/api/projects', data)
}

// 更新项目
export const updateProject = (id: number, data: Partial<Project>) => {
  return request.put<ApiResponse<Project>>(`/api/projects/${id}`, data)
}

// 删除项目
export const deleteProject = (id: number) => {
  return request.delete<ApiResponse<void>>(`/api/projects/${id}`)
}

// 添加案例
export const addCase = (projectId: number, data: ProjectCase) => {
  return request.post<ApiResponse<ProjectCase>>(`/api/projects/${projectId}/cases`, data)
}

// 更新案例
export const updateCase = (projectId: number, caseId: number, data: Partial<ProjectCase>) => {
  return request.put<ApiResponse<ProjectCase>>(`/api/projects/${projectId}/cases/${caseId}`, data)
}

// 删除案例
export const deleteCase = (projectId: number, caseId: number) => {
  return request.delete<ApiResponse<void>>(`/api/projects/${projectId}/cases/${caseId}`)
}

// 上传案例图片
export const uploadCaseImage = (projectId: number, caseId: number, file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post<ApiResponse<string>>(`/api/projects/${projectId}/cases/${caseId}/image`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 上传项目视频
export const uploadProjectVideo = (projectId: number, file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post<ApiResponse<string>>(`/api/projects/${projectId}/video`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 添加福利
export const addBenefit = (projectId: number, data: ProjectBenefit) => {
  return request.post<ApiResponse<ProjectBenefit>>(`/api/projects/${projectId}/benefits`, data)
}

// 更新福利
export const updateBenefit = (projectId: number, benefitId: number, data: Partial<ProjectBenefit>) => {
  return request.put<ApiResponse<ProjectBenefit>>(`/api/projects/${projectId}/benefits/${benefitId}`, data)
}

// 删除福利
export const deleteBenefit = (projectId: number, benefitId: number) => {
  return request.delete<ApiResponse<void>>(`/api/projects/${projectId}/benefits/${benefitId}`)
} 