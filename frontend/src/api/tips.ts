import request from '@/utils/request'
import type { AiTips, AiTipsPage } from '@/types/tips'
import type { SharePosterData } from '@/types/poster'
import type { ApiResponse } from '@/utils/request'

/**
 * 获取AI锦囊列表
 */
export function getAiTipsList(params: {
    page: number;
    size: number;
    category?: string;
}) {
    return request.get<AiTipsPage>('/api/tips/list', { params })
}

/**
 * 获取AI锦囊详情
 */
export function getAiTipsDetail(id: number) {
    return request.get<AiTips>(`/api/tips/${id}`)
}

/**
 * 创建AI锦囊
 */
export function createAiTips(data: Partial<AiTips>) {
    return request.post<boolean>('/api/tips', data)
}

/**
 * 更新AI锦囊
 */
export function updateAiTips(id: number, data: Partial<AiTips>) {
    return request.put<boolean>(`/api/tips/${id}`, data)
}

/**
 * 删除AI锦囊
 */
export function deleteAiTips(id: number) {
    return request.delete<boolean>(`/api/tips/${id}`)
}

/**
 * 点赞AI锦囊
 */
export function likeAiTips(id: number) {
    return request.post<boolean>(`/api/tips/${id}/like`)
}

/**
 * 取消点赞AI锦囊
 */
export function unlikeAiTips(id: number) {
    return request.delete<boolean>(`/api/tips/${id}/like`)
}

/**
 * 获取分享海报数据
 */
export function getSharePoster(tipId: number) {
    return request.get<SharePosterData>(`/api/tips/${tipId}/poster`)
} 