import axios from 'axios'
import toast from './toast'

// 创建axios实例
const request = axios.create({
    baseURL: 'http://localhost:8080', // 移除重复的/api
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
        // 如果是401错误，说明token过期或无效，需要重新登录
        if (error.response?.status === 401) {
            localStorage.removeItem('token')
            window.location.href = '/login'
            toast.error('登录已过期，请重新登录')
        } else if (error.response?.status === 403) {
            toast.error('没有权限访问')
        } else {
            console.error('响应错误:', error)
            toast.error(error.response?.data?.message || '请求失败')
        }
        return Promise.reject(error)
    }
)

export default request 