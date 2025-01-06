package com.aigroup.world.service.impl;

import com.aigroup.world.entity.AiTips;
import com.aigroup.world.entity.Project;
import com.aigroup.world.mapper.AiTipsMapper;
import com.aigroup.world.service.AiTipsService;
import com.aigroup.world.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * AI锦囊Service实现类
 */
@Service
public class AiTipsServiceImpl extends ServiceImpl<AiTipsMapper, AiTips> implements AiTipsService {

    @Autowired
    private ProjectService projectService;

    @Override
    public IPage<AiTips> getAiTipsList(Integer page, Integer size, String category) {
        LambdaQueryWrapper<AiTips> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AiTips::getStatus, 1); // 只查询已发布的
        if (category != null && !category.isEmpty()) {
            wrapper.eq(AiTips::getCategory, category);
        }
        wrapper.orderByDesc(AiTips::getCreateTime);
        
        // 获取分页数据
        IPage<AiTips> pageResult = page(new Page<>(page, size), wrapper);
        
        // 加载每个锦囊的项目信息
        for (AiTips tip : pageResult.getRecords()) {
            // 获取该锦囊关联的项目列表
            Page<Project> projectPage = projectService.getProjects(1, 100, tip.getId());
            if (projectPage != null && projectPage.getRecords() != null) {
                tip.setProjects(projectPage.getRecords());
            }
        }
        
        return pageResult;
    }

    @Override
    public AiTips getAiTipsDetail(Long id) {
        AiTips aiTips = getById(id);
        if (aiTips != null) {
            // 加载项目信息
            Page<Project> projectPage = projectService.getProjects(1, 100, aiTips.getId());
            if (projectPage != null && projectPage.getRecords() != null) {
                aiTips.setProjects(projectPage.getRecords());
            }
        }
        return aiTips;
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