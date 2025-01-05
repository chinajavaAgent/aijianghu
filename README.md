# AI群江湖应用

## 项目简介
AI群江湖是一个资源分享平台，用户可以根据不同的等级获取不同的资源。

## 技术栈
### 前端
- Vue 3
- Tailwind CSS
- Vite (构建工具)
- Axios (HTTP客户端)
- Vue Router
- Pinia (状态管理)

### 后端
- Spring Boot
- MySQL
- MyBatis-Plus
- Spring Security (认证授权)
- Swagger (API文档)

## 功能模块
1. 用户系统
   - 用户注册
   - 用户登录
   - 用户等级
   - 个人信息管理

2. 资源系统
   - 资源分类
   - 资源列表
   - 资源详情
   - 资源权限控制

3. 等级系统
   - 等级规则
   - 等级特权
   - 升级条件

## 数据库设计
### 用户表(user)
- id: 主键
- username: 用户名
- password: 密码
- nickname: 昵称
- level: 等级
- points: 积分
- avatar: 头像
- create_time: 创建时间
- update_time: 更新时间

### 资源表(resource)
- id: 主键
- title: 标题
- description: 描述
- content: 内容
- category_id: 分类ID
- required_level: 所需等级
- downloads: 下载次数
- create_time: 创建时间
- update_time: 更新时间

### 资源分类表(category)
- id: 主键
- name: 分类名称
- description: 描述
- create_time: 创建时间
- update_time: 更新时间

### 用户资源关联表(user_resource)
- id: 主键
- user_id: 用户ID
- resource_id: 资源ID
- download_time: 下载时间

### AI锦囊表(ai_tips)
- id: 主键
- title: 标题
- description: 描述
- content: 内容
- category: 分类
- required_level: 所需等级
- views: 查看次数
- likes: 点赞数
- create_time: 创建时间
- update_time: 更新时间
- status: 状态(0:草稿 1:已发布 2:已下架)

### 用户锦囊收藏表(user_tips_collection)
- id: 主键
- user_id: 用户ID
- tip_id: 锦囊ID
- create_time: 收藏时间

### 用户锦囊点赞表(user_tips_like)
- id: 主键
- user_id: 用户ID
- tip_id: 锦囊ID
- create_time: 点赞时间

## 项目结构
```
ai-group-world/
├── frontend/                # 前端项目
│   ├── src/
│   │   ├── assets/         # 静态资源
│   │   ├── components/     # 组件
│   │   ├── views/          # 页面
│   │   ├── router/         # 路由
│   │   ├── store/          # 状态管理
│   │   └── api/            # API接口
│   └── package.json
│
└── backend/                 # 后端项目
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   ├── controller/
    │   │   │   ├── service/
    │   │   │   ├── model/
    │   │   │   └── config/
    │   │   └── resources/
    │   └── test/
    └── pom.xml
```

## 开发计划
1. 搭建基础框架
   - 初始化前端项目
   - 初始化后端项目
   - 配置数据库连接

2. 实现基础功能
   - 用户认证系统
   - 资源管理系统
   - 等级系统

3. 优化和测试
   - 性能优化
   - 单元测试
   - 接口测试

## 部署说明
待补充... 