export interface Project {
    /**
     * 项目名称
     */
    title: string;

    /**
     * 项目介绍
     */
    description: string;

    /**
     * 项目详细信息
     */
    detail?: string;

    /**
     * 项目视频链接
     */
    videoUrl?: string;

    /**
     * 项目案例列表
     */
    cases?: string[];

    /**
     * 项目福利列表
     */
    benefits?: string[];
}

export interface SharePosterData {
    /**
     * 锦囊标题
     */
    title: string;

    /**
     * 锦囊下的项目列表
     */
    projects: Project[];

    /**
     * 品牌名称
     */
    brandName: string;

    /**
     * 二维码提示文字
     */
    qrCodeTip: string;
} 