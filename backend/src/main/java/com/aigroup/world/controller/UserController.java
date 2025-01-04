package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.dto.RegisterRequest;
import com.aigroup.world.model.User;
import com.aigroup.world.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<User> register(@Validated @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return Result.success(user);
    }

    @ApiOperation("检查手机号是否存在")
    @GetMapping("/check-phone")
    public Result<Boolean> checkPhone(@RequestParam String phone) {
        return Result.success(userService.checkPhoneExists(phone));
    }
} 