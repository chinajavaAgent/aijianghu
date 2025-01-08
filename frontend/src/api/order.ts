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
  return request({
    url: '/api/orders',
    method: 'post',
    params: data
  })
}

/**
 * 获取用户订单列表
 */
export function getUserOrders(userId: number, params: {
  page?: number
  size?: number
  status?: number
}) {
  return request({
    url: `/api/orders/user/${userId}`,
    method: 'get',
    params
  })
}

/**
 * 审核订单
 */
export function approveOrder(orderId: number, status: number) {
  return request({
    url: `/api/orders/${orderId}/approve`,
    method: 'put',
    params: { status }
  })
} 