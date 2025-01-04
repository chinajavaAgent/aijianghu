import request from './request'
import type { Category, Resource, User, LoginParams, RegisterParams } from '../types'

export const sendVerificationCode = (phone: string) => {
  return request.post<void>('/auth/send-code', { phone })
}

export const login = (data: LoginParams) => {
  return request.post<User>('/auth/login', data)
}

export const register = (data: RegisterParams) => {
  return request.post<void>('/auth/register', data)
}

export const getCategories = () => {
  return request.get<Category[]>('/categories')
}

export const getResources = (params: { categoryId?: string | number }) => {
  return request.get<Resource[]>('/resources', { params })
}

export const downloadResource = (id: number) => {
  return request.get<string>(`/resources/${id}/download`)
} 