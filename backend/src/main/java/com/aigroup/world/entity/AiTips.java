package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * AI锦囊实体类
 */
@Data
@TableName("ai_tips")
public class AiTips {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String description;
    
    private String content;
    
    private String category;
    
    private Integer requiredLevel;
    
    private Integer views;
    
    private Integer likes;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private Integer status; // 0:草稿 1:已发布 2:已下架
} 