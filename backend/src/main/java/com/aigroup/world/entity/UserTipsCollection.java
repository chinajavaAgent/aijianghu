package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户锦囊收藏实体类
 */
@Data
@TableName("user_tips_collection")
public class UserTipsCollection {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long tipId;
    
    private LocalDateTime createTime;
} 