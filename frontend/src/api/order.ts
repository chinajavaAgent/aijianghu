import request from '@/utils/request'

export interface OrderResponse {
  records: Array<{
    id: number
    tipsId: number
    title: string
    reviewer: string
    reviewerPhone: string
    reviewerWechat: string
    credit: number
    price: number
    applyTime: string
    approveTime?: string
    status: number
    userName: string
    gender: number
    age: number
    city: string
    userPhone: string
    userWechat: string
    profession: string
    experience: number
    reason: string
    introduction: string
  }>
  total: number
  size: number
  current: number
}

export interface UserResponse {
  id: number
  name: string
  phone: string
  wechat: string
  avatar: string
  gender: number
  age: number
  city: string
  profession: string
  experience: number
  introduction: string
  credit: number
  levelTitle: string
  nextLevelCredit: number
}

// 获取用户订单列表
export const getUserOrders = async (userId: number, params: { page: number; size: number; status: number }) => {
  const { data } = await request.get<OrderResponse>(`/orders/user/${userId}`, { params })
  return data
}

// 获取用户等级信息
export const getUserLevel = async (userId: number) => {
  const { data } = await request.get<UserResponse>(`/users/${userId}`)
  const { levelTitle, credit, nextLevelCredit } = data
  return { levelTitle, credit, nextLevelCredit }
} 