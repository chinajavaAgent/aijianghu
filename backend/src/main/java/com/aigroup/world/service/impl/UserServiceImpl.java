package com.aigroup.world.service.impl;

import com.aigroup.world.dto.RegisterRequest;
import com.aigroup.world.exception.BusinessException;
import com.aigroup.world.mapper.UserMapper;
import com.aigroup.world.model.User;
import com.aigroup.world.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(RegisterRequest request) {
        // 1. 验证两次密码是否一致
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }

        // 2. 验证手机号是否已存在
        if (checkPhoneExists(request.getPhone())) {
            throw new BusinessException("手机号已被注册");
        }

        // 3. 验证微信号是否已存在
        if (checkWechatExists(request.getWechat())) {
            throw new BusinessException("微信号已被注册");
        }

        // 4. 创建用户
        User user = new User();
        BeanUtils.copyProperties(request, user);
        
        // 设置密码（加密）
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // 设置初始等级和积分
        user.setLevel(1);
        user.setPoints(0);
        // 设置用户状态为正常
        user.setStatus(1);
        
        // 保存用户
        save(user);

        // 5. 清空密码后返回
        user.setPassword(null);
        return user;
    }

    @Override
    public boolean checkPhoneExists(String phone) {
        return baseMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone));
    }

    private boolean checkWechatExists(String wechat) {
        return baseMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getWechat, wechat));
    }
} 