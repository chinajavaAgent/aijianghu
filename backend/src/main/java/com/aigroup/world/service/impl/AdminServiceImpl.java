package com.aigroup.world.service.impl;

import com.aigroup.world.entity.Admin;
import com.aigroup.world.mapper.AdminMapper;
import com.aigroup.world.service.AdminService;
import com.aigroup.world.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private final FileService fileService;
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public List<Admin> list() {
        return adminMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    @Transactional
    public Admin add(String phone, String wechatId, String realName, MultipartFile wechatQrCode) {
        Admin admin = new Admin();
        admin.setPhone(phone);
        admin.setWechatId(wechatId);
        admin.setRealName(realName);
        
//        if (wechatQrCode != null && !wechatQrCode.isEmpty()) {
//            String qrCodeUrl = fileService.uploadFile(wechatQrCode);
//            admin.setWechatQrCode(qrCodeUrl);
//        }
        
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        save(admin);
        return admin;
    }

    @Override
    @Transactional
    public Admin update(Long id, String phone, String wechatId, String realName, MultipartFile wechatQrCode) {
        Admin admin = getById(id);
        if (admin == null) {
            throw new RuntimeException("管理员不存在");
        }

        if (phone != null) {
            admin.setPhone(phone);
        }
        if (wechatId != null) {
            admin.setWechatId(wechatId);
        }
        if (realName != null) {
            admin.setRealName(realName);
        }
//        if (wechatQrCode != null && !wechatQrCode.isEmpty()) {
//            String qrCodeUrl = fileService.uploadFile(wechatQrCode);
//            admin.setWechatQrCode(qrCodeUrl);
//        }

        admin.setUpdatedAt(LocalDateTime.now());
        updateById(admin);
        return admin;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        removeById(id);
    }
} 