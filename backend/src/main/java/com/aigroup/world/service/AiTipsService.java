package com.aigroup.world.service;

import com.aigroup.world.entity.AiTips;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * AI锦囊Service接口
 */
public interface AiTipsService extends IService<AiTips> {
    
    /**
     * 分页查询AI锦囊列表
     * @param page 页码
     * @param size 每页大小
     * @param category 分类
     * @return 分页结果
     */
    IPage<AiTips> getAiTipsList(Integer page, Integer size, String category);
    
    /**
     * 获取AI锦囊详情
     * @param id 锦囊ID
     * @return AI锦囊详情
     */
    AiTips getAiTipsDetail(Long id);
    
    /**
     * 创建AI锦囊
     * @param aiTips AI锦囊信息
     * @return 是否成功
     */
    boolean createAiTips(AiTips aiTips);
    
    /**
     * 更新AI锦囊
     * @param aiTips AI锦囊信息
     * @return 是否成功
     */
    boolean updateAiTips(AiTips aiTips);
    
    /**
     * 删除AI锦囊
     * @param id 锦囊ID
     * @return 是否成功
     */
    boolean deleteAiTips(Long id);
    
    /**
     * 更新浏览量
     * @param id 锦囊ID
     * @return 是否成功
     */
    boolean updateViews(Long id);
    
    /**
     * 更新点赞数
     * @param id 锦囊ID
     * @param increment 增量（可为负数）
     * @return 是否成功
     */
    boolean updateLikes(Long id, Integer increment);
} 