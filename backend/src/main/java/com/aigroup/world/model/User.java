package com.aigroup.world.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@TableName("user")
public class User {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信号
     */
    private String wechat;
    private String platform;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别：1-男，2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 职业
     */
    private String profession;

    /**
     * 工作经验（年）
     */
    private Integer experience;

    /**
     * 求道理由
     */
    private String reason;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 推荐人ID
     */
    private Long referrerId;

    /**
     * 用户等级
     */
    @TableField("level")
    private Integer level;

    /**
     * 用户积分
     */
    private Integer points;

    /**
     * 用户状态：0-禁用，1-正常
     */
    private Integer status;

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