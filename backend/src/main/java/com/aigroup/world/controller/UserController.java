package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.dto.LoginRequest;
import com.aigroup.world.dto.LoginResponse;
import com.aigroup.world.dto.RegisterRequest;
import com.aigroup.world.model.User;
import com.aigroup.world.service.UserService;
import com.aigroup.world.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<User> register(@Validated @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return Result.success(user);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        // 登录验证
        User user = userService.login(request);
        
        // 生成token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getPhone());
        String token = jwtUtil.generateToken(userDetails);
        
        // 返回登录响应
        return Result.success(LoginResponse.of(token, user));
    }

    @ApiOperation("检查手机号是否存在")
    @GetMapping("/check-phone")
    public Result<Boolean> checkPhone(@RequestParam String phone) {
        return Result.success(userService.checkPhoneExists(phone));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }
} 