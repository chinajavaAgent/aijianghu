import request from '@/utils/request'
import type { Project, ProjectCase, ProjectBenefit } from '@/types/project'

// 获取项目列表
export const getProjects = (page: number, size: number) => {
  return request({
    url: '/api/projects',
    method: 'get',
    params: {
      page,
      size
    }
  })
}

// 创建项目
export const createProject = (data: Project) => {
  return request({
    url: '/api/projects',
    method: 'post',
    data
  })
}

// 更新项目
export const updateProject = (id: number, data: Partial<Project>) => {
  return request({
    url: `/api/projects/${id}`,
    method: 'put',
    data
  })
}

// 删除项目
export const deleteProject = (id: number) => {
  return request({
    url: `/api/projects/${id}`,
    method: 'delete'
  })
}

// 添加案例
export const addCase = (projectId: number, data: ProjectCase) => {
  return request({
    url: `/api/projects/${projectId}/cases`,
    method: 'post',
    data
  })
}

// 更新案例
export const updateCase = (projectId: number, caseId: number, data: Partial<ProjectCase>) => {
  return request({
    url: `/api/projects/${projectId}/cases/${caseId}`,
    method: 'put',
    data
  })
}

// 删除案例
export const deleteCase = (projectId: number, caseId: number) => {
  return request({
    url: `/api/projects/${projectId}/cases/${caseId}`,
    method: 'delete'
  })
}

// 上传案例图片
export const uploadCaseImage = (projectId: number, caseId: number, file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: `/api/projects/${projectId}/cases/${caseId}/image`,
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 上传项目视频
export const uploadProjectVideo = (projectId: number, file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: `/api/projects/${projectId}/video`,
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 添加福利
export const addBenefit = (projectId: number, data: ProjectBenefit) => {
  return request({
    url: `/api/projects/${projectId}/benefits`,
    method: 'post',
    data
  })
}

// 更新福利
export const updateBenefit = (projectId: number, benefitId: number, data: Partial<ProjectBenefit>) => {
  return request({
    url: `/api/projects/${projectId}/benefits/${benefitId}`,
    method: 'put',
    data
  })
}

// 删除福利
export const deleteBenefit = (projectId: number, benefitId: number) => {
  return request({
    url: `/api/projects/${projectId}/benefits/${benefitId}`,
    method: 'delete'
  })
} 