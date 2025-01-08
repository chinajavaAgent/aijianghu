package com.aigroup.world.service.impl;

import com.aigroup.world.entity.Order;
import com.aigroup.world.mapper.OrderMapper;
import com.aigroup.world.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    @Transactional
    public Order createOrder(Long userId, Long tipsId, Long adminId, String title, String price) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTipsId(tipsId);
        order.setAdminId(adminId);
        order.setTitle(title);
        order.setPrice(new BigDecimal(price));
        order.setStatus(0); // 待审核状态
        
        // 生成订单编号：ORD + 年月日时分秒 + 4位随机数
        String orderNo = "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", (int)(Math.random() * 10000));
        order.setOrderNo(orderNo);
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        order.setApplyTime(now);
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        
        // 保存订单
        save(order);
        
        return order;
    }

    @Override
    public IPage<Order> getUserOrders(Long userId, Integer page, Integer size, Integer status) {
        Page<Order> pageParam = new Page<>(page, size);
        return baseMapper.selectUserOrdersWithAdmin(pageParam, userId, status);
    }

    @Override
    @Transactional
    public boolean approveOrder(Long orderId, Integer status) {
        Order order = getById(orderId);
        if (order == null) {
            return false;
        }
        
        order.setStatus(status);
        order.setApproveTime(LocalDateTime.now());
        
        return updateById(order);
    }
} 