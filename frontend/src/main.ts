import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import './style.css'

// 导入Vant组件和样式
import { 
  PullRefresh,
  Toast,
  Tab,
  Tabs,
  Dialog
} from 'vant'
import 'vant/lib/index.css'

const app = createApp(App)

// 初始化 Pinia
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

// 导入用户状态管理
import { useUserStore } from '@/store/user'

// 初始化用户状态
const userStore = useUserStore()
userStore.initUserState()

// 注册Vant组件
app.use(PullRefresh)
app.use(Toast)
app.use(Tab)
app.use(Tabs)
app.use(Dialog)

// 注册路由
app.use(router)

// 挂载应用
app.mount('#app')
