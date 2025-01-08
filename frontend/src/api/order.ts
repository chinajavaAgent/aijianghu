import request from '@/utils/request'
import type { Order } from '@/types/order'

/**
 * 创建订单
 */
export function createOrder(data: {
  userId: number
  tipsId: number
  adminId: number
  title: string
  price: string
}) {
  return request.post<Order>('/api/orders', data)
}

/**
 * 获取用户订单列表
 */
export function getUserOrders(userId: number, params: {
  page?: number
  size?: number
  status?: number
}) {
  return request.get<Order>(`/api/orders/user/${userId}`, { params })
}

/**
 * 审核订单
 */
export function approveOrder(orderId: number, status: number) {
  return request.put<boolean>(`/api/orders/${orderId}/approve`, null, { params: { status } })
} 