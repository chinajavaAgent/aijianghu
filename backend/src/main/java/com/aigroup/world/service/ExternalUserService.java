package com.aigroup.world.service;

import com.aigroup.world.dto.ExternalUserImportDTO;
import com.aigroup.world.model.ExternalUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 外部用户服务接口
 */
public interface ExternalUserService extends IService<ExternalUser> {
    /**
     * 批量导入外部用户
     *
     * @param importDTOs 导入数据列表
     * @return 导入结果列表
     */
    List<ExternalUser> importUsers(List<ExternalUserImportDTO> importDTOs);

    /**
     * 分页查询外部用户
     *
     * @param page 分页参数
     * @param phone 手机号（可选）
     * @param platform 平台（可选）
     * @param status 状态（可选）
     * @return 分页结果
     */
    Page<ExternalUser> pageQuery(Page<ExternalUser> page, String phone, String platform, Integer status);

    /**
     * 处理外部用户
     * 将外部用户转换为系统用户
     *
     * @param id 外部用户ID
     * @return 处理结果
     */
    ExternalUser processUser(Long id);
} 