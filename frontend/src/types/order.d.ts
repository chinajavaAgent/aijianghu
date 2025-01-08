export interface Order {
  id: number
  orderNo: string
  userId: number
  tipsId: number
  adminId: number
  title: string
  price: string
  status: number
  applyTime: string
  approveTime?: string
  createdAt: string
  updatedAt: string
  // 关联字段
  reviewer?: string
  phone?: string
  wechat?: string
  credit?: number
} 