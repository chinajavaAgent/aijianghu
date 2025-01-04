import axios from 'axios'
import toast from './toast'

// 创建axios实例
const request = axios.create({
    baseURL: 'http://localhost:8080/api', // API的base_url
    timeout: 15000 // 请求超时时间
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 可以在这里添加token等认证信息
        return config
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        const res = response.data
        // 这里可以根据后端的返回格式进行统一处理
        if (res.code !== 200) {
            // 如果返回的code不是200，说明有错误
            toast.error(res.message || '操作失败')
            return Promise.reject(new Error(res.message || '操作失败'))
        }
        return res
    },
    error => {
        console.error('响应错误:', error)
        toast.error(error.response?.data?.message || '请求失败')
        return Promise.reject(error)
    }
)

export default request 