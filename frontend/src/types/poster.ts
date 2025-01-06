// 分享海报数据接口
export interface SharePosterData {
  // 海报标题
  title: string
  // 海报描述
  description: string
  // 背景颜色
  backgroundColor: string
  // 品牌名称
  brandName: string
  // 成功案例列表
  cases?: string[]
  // 二维码提示文字
  qrCodeTip?: string
} 