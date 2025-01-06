export interface SharePosterData {
    /**
     * 项目名称
     */
    title: string;

    /**
     * 项目介绍
     */
    description: string;

    /**
     * 项目案例
     */
    cases?: string[];

    /**
     * 项目福利
     */
    benefits?: string[];

    /**
     * 品牌名称
     */
    brandName: string;

    /**
     * 二维码提示文字
     */
    qrCodeTip: string;
} 