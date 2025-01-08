package com.aigroup.world.service.impl;

import com.aigroup.world.dto.ExternalUserImportDTO;
import com.aigroup.world.exception.BusinessException;
import com.aigroup.world.mapper.ExternalUserMapper;
import com.aigroup.world.model.ExternalUser;
import com.aigroup.world.service.ExternalUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 外部用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class ExternalUserServiceImpl extends ServiceImpl<ExternalUserMapper, ExternalUser> implements ExternalUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ExternalUser> importUsers(List<ExternalUserImportDTO> importDTOs) {
        if (importDTOs == null || importDTOs.isEmpty()) {
            throw new BusinessException("导入数据不能为空");
        }

        // 1. 验证数据格式
        for (ExternalUserImportDTO dto : importDTOs) {
            if (!dto.getPhone().matches("^1[3-9]\\d{9}$")) {
                throw new BusinessException("手机号格式不正确：" + dto.getPhone());
            }
            if (StringUtils.hasText(dto.getReferrerPhone()) && !dto.getReferrerPhone().matches("^1[3-9]\\d{9}$")) {
                throw new BusinessException("上级用户手机号格式不正确：" + dto.getReferrerPhone());
            }
        }

        // 2. 构建外部用户对象
        List<ExternalUser> users = new ArrayList<>();
        for (ExternalUserImportDTO dto : importDTOs) {
            ExternalUser user = new ExternalUser();
            user.setPhone(dto.getPhone());
            user.setPlatform(dto.getPlatform());
            user.setReferrerPhone(dto.getReferrerPhone());
            users.add(user);
        }

        // 3. 批量保存
        saveBatch(users);

        return users;
    }

    @Override
    public Page<ExternalUser> pageQuery(Page<ExternalUser> page, String phone, String platform, Integer status) {
        LambdaQueryWrapper<ExternalUser> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (StringUtils.hasText(phone)) {
            wrapper.like(ExternalUser::getPhone, phone);
        }
        if (StringUtils.hasText(platform)) {
            wrapper.eq(ExternalUser::getPlatform, platform);
        }
        
        // 按创建时间降序排序
        wrapper.orderByDesc(ExternalUser::getCreateTime);
        
        return page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExternalUser processUser(Long id) {
        // 1. 获取外部用户信息
        ExternalUser externalUser = getById(id);
        if (externalUser == null) {
            throw new BusinessException("外部用户不存在");
        }

        try {
            updateById(externalUser);

            return externalUser;
        } catch (Exception e) {;
            updateById(externalUser);
            throw new BusinessException("处理失败：" + e.getMessage());
        }
    }
} 