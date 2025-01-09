import request from '@/utils/request'
import { ApiResponse } from '@/utils/request'

/**
 * 加密用户ID
 * @param userId 用户ID
 * @returns 加密后的token
 */
export function encryptUserId(userId: number) {
  return request.get<string>(`/share/encrypt/${userId}`)
}

/**
 * 解密用户ID
 * @param token 加密后的token
 * @returns 解密后的用户ID
 */
export function decryptUserId(token: string) {
  return request.get<number>(`/share/decrypt/${token}`)
} 