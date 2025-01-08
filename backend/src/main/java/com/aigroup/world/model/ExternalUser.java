package com.aigroup.world.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 外部导入用户实体类
 */
@Data
@TableName("external_user")
public class ExternalUser {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 来源平台
     */
    private String platform;

    /**
     * 上级用户手机号
     */
    private String referrerPhone;
    

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
} 