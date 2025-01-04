// 注册请求参数
export interface RegisterRequest {
    phone: string
    password: string
    confirmPassword: string
    wechat: string
    realName: string
    referrerId?: number
}

// 用户信息
export interface User {
    id: number
    phone: string
    wechat: string
    realName: string
    level: number
    points: number
    status: number
    createTime: string
    updateTime: string
} 