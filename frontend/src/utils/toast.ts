import { createVNode, render } from 'vue'
import Toast from '../components/Toast.vue'

const div = document.createElement('div')
document.body.appendChild(div)

// 创建Toast实例
const vnode = createVNode(Toast)
render(vnode, div)

// 导出Toast方法
export default {
    success(message: string) {
        // @ts-ignore
        vnode.component?.exposed?.show(message, 'success')
    },
    error(message: string) {
        // @ts-ignore
        vnode.component?.exposed?.show(message, 'error')
    }
} 