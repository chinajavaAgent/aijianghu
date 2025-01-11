package com.aigroup.world.service;

import com.aigroup.world.entity.Order;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {
    /**
     * 创建订单
     */
    Order createOrder(Long userId, Long tipsId, Long adminId, String title, String price);

    /**
     * 获取用户的订单列表
     */
    IPage<Order> getUserOrders(Long userId, Integer page, Integer size, Integer status, Boolean isBig);

    /**
     * 审核订单
     */
    boolean approveOrder(Long orderId, Integer status);

    /**
     * 根据锦囊ID获取订单列表
     */
    IPage<Order> getOrdersByTipId(Long tipId, Integer page, Integer size, Integer status);
} 