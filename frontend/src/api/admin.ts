import request from '@/utils/request'
import type { Admin, AdminFormData } from '@/types/admin'
import type { ApiResponse } from '@/utils/request'

// 获取管理员列表
export const getAdminList = () => {
  return request.get<Admin[]>('/api/admin/list')
}

// 添加管理员
export const addAdmin = (data: AdminFormData) => {
  const formData = new FormData()
  formData.append('phone', data.phone)
  formData.append('wechatId', data.wechatId)
  formData.append('realName', data.realName)
  if (data.wechatQrCode) {
    formData.append('wechatQrCode', data.wechatQrCode)
  }
  data.tipsIds.forEach((id: number) => formData.append('tipsIds', id.toString()))
  return request.post<Admin>('/api/admin', formData)
}

// 更新管理员
export const updateAdmin = (id: number, data: AdminFormData) => {
  const formData = new FormData()
  formData.append('phone', data.phone)
  formData.append('wechatId', data.wechatId)
  formData.append('realName', data.realName)
  if (data.wechatQrCode) {
    formData.append('wechatQrCode', data.wechatQrCode)
  }
  data.tipsIds.forEach((id: number) => formData.append('tipsIds', id.toString()))
  return request.put<Admin>(`/api/admin/${id}`, formData)
}

// 删除管理员
export const deleteAdmin = (id: number) => {
  return request.delete<void>(`/api/admin/${id}`)
}

// 获取管理员关联的锦囊列表
export const getAdminTips = (adminId: number) => {
  return request.get<Admin>(`/api/admin/${adminId}/tips`)
}

// 更新管理员关联的锦囊
export const updateAdminTips = (adminId: number, tipsIds: number[]) => {
  return request.put<void>(`/api/admin/${adminId}/tips`, { tipsIds })
} 