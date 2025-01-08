export interface Order {
  id: number
  orderNo: string
  userId: number
  tipsId: number
  adminId: number
  title: string
  price: number
  status: number
  applyTime: string
  approveTime?: string
  createdAt: string
  updatedAt: string
} 