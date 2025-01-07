import axios from 'axios'
import type { Admin, AdminFormData } from '@/types/admin'

// 获取管理员列表
export const getAdminList = async () => {
  const response = await axios.get<Admin[]>('/api/admin/list')
  return response.data
}

// 添加管理员
export const addAdmin = async (data: AdminFormData) => {
  const formData = new FormData()
  formData.append('phone', data.phone)
  formData.append('wechatId', data.wechatId)
  formData.append('realName', data.realName)
  if (data.wechatQrCode) {
    formData.append('wechatQrCode', data.wechatQrCode)
  }
  
  const response = await axios.post<Admin>('/api/admin', formData)
  return response.data
}

// 更新管理员信息
export const updateAdmin = async (id: number, data: Partial<AdminFormData>) => {
  const formData = new FormData()
  if (data.phone) formData.append('phone', data.phone)
  if (data.wechatId) formData.append('wechatId', data.wechatId)
  if (data.realName) formData.append('realName', data.realName)
  if (data.wechatQrCode) formData.append('wechatQrCode', data.wechatQrCode)
  
  const response = await axios.put<Admin>(`/api/admin/${id}`, formData)
  return response.data
}

// 删除管理员
export const deleteAdmin = async (id: number) => {
  await axios.delete(`/api/admin/${id}`)
} 