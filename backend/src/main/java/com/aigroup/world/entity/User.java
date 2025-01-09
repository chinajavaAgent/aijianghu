package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String phone;
    private String password;
    private String wechat;
    private String nickname;
    private Integer level;
    private Integer points;
    private Integer status;
    
    @TableField("referrer_id")
    private Long referrerId;  // 推荐人ID
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 