package com.aigroup.world.service.impl;

import com.aigroup.world.entity.Order;
import com.aigroup.world.entity.AiTips;
import com.aigroup.world.mapper.UserMapper;
import com.aigroup.world.model.User;
import com.aigroup.world.mapper.OrderMapper;
import com.aigroup.world.service.OrderService;
import com.aigroup.world.service.AiTipsService;
import com.aigroup.world.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private AiTipsService aiTipsService;
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
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
        User user = userMapper.selectById(userId);
        String phone = user.getPhone();
        // 生成订单编号：ORD + 年月日时分秒 + 4位随机数
        String orderNo = "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", (int)(Math.random() * 10000));
        order.setOrderNo(orderNo);
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        order.setApplyTime(now);
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setCheckPhone(phone);
        // 保存订单
        save(order);
        
        return order;
    }

    @Override
    public IPage<Order> getUserOrders(Long userId, Integer page, Integer size, Integer status, Boolean isBig) {
        Page<Order> pageParam = new Page<>(page, size);
        return orderMapper.selectUserOrdersWithAdmin(pageParam, userId, status, isBig);
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
        
        // 如果订单审核通过，设置用户等级
        if (status == 1) { // 假设status=1表示审核通过
            // 获取锦囊信息
            AiTips aiTips = aiTipsService.getById(order.getTipsId());
            if (aiTips != null) {
                // 获取用户信息
                User user = userMapper.selectById(order.getUserId());
                if (user != null) {
                    // 设置用户等级为锦囊要求的等级
                    user.setLevel(aiTips.getRequiredLevel());
                    userMapper.updateById(user);
                }
            }
        }
        
        return updateById(order);
    }

    /**
     * 根据锦囊ID获取订单列表
     */
    @Override
    public IPage<Order> getOrdersByTipId(Long tipId, Integer page, Integer size, Integer status) {
        Page<Order> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
            .eq(Order::getTipsId, tipId)
            .eq(status != null, Order::getStatus, status)
            .orderByDesc(Order::getCreatedAt);
        
        return this.page(pageParam, queryWrapper);
    }

    @Override
    public IPage<Order> getOrdersByPhone(String phone, Integer page, Integer size, Integer status) {
        Page<Order> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
            .eq(Order::getCheckPhone, phone)
            .eq(status != null, Order::getStatus, status)
            .orderByDesc(Order::getCreatedAt);
        
        return this.page(pageParam, queryWrapper);
    }
} 