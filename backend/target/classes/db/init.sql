-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `password` varchar(100) NOT NULL COMMENT '密码',
    `wechat` varchar(50) NOT NULL COMMENT '微信号',
    `real_name` varchar(20) NOT NULL COMMENT '真实姓名',
    `referrer_id` bigint(20) DEFAULT NULL COMMENT '推荐人ID',
    `level` int(11) NOT NULL DEFAULT '1' COMMENT '用户等级',
    `points` int(11) NOT NULL DEFAULT '0' COMMENT '用户积分',
    `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-正常',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_wechat` (`wechat`),
    KEY `idx_referrer` (`referrer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建资源分类表
CREATE TABLE IF NOT EXISTS `category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(50) NOT NULL COMMENT '分类名称',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源分类表';

-- 创建资源表
CREATE TABLE IF NOT EXISTS `resource` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title` varchar(100) NOT NULL COMMENT '标题',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `content` text COMMENT '内容',
    `category_id` bigint(20) NOT NULL COMMENT '分类ID',
    `required_level` int(11) DEFAULT 1 COMMENT '所需等级',
    `downloads` int(11) DEFAULT 0 COMMENT '下载次数',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- 创建用户资源关联表
CREATE TABLE IF NOT EXISTS `user_resource` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `resource_id` bigint(20) NOT NULL COMMENT '资源ID',
    `download_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '下载时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_resource` (`user_id`, `resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户资源关联表';

-- 删除已存在的AI锦囊表
DROP TABLE IF EXISTS `ai_tips`;

-- 创建AI锦囊表
CREATE TABLE IF NOT EXISTS `ai_tips` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title` varchar(100) NOT NULL COMMENT '标题',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `content` text  COMMENT '内容',
    `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '价格',
    `category` varchar(50) DEFAULT NULL COMMENT '分类',
    `tags` varchar(255) DEFAULT NULL COMMENT '标签，多个标签用逗号分隔',
    `author_id` bigint(20) COMMENT '作者ID',
    `required_level` int(11) COMMENT '所需等级',
    `views` int(11) DEFAULT 0 COMMENT '浏览次数',
    `likes` int(11) DEFAULT 0 COMMENT '点赞次数',
    `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0-草稿，1-已发布',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_author` (`author_id`),
    KEY `idx_category` (`category`),
    FULLTEXT KEY `idx_content` (`title`, `content`, `tags`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI锦囊表';

-- 创建project表
CREATE TABLE IF NOT EXISTS `project` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` varchar(100) NOT NULL COMMENT '项目名称',
    `description` text COMMENT '项目描述',
    `cover_image` varchar(255) COMMENT '封面图片URL',
    `video_url` varchar(255) DEFAULT NULL COMMENT '视频URL',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-开发中 1-已完成',
    `start_time` datetime COMMENT '开始时间',
    `end_time` datetime COMMENT '结束时间',
    `author_id` bigint(20) NOT NULL COMMENT '创建者ID',
    `views` int(11) NOT NULL DEFAULT '0' COMMENT '浏览量',
    `likes` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_author_id` (`author_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- 创建项目案例表
CREATE TABLE IF NOT EXISTS `project_case` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `project_id` bigint(20) NOT NULL COMMENT '项目ID',
    `description` text COMMENT '案例描述',
    `image_url` varchar(255) DEFAULT NULL COMMENT '案例图片URL',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目案例表';

-- 创建项目福利表
CREATE TABLE IF NOT EXISTS `project_benefit` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `project_id` bigint(20) NOT NULL COMMENT '项目ID',
    `content` varchar(255) NOT NULL COMMENT '福利内容',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目福利表';

-- 创建用户锦囊点赞表
CREATE TABLE IF NOT EXISTS `user_tips_like` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `tip_id` bigint(20) NOT NULL COMMENT '锦囊ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_tip` (`user_id`, `tip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户锦囊点赞表';

-- 创建用户锦囊收藏表
CREATE TABLE IF NOT EXISTS `user_tips_collection` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `tip_id` bigint(20) NOT NULL COMMENT '锦囊ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_tip` (`user_id`, `tip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户锦囊收藏表'; 