// 注册请求参数
export interface RegisterRequest {
    phone: string
    password: string
    confirmPassword: string
    wechat: string
    realName: string
    referrerId?: number
    shareCode?: string
}

// 用户信息
export interface User {
    id: number;
    username: string;
    nickname: string;
    realName: string;
    phone: string;
    avatar: string;
    level: number;
    points: number;
    createTime: string;
    updateTime: string;
}

export interface LoginResponse {
    token: string;
    user: User;
}

export interface LoginRequest {
    phone: string;
    password: string;
} 