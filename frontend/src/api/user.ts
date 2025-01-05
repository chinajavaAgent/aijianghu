import request from '@/utils/request'
import type { User, LoginRequest, LoginResponse } from '@/types/user'

/**
 * 用户登录
 */
export function login(data: LoginRequest) {
    return request<LoginResponse>({
        url: '/api/users/login',
        method: 'post',
        data
    })
}

/**
 * 用户注册
 */
export function register(data: any) {
    return request<User>({
        url: '/api/users/register',
        method: 'post',
        data
    })
}

/**
 * 检查手机号是否存在
 */
export function checkPhone(phone: string) {
    return request<boolean>({
        url: '/api/users/check-phone',
        method: 'get',
        params: { phone }
    })
} 