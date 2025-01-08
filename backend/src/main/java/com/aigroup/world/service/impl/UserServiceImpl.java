package com.aigroup.world.service.impl;

import com.aigroup.world.dto.LoginRequest;
import com.aigroup.world.dto.RegisterRequest;
import com.aigroup.world.entity.TUser;
import com.aigroup.world.exception.BusinessException;
import com.aigroup.world.mapper.UserMapper;
import com.aigroup.world.mapper.secondary.TUserMapper;
import com.aigroup.world.model.User;
import com.aigroup.world.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TUserMapper tUserMapper;
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
    public User login(LoginRequest request) {
        // 1. 根据手机号查询用户
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, request.getPhone())
                .eq(User::getStatus, 1));

        // 2. 验证用户是否存在
        if (user == null) {
            throw new BusinessException("用户不存在或已被禁用");
        }

        // 3. 验证密码是否正确
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 4. 清空密码后返回
        user.setPassword(null);
        return user;
    }

    @Override
    public boolean checkPhoneExists(String phone) {
        return baseMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone));
    }

    public boolean checkWechatExists(String wechat) {
        return baseMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getWechat, wechat));
    }

    @Override
    public User findByPhone(String phone) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone));
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);

        if(user.getLevel()==0) {
            TUser tUser = tUserMapper.selectOne(new LambdaQueryWrapper<TUser>(new TUser())
                    .eq(TUser::getPhoneNumber, user.getPhone()));
            if (tUser != null) {
                Integer proxyUser = tUser.getProxyUser();
                if(proxyUser == 1) {
                    user.setLevel(1);
                    userMapper.updateById(user);
                }
            }
        }
        return user;
    }
} 