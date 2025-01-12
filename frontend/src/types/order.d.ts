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
  checkPhone?: string
  // 关联字段
  userName?: string
  gender?: number
  age?: number
  city?: string
  userPhone?: string
  userWechat?: string
  profession?: string
  experience?: number
  reason?: string
  introduction?: string
  reviewer?: string
  reviewerPhone?: string
  reviewerWechat?: string
}

// API响应类型
export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T & {
    records?: T[];
  };
} 