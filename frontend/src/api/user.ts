import request from '../utils/request'
import type { RegisterRequest, User } from '../types/user'

// 用户注册
export function register(data: RegisterRequest) {
    return request<any, { code: number; message: string; data: User }>({
        url: '/users/register',
        method: 'post',
        data
    })
}

// 用户登录
export function login(data: { phone: string; password: string }) {
    return request<any, { code: number; message: string; data: User }>({
        url: '/users/login',
        method: 'post',
        data
    })
}

// 检查手机号是否存在
export function checkPhone(phone: string) {
    return request<any, { code: number; message: string; data: boolean }>({
        url: '/users/check-phone',
        method: 'get',
        params: { phone }
    })
} 