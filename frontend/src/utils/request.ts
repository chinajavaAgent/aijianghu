import axios, { AxiosInstance, AxiosResponse } from 'axios'
import toast from './toast'
import router from '@/router'
import { useUserStore } from '@/store/user'
import type { Store } from 'pinia'

type UserStore = ReturnType<typeof useUserStore>

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

// 创建axios实例
const request = axios.create({
  timeout: 15000 // 请求超时时间
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从localStorage中获取token
    const token = localStorage.getItem('token')
    if (token) {
      // 将token添加到请求头中
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    // 判断返回的code是否为200
    if (res.code !== 200) {
      toast.error(res.message || '操作失败')
      return Promise.reject(new Error(res.message || '操作失败'))
    }
    // 返回数据部分
    return {
      ...response,
      data: res
    }
  },
  error => {
    // 如果是401错误，说明token过期或无效，需要重新登录
    if (error.response?.status === 401) {
      const userStore: any = useUserStore()
      userStore.clearUser()
      router.push({
        path: '/login',
        query: { redirect: router.currentRoute.value.fullPath }
      })
      toast.error('登录已过期，请重新登录')
    } else if (error.response?.status === 403) {
      const userStore: any = useUserStore()
      userStore.clearUser()
      router.push({
        path: '/login',
        query: { redirect: router.currentRoute.value.fullPath }
      })
      toast.error('权限不足，请重新登录')
    } else {
      console.error('响应错误:', error)
      const message = error.response?.data?.message || error.message || '请求失败'
      toast.error(message)
    }
    return Promise.reject(error)
  }
)

// 扩展request类型
interface ExtendedRequest {
  get<T>(url: string, config?: any): Promise<ApiResponse<T>>
  post<T>(url: string, data?: any, config?: any): Promise<ApiResponse<T>>
  put<T>(url: string, data?: any, config?: any): Promise<ApiResponse<T>>
  delete<T>(url: string, config?: any): Promise<ApiResponse<T>>
}

// 创建一个包装函数来处理响应
const wrapRequest = (axiosInstance: AxiosInstance): ExtendedRequest => {
  return {
    get: async <T>(url: string, config?: any) => {
      const response = await axiosInstance.get(url, config)
      return response.data as ApiResponse<T>
    },
    post: async <T>(url: string, data?: any, config?: any) => {
      const response = await axiosInstance.post(url, data, config)
      return response.data as ApiResponse<T>
    },
    put: async <T>(url: string, data?: any, config?: any) => {
      const response = await axiosInstance.put(url, data, config)
      return response.data as ApiResponse<T>
    },
    delete: async <T>(url: string, config?: any) => {
      const response = await axiosInstance.delete(url, config)
      return response.data as ApiResponse<T>
    }
  }
}

export default wrapRequest(request) 