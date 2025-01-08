package com.aigroup.world.entity;

import com.aigroup.world.model.User;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

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

    @TableField(exist = false)
    private List<AiTips> tips;

    @TableField(exist = false)
    private User referrerUser;
} 