package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    
    private BigDecimal price;
    
    private String category;
    
    private String tags;
    
    private Long authorId;
    
    private Integer requiredLevel;
    
    private Integer views;
    
    private Integer likes;
    
    private Integer status; // 0:草稿 1:已发布
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private Integer deleted; // 0:未删除 1:已删除
    
    @TableField(exist = false)
    private List<Project> projects;
} 