import type { Project } from './project'

// AI锦囊类型定义
export interface AiTips {
    id?: number;
    title: string;
    description: string;
    content?: string;
    price: number;
    category?: string;
    tags?: string;
    authorId?: number;
    requiredLevel: number;
    views?: number;
    likes?: number;
    status?: number; // 0:草稿 1:已发布
    createTime?: string;
    updateTime?: string;
    projects?: Project[]; // 关联的项目列表
    referrerUser?: {
        phone: string;
        wechat: string;
    };
}

// AI锦囊分页结果
export interface AiTipsPage {
    records: AiTips[];
    total: number;
    size: number;
    current: number;
    pages: number;
}

// AI锦囊分类
export type AiTipsCategory = 'AI工具' | 'AI应用' | 'AI变现' | '流量获取' | '其他';

// API响应类型
export interface ApiResponse<T> {
    code: number;
    message: string;
    data: T;
    records?: T[];
    url?: string;
}

// 管理员信息
export interface Admin {
    id: number;
    phone: string;
    wechatId: string;
    referrerUser?: {
        phone: string;
        wechat: string;
    };
    url?: string;
} 