// AI锦囊类型定义
export interface AiTips {
    id: number;
    title: string;
    description: string;
    content: string;
    price: number;
    category: string;
    tags: string;
    authorId: number;
    requiredLevel: number;
    views: number;
    likes: number;
    status: number; // 0:草稿 1:已发布
    createTime: string;
    updateTime: string;
    deleted: number; // 0:未删除 1:已删除
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