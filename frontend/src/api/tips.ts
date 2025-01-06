import request from '@/utils/request'
import type { AiTips, AiTipsPage } from '@/types/tips'
import type { SharePosterData } from '@/types/poster'

/**
 * 获取AI锦囊列表
 */
export function getAiTipsList(params: {
    page: number;
    size: number;
    category?: string;
}) {
    return request<AiTipsPage>({
        url: '/api/tips/list',
        method: 'get',
        params
    })
}

/**
 * 获取AI锦囊详情
 */
export function getAiTipsDetail(id: number) {
    return request<AiTips>({
        url: `/api/tips/${id}`,
        method: 'get'
    })
}

/**
 * 创建AI锦囊
 */
export function createAiTips(data: Partial<AiTips>) {
    return request<boolean>({
        url: '/api/tips',
        method: 'post',
        data
    })
}

/**
 * 更新AI锦囊
 */
export function updateAiTips(id: number, data: Partial<AiTips>) {
    return request<boolean>({
        url: `/api/tips/${id}`,
        method: 'put',
        data
    })
}

/**
 * 删除AI锦囊
 */
export function deleteAiTips(id: number) {
    return request<boolean>({
        url: `/api/tips/${id}`,
        method: 'delete'
    })
}

/**
 * 点赞AI锦囊
 */
export function likeAiTips(id: number) {
    return request<boolean>({
        url: `/api/tips/${id}/like`,
        method: 'post'
    })
}

/**
 * 取消点赞AI锦囊
 */
export function unlikeAiTips(id: number) {
    return request<boolean>({
        url: `/api/tips/${id}/like`,
        method: 'delete'
    })
}

/**
 * 获取分享海报数据
 */
export function getSharePoster(tipId: number) {
    return request<SharePosterData>({
        url: `/api/tips/${tipId}/poster`,
        method: 'get'
    })
} 