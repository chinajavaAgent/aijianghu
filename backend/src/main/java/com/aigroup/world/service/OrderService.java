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

    /**
     * 根据手机号查询订单列表
     * @param phone 手机号
     * @param page 页码
     * @param size 每页大小
     * @param status 订单状态
     * @return 订单列表
     */
    IPage<Order> getOrdersByPhone(String phone, Integer page, Integer size, Integer status);
} 