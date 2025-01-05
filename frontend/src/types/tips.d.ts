// AI锦囊类型定义
export interface AiTips {
    id: number;
    title: string;
    description: string;
    content: string;
    category: string;
    requiredLevel: number;
    views: number;
    likes: number;
    createTime: string;
    updateTime: string;
    status: number; // 0:草稿 1:已发布 2:已下架
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