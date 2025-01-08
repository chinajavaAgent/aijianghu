package com.aigroup.world.mapper;

import com.aigroup.world.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 获取用户订单列表（包含管理员信息）
     */
    IPage<Order> selectUserOrdersWithAdmin(Page<Order> page, @Param("userId") Long userId, @Param("status") Integer status);
} 