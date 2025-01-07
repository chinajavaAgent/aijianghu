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