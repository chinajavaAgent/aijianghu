package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mpg
 * @since 2024-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
@ApiModel(value="TUser对象", description="")
public class TUser extends Model<TUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，自增长的长整型，唯一标识每个用户")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "手机号，存储用户的电话号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "创建时间，记录用户账号的创建时间戳")
    @TableField("creation_time")
    private Date creationTime;

    @ApiModelProperty(value = "微信OpenID，与用户的微信账号相关联的唯一标识符")
    @TableField("wechat_openid")
    private String wechatOpenid;

    @ApiModelProperty(value = "头像URL，存储用户头像图片的网络链接")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "昵称，用户设置的昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "性别，用户的性别，0未知 1男 2女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "年龄，用户的年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty(value = "用户积分")
    @TableField("points")
    private BigDecimal points;

    @TableField("token")
    private String token;

    @ApiModelProperty(value = "推荐人id")
    @TableField("recommend_id")
    private Long recommendId;

    @ApiModelProperty(value = "绑定时间")
    @TableField("bind_time")
    private Date bindTime;

    @ApiModelProperty(value = "奖金")
    @TableField("reward")
    private BigDecimal reward;

    @ApiModelProperty(value = "白名单用户 0否 1是")
    @TableField("white_user")
    private Integer whiteUser;

    @ApiModelProperty(value = "是否经纪人 0否 1是")
    @TableField("proxy_user")
    private Integer proxyUser;

    @ApiModelProperty(value = "订单积分")
    @TableField("order_point")
    private BigDecimal orderPoint;


    public static final String ID = "id";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String CREATION_TIME = "creation_time";

    public static final String WECHAT_OPENID = "wechat_openid";

    public static final String AVATAR_URL = "avatar_url";

    public static final String NICKNAME = "nickname";

    public static final String GENDER = "gender";

    public static final String AGE = "age";

    public static final String POINTS = "points";

    public static final String TOKEN = "token";

    public static final String RECOMMEND_ID = "recommend_id";

    public static final String BIND_TIME = "bind_time";

    public static final String REWARD = "reward";

    public static final String WHITE_USER = "white_user";

    public static final String PROXY_USER = "proxy_user";

    public static final String ORDER_POINT = "order_point";

}
