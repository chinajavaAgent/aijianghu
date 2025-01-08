export interface Admin {
  id: number;
  phone: string;
  wechatId: string;
  realName: string;
  wechatQrCode?: string;
  createdAt?: string;
  updatedAt?: string;
  tips?: any[];
}

export interface AdminFormData {
  phone: string;
  wechatId: string;
  realName: string;
  wechatQrCode?: File | null;
  tipsIds: number[];
} 