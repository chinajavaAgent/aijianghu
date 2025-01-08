package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.entity.Order;
import com.aigroup.world.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Data
    public static class CreateOrderRequest {
        private Long userId;
        private Long tipsId;
        private Long adminId;
        private String title;
        private String price;
    }

    /**
     * 创建订单
     */
    @PostMapping
    public Result<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(
            request.getUserId(),
            request.getTipsId(),
            request.getAdminId(),
            request.getTitle(),
            request.getPrice()
        );
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
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Boolean isBig
    ) {
        IPage<Order> orders = orderService.getUserOrders(userId, page, size, status, isBig);
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