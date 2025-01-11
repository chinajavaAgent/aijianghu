import request from '@/utils/request'
import type { Project, ProjectCase, ProjectBenefit } from '@/types/project'
import type { ApiResponse, PageResponse } from '@/types/common'

// 获取项目列表
export const getProjects = (page: number, size: number, tipId?: number) => {
  return request.get<ApiResponse<PageResponse<Project>>>('/projects', {
    params: {
      page,
      size,
      tipId
    }
  })
}

// 获取项目详情
export const getProjectById = (id: number) => {
  return request.get<ApiResponse<{ projects: Project[], tipId: number }>>(`/projects/${id}`)
}

// 创建项目
export const createProject = (data: Project) => {
  return request.post<ApiResponse<Project>>('/projects', data)
}

// 更新项目
export const updateProject = (id: number, data: Partial<Project>) => {
  return request.put<ApiResponse<Project>>(`/projects/${id}`, data)
}

// 删除项目
export const deleteProject = (id: number) => {
  return request.delete<ApiResponse<void>>(`/projects/${id}`)
}

// 添加案例
export const addCase = (projectId: number, data: ProjectCase) => {
  return request.post<ApiResponse<ProjectCase>>(`/projects/${projectId}/cases`, data)
}

// 更新案例
export const updateCase = (projectId: number, caseId: number, data: Partial<ProjectCase>) => {
  return request.put<ApiResponse<ProjectCase>>(`/projects/${projectId}/cases/${caseId}`, data)
}

// 删除案例
export const deleteCase = (projectId: number, caseId: number) => {
  return request.delete<ApiResponse<void>>(`/projects/${projectId}/cases/${caseId}`)
}

// 上传案例图片
export const uploadCaseImage = (projectId: number, caseId: number, file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post<ApiResponse<string>>(`/projects/${projectId}/cases/${caseId}/image`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 上传项目视频
export const uploadProjectVideo = (projectId: number, file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post<ApiResponse<string>>(`/projects/${projectId}/video`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 添加福利
export const addBenefit = (projectId: number, data: ProjectBenefit) => {
  return request.post<ApiResponse<ProjectBenefit>>(`/projects/${projectId}/benefits`, data)
}

// 更新福利
export const updateBenefit = (projectId: number, benefitId: number, data: Partial<ProjectBenefit>) => {
  return request.put<ApiResponse<ProjectBenefit>>(`/projects/${projectId}/benefits/${benefitId}`, data)
}

// 删除福利
export const deleteBenefit = (projectId: number, benefitId: number) => {
  return request.delete<ApiResponse<void>>(`/projects/${projectId}/benefits/${benefitId}`)
} 