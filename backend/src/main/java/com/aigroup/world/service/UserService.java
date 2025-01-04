package com.aigroup.world.service;

import com.aigroup.world.dto.RegisterRequest;
import com.aigroup.world.model.User;

public interface UserService {
    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 注册成功的用户信息
     */
    User register(RegisterRequest request);

    /**
     * 检查手机号是否已存在
     *
     * @param phone 手机号
     * @return true-存在，false-不存在
     */
    boolean checkPhoneExists(String phone);
} 