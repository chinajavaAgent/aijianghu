package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("admin")
public class Admin {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String phone;
    
    @TableField("wechat_id")
    private String wechatId;
    
    @TableField("real_name")
    private String realName;
    
    @TableField("wechat_qr_code")
    private String wechatQrCode;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
} 