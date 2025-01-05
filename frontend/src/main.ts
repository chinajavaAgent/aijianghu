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
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

// 注册Vant组件
app.use(PullRefresh)
app.use(Toast)
app.use(Tab)
app.use(Tabs)
app.use(Dialog)

app.use(pinia)
app.use(router)
app.mount('#app')
