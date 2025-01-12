import request from '@/utils/request'
import type { Order } from '@/types/order'

export interface OrderResponse {
  records: Order[]
  total: number
  size: number
  current: number
  data?: {
    records: Order[]
    total: number
    size: number
    current: number
  }
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

export interface OrderCreateRequest {
  tipsId: number
  userId: number
  title: string
  price: number
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
}

// 创建订单
export const createOrder = async (data: OrderCreateRequest & { adminId: number }) => {
  const response = await request.post<Order>('/orders', data)
  return response.data
}

// 获取用户订单列表
export const getUserOrders = async (userId: number, params: { page: number; size: number; status?: number; isBig?: boolean }) => {
  try {
    const response = await request.get<OrderResponse>(`/orders/user/${userId}`, { params })
    return response.data || { records: [], total: 0, size: params.size, current: params.page }
  } catch (error) {
    console.error('获取订单列表失败:', error)
    return { records: [], total: 0, size: params.size, current: params.page }
  }
}

// 获取用户等级信息
export const getUserLevel = async (userId: number) => {
  const { data } = await request.get<UserResponse>(`/users/${userId}`)
  const { levelTitle, credit, nextLevelCredit } = data
  return { levelTitle, credit, nextLevelCredit }
}

export interface ApproveOrderRequest {
  orderId: number
  status: number
  reviewerId: number
  reviewerName: string
  reviewerPhone: string
  reviewerWechat: string
  credit: number
}

// 审核订单
export const approveOrder = async (data: ApproveOrderRequest) => {
  const response = await request.post<Order>(`/orders/${data.orderId}/approve`, {
    status: data.orderId === 0 ? 2 : 1, // 如果orderId为0则拒绝，否则通过
    reviewerId: data.reviewerId,
    reviewerName: data.reviewerName,
    reviewerPhone: data.reviewerPhone,
    reviewerWechat: data.reviewerWechat,
    credit: data.credit
  })
  return response.data
}

// 根据锦囊ID获取订单列表
export const getOrdersByTipId = async (tipId: number, params?: { page?: number; size?: number; status?: number }) => {
  try {
    const response = await request.get<OrderResponse>(`/orders/tips/${tipId}`, {
      params: {
        page: params?.page || 1,
        size: params?.size || 100,
        status: params?.status
      }
    })
    return response.data
  } catch (error) {
    console.error('获取锦囊订单失败:', error)
    return { records: [], total: 0, size: params?.size || 100, current: params?.page || 1 }
  }
}

// 根据手机号查询订单列表
export const getOrdersByPhone = async (phone: string, params?: { page?: number; size?: number; status?: number }) => {
  try {
    const response = await request.get<OrderResponse>(`/orders/phone/${phone}`, {
      params: {
        page: params?.page || 1,
        size: params?.size || 10,
        status: params?.status
      }
    })
    return response.data
  } catch (error) {
    console.error('根据手机号查询订单失败:', error)
    return { records: [], total: 0, size: params?.size || 10, current: params?.page || 1 }
  }
} 