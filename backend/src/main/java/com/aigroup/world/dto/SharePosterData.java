package com.aigroup.world.dto;

import lombok.Data;
import java.util.List;

/**
 * 分享海报数据
 */
@Data
public class SharePosterData {
    /**
     * 锦囊标题
     */
    private String title;

    /**
     * 锦囊下的项目列表
     */
    private List<Project> projects;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 二维码提示文字
     */
    private String qrCodeTip;

    /**
     * 项目信息
     */
    @Data
    public static class Project {
        /**
         * 项目名称
         */
        private String title;

        /**
         * 项目介绍
         */
        private String description;

        /**
         * 项目视频链接
         */
        private String videoUrl;

        /**
         * 项目案例列表
         */
        private List<String> cases;

        /**
         * 项目福利列表
         */
        private List<String> benefits;
    }
} 