package com.aigroup.world.dto;

import com.aigroup.world.entity.ProjectCase;
import lombok.Data;
import java.util.List;

/**
 * 分享海报数据
 */
@Data
public class SharePosterData {
    /**
     * 项目名称
     */
    private String title;

    /**
     * 项目介绍
     */
    private String description;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 项目福利列表
     */
    private List<String> benefits;

    /**
     * 项目案例列表
     */
    private List<ProjectCase> cases;

    /**
     * 二维码提示文字
     */
    private String qrCodeTip;
} 