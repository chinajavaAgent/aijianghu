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

/**
 * 获取用户信息
 */
export function getUserInfo(id: number) {
    return request.get<User>(`/users/${id}`)
} 