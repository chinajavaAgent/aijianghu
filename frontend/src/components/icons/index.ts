export const HomeIcon = {
  template: `
    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <!-- 古代门派大门 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 21h16a1 1 0 001-1v-8l-2-2-2 2v4h-3v-6l-2-2-2 2v6H7v-4l-2-2-2 2v8a1 1 0 001 1z"/>
      <!-- 屋顶 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2 12l10-8 10 8"/>
      <!-- 门匾 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 8h8"/>
    </svg>
  `
}

export const OpportunityIcon = {
  template: `
    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <!-- 宝剑 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 7v10M8 11l4-4 4 4"/>
      <!-- 剑柄 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 17h4"/>
      <!-- 剑气 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3l2 2-2 2-2-2z"/>
    </svg>
  `
}

export const OrderIcon = {
  template: `
    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <!-- 卷轴主体 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21H5a2 2 0 01-2-2V5a2 2 0 012-2h14a2 2 0 012 2v14a2 2 0 01-2 2z"/>
      <!-- 卷轴纹路 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h10M7 16h10"/>
      <!-- 卷轴装饰 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 3v18M19 3v18"/>
    </svg>
  `
}

export const ProfileIcon = {
  template: `
    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <!-- 侠客头部 -->
      <circle cx="12" cy="8" r="4" stroke-width="2"/>
      <!-- 发髻 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4c0 0 2-2 4 0M12 4c0 0-2-2-4 0"/>
      <!-- 衣袍 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 21v-2c0-2 2.5-4 6-4s6 2 6 4v2"/>
      <!-- 腰带 -->
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 15h10"/>
    </svg>
  `
}

export const OperationIcon = {
  template: `
    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <!-- 太极图形 -->
      <circle cx="12" cy="12" r="9" stroke-width="2"/>
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3c4.97 0 9 4.03 9 9s-4.03 9-9 9"/>
      <!-- 阴阳鱼 -->
      <circle cx="12" cy="8" r="2" stroke-width="2"/>
      <circle cx="12" cy="16" r="2" fill="currentColor"/>
    </svg>
  `
}

export default {
  home: HomeIcon,
  opportunity: OpportunityIcon,
  order: OrderIcon,
  profile: ProfileIcon,
  operation: OperationIcon
} 