import axios from 'axios'
import toast from './toast'

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
    response => {
        const res = response.data
        // 判断返回的code是否为200
        if (res.code !== 200) {
            toast.error(res.message || '操作失败')
            return Promise.reject(new Error(res.message || '操作失败'))
        }
        // 返回数据部分
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
            const message = error.response?.data?.message || error.message || '请求失败'
            toast.error(message)
        }
        return Promise.reject(error)
    }
)

export default request 