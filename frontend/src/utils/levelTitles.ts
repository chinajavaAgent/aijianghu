// 江湖等级称号映射
export const getLevelTitle = (level: number): string => {
  const titles: { [key: number]: string } = {
    1: '初入江湖',
    2: '武学新手',
    3: '练气小成',
    4: '气宗师',
    5: '武道大师',
    6: '江湖豪侠',
    7: '武林高手',
    8: '绝世高手',
    9: '武林盟主',
    10: '武道至尊'
  }
  return titles[level] || '修炼者'
}

// 获取下一个等级称号
export const getNextLevelTitle = (level: number): string => {
  return getLevelTitle(level + 1)
}

// 获取等级对应的颜色
export const getLevelColor = (level: number): string => {
  if (level >= 10) return 'text-red-600'
  if (level >= 8) return 'text-purple-600'
  if (level >= 6) return 'text-blue-600'
  if (level >= 4) return 'text-cyan-600'
  return 'text-green-600'
} 