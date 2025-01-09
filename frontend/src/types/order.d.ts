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
  reviewerPhone?: string
  reviewerWechat?: string
  phone?: string
  wechat?: string
  credit?: number
  introduction?: string
}

// API响应类型
export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T & {
    records?: T[];
  };
} 