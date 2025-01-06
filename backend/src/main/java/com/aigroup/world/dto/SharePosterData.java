package com.aigroup.world.dto;

import lombok.Data;
import java.util.List;

/**
 * 分享海报数据
 */
@Data
public class SharePosterData {
    /**
     * 海报标题
     */
    private String title;

    /**
     * 海报描述
     */
    private String description;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 成功案例列表
     */
    private List<String> cases;

    /**
     * 二维码提示文字
     */
    private String qrCodeTip;
} 