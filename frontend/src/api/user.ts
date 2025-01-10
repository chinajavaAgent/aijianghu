import request from '@/utils/request'
import type { User, LoginRequest, LoginResponse, RegisterRequest } from '@/types/user'

/**
 * 用户登录
 */
export function login(data: LoginRequest) {
    return request.post<LoginResponse>('/users/login', data)
}

/**
 * 用户注册
 */
export function register(data: RegisterRequest) {
    return request.post<User>('/users/register', data)
}

/**
 * 检查手机号是否存在
 */
export function checkPhone(phone: string) {
    return request.get<boolean>('/users/check-phone', {
        params: { phone }
    })
}

export interface UserInfoResponse {
  id: number
  phone: string
  wechat: string
  platform: string
  realName: string
  gender: number
  birthDate: string
  city: string
  profession: string
  experience: number
  reason: string
  introduction: string
  referrerId: number
  level: number
  levelTitle: string
  points: number
  status: number
  createTime: string
  updateTime: string
}

export const getUserInfo = (userId: number) => {
  return request.get<UserInfoResponse>(`/users/${userId}`)
} 