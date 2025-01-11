// 分页响应数据结构
export interface PageResponse<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}

// API 通用响应结构
export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
} 