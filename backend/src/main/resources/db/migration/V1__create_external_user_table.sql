-- 创建外部用户表
CREATE TABLE `external_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `phone` varchar(11) NOT NULL COMMENT '用户手机号',
    `platform` varchar(50) NOT NULL COMMENT '来源平台',
    `referrer_phone` varchar(11) DEFAULT NULL COMMENT '上级用户手机号',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '导入状态：0-待处理，1-已处理，2-处理失败',
    `process_result` varchar(255) DEFAULT NULL COMMENT '处理结果说明',
    `user_id` bigint(20) DEFAULT NULL COMMENT '关联的系统用户ID',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_phone` (`phone`),
    KEY `idx_platform` (`platform`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外部导入用户表'; 