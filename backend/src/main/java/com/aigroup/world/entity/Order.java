package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private Long tipsId;
    
    private Long adminId;
    
    private String title;
    
    private BigDecimal price;
    
    private Integer status;
    
    private LocalDateTime applyTime;
    
    private LocalDateTime approveTime;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    // 用户信息（非数据库字段）
    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private Integer gender;

    @TableField(exist = false)
    private Integer age;

    @TableField(exist = false)
    private String city;

    @TableField(exist = false)
    private String userPhone;

    @TableField(exist = false)
    private String userWechat;

    @TableField(exist = false)
    private String profession;

    @TableField(exist = false)
    private Integer experience;

    @TableField(exist = false)
    private String reason;

    @TableField(exist = false)
    private String introduction;
} 