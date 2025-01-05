-- 添加项目视频URL字段
ALTER TABLE `project` 
ADD COLUMN `video_url` varchar(255) DEFAULT NULL COMMENT '视频URL' 
AFTER `cover_image`; 