package com.aigroup.world.dto;

import com.aigroup.world.enums.UserLevel;
import com.aigroup.world.model.User;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserInfoResponse {
    private Long id;
    private String phone;
    private String wechat;
    private String platform;
    private String realName;
    private Integer gender;
    private LocalDate birthDate;
    private String city;
    private String profession;
    private Integer experience;
    private String reason;
    private String introduction;
    private Long referrerId;
    private Integer level;
    private String levelTitle;
    private Integer points;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static UserInfoResponse from(User user) {
        UserInfoResponse response = new UserInfoResponse();
        response.setId(user.getId());
        response.setPhone(user.getPhone());
        response.setWechat(user.getWechat());
        response.setPlatform(user.getPlatform());
        response.setRealName(user.getRealName());
        response.setGender(user.getGender());
        response.setBirthDate(user.getBirthDate());
        response.setCity(user.getCity());
        response.setProfession(user.getProfession());
        response.setExperience(user.getExperience());
        response.setReason(user.getReason());
        response.setIntroduction(user.getIntroduction());
        response.setReferrerId(user.getReferrerId());
        response.setLevel(user.getLevel());
        response.setLevelTitle(UserLevel.getTitleByLevel(user.getLevel() != null ? user.getLevel() : 1));
        response.setPoints(user.getPoints());
        response.setStatus(user.getStatus());
        response.setCreateTime(user.getCreateTime());
        response.setUpdateTime(user.getUpdateTime());
        return response;
    }
} 