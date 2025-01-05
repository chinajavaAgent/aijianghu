package com.aigroup.world.service.impl;

import com.aigroup.world.entity.AiTips;
import com.aigroup.world.mapper.AiTipsMapper;
import com.aigroup.world.service.AiTipsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * AI锦囊Service实现类
 */
@Service
public class AiTipsServiceImpl extends ServiceImpl<AiTipsMapper, AiTips> implements AiTipsService {

    @Override
    public IPage<AiTips> getAiTipsList(Integer page, Integer size, String category) {
        LambdaQueryWrapper<AiTips> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AiTips::getStatus, 1); // 只查询已发布的
        if (category != null && !category.isEmpty()) {
            wrapper.eq(AiTips::getCategory, category);
        }
        wrapper.orderByDesc(AiTips::getCreateTime);
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public AiTips getAiTipsDetail(Long id) {
        return getById(id);
    }

    @Override
    @Transactional
    public boolean createAiTips(AiTips aiTips) {
        aiTips.setCreateTime(LocalDateTime.now());
        aiTips.setUpdateTime(LocalDateTime.now());
        aiTips.setViews(0);
        aiTips.setLikes(0);
        return save(aiTips);
    }

    @Override
    @Transactional
    public boolean updateAiTips(AiTips aiTips) {
        aiTips.setUpdateTime(LocalDateTime.now());
        return updateById(aiTips);
    }

    @Override
    @Transactional
    public boolean deleteAiTips(Long id) {
        return removeById(id);
    }

    @Override
    @Transactional
    public boolean updateViews(Long id) {
        AiTips aiTips = getById(id);
        if (aiTips != null) {
            aiTips.setViews(aiTips.getViews() + 1);
            return updateById(aiTips);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateLikes(Long id, Integer increment) {
        AiTips aiTips = getById(id);
        if (aiTips != null) {
            aiTips.setLikes(aiTips.getLikes() + increment);
            return updateById(aiTips);
        }
        return false;
    }
} 