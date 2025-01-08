package com.aigroup.world.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 外部用户导入DTO
 */
@Data
public class ExternalUserImportDTO {
    /**
     * 用户手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 平台来源
     */
    @NotBlank(message = "平台来源不能为空")
    private String platform;

    /**
     * 上级用户手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "上级用户手机号格式不正确")
    private String referrerPhone;
} 