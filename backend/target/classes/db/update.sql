-- 添加项目视频URL字段
ALTER TABLE `project` 
ADD COLUMN `video_url` varchar(255) DEFAULT NULL COMMENT '视频URL' 
AFTER `cover_image`; 

-- 创建管理员表
CREATE TABLE IF NOT EXISTS `aijianghu`.`admin` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号码',
    `wechat_id` VARCHAR(50) NOT NULL COMMENT '微信号',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `wechat_qr_code` VARCHAR(255) COMMENT '微信二维码图片URL',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表'; 

-- 创建管理员和锦囊关联表
CREATE TABLE IF NOT EXISTS `aijianghu`.`admin_tips` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `admin_id` BIGINT NOT NULL COMMENT '管理员ID',
    `tips_id` BIGINT NOT NULL COMMENT '锦囊ID',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`admin_id`) REFERENCES `admin`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`tips_id`) REFERENCES `ai_tips`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_admin_tips` (`admin_id`, `tips_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员锦囊关联表'; 

-- 创建订单表
CREATE TABLE IF NOT EXISTS `aijianghu`.`order` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `tips_id` BIGINT NOT NULL COMMENT '锦囊ID',
    `admin_id` BIGINT NOT NULL COMMENT '审核管理员ID',
    `title` VARCHAR(100) NOT NULL COMMENT '订单标题',
    `price` DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态：0-待审核，1-已通过，2-已拒绝',
    `apply_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `approve_time` DATETIME COMMENT '审核时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`tips_id`) REFERENCES `ai_tips`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`admin_id`) REFERENCES `admin`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_tips_id` (`tips_id`),
    KEY `idx_admin_id` (`admin_id`),
    KEY `idx_status` (`status`),
    KEY `idx_apply_time` (`apply_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表'; 