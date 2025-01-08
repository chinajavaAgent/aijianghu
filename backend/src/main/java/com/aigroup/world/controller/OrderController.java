package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.entity.Order;
import com.aigroup.world.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping
    public Result<Order> createOrder(
            @RequestParam Long userId,
            @RequestParam Long tipsId,
            @RequestParam Long adminId,
            @RequestParam String title,
            @RequestParam String price
    ) {
        Order order = orderService.createOrder(userId, tipsId, adminId, title, price);
        return Result.success(order);
    }

    /**
     * 获取用户订单列表
     */
    @GetMapping("/user/{userId}")
    public Result<IPage<Order>> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status
    ) {
        IPage<Order> orders = orderService.getUserOrders(userId, page, size, status);
        return Result.success(orders);
    }

    /**
     * 审核订单
     */
    @PutMapping("/{orderId}/approve")
    public Result<Boolean> approveOrder(
            @PathVariable Long orderId,
            @RequestParam Integer status
    ) {
        boolean success = orderService.approveOrder(orderId, status);
        return Result.success(success);
    }
} 