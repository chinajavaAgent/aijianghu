package com.aigroup.world.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_resource")
public class UserResource {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long resourceId;
    
    private LocalDateTime downloadTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 