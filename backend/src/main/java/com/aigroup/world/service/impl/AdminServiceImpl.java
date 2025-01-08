package com.aigroup.world.service.impl;

import com.aigroup.world.entity.Admin;
import com.aigroup.world.entity.AdminTips;
import com.aigroup.world.entity.AiTips;
import com.aigroup.world.mapper.AdminMapper;
import com.aigroup.world.mapper.AdminTipsMapper;
import com.aigroup.world.service.AdminService;
import com.aigroup.world.service.AiTipsService;
import com.aigroup.world.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private final FileService fileService;
    private final AdminTipsMapper adminTipsMapper;
    private final AiTipsService aiTipsService;
    private final AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        List<Admin> admins = super.list();
        // 获取每个管理员关联的锦囊
        for (Admin admin : admins) {
            List<Long> tipsIds = getAdminTipsIds(admin.getId());
            if (!tipsIds.isEmpty()) {
                List<AiTips> tips = aiTipsService.listByIds(tipsIds);
                admin.setTips(tips);
            } else {
                admin.setTips(new ArrayList<>());
            }
        }
        return admins;
    }

    @Override
    @Transactional
    public Admin add(String phone, String wechatId, String realName, MultipartFile wechatQrCode, List<Long> tipsIds) {
        // 创建管理员
        Admin admin = new Admin();
        admin.setPhone(phone);
        admin.setWechatId(wechatId);
        admin.setRealName(realName);
        
        // 上传二维码
//        if (wechatQrCode != null) {
//            String qrCodeUrl = fileService.uploadFile(wechatQrCode);
//            admin.setWechatQrCode(qrCodeUrl);
//        }
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        
        // 保存管理员
        save(admin);
        
        // 保存关联的锦囊
        if (tipsIds != null && !tipsIds.isEmpty()) {
            saveAdminTips(admin.getId(), tipsIds);
        }
        
        return admin;
    }

    @Override
    @Transactional
    public Admin update(Long id, String phone, String wechatId, String realName, MultipartFile wechatQrCode, List<Long> tipsIds) {
        // 获取管理员
        Admin admin = getById(id);
        if (admin == null) {
            throw new RuntimeException("管理员不存在");
        }
        
        // 更新基本信息
        if (phone != null) {
            admin.setPhone(phone);
        }
        if (wechatId != null) {
            admin.setWechatId(wechatId);
        }
        if (realName != null) {
            admin.setRealName(realName);
        }
        
        // 更新二维码
//        if (wechatQrCode != null) {
//            String qrCodeUrl = fileService.uploadFile(wechatQrCode);
//            admin.setWechatQrCode(qrCodeUrl);
//        }
        
        // 更新时间
        admin.setUpdatedAt(LocalDateTime.now());
        
        // 保存管理员
        updateById(admin);
        
        // 更新关联的锦囊
        if (tipsIds != null) {
            // 先删除原有关联
            deleteAdminTips(id);
            // 保存新的关联
            if (!tipsIds.isEmpty()) {
                saveAdminTips(id, tipsIds);
            }
        }
        
        return admin;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 删除管理员
        removeById(id);
        // 删除关联的锦囊
        deleteAdminTips(id);
    }

    @Override
    public List<Long> getAdminTipsIds(Long adminId) {
        LambdaQueryWrapper<AdminTips> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminTips::getAdminId, adminId);
        return adminTipsMapper.selectList(wrapper)
                .stream()
                .map(AdminTips::getTipsId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAdminTips(Long adminId, List<Long> tipsIds) {
        // 先删除原有关联
        deleteAdminTips(adminId);
        // 保存新的关联
        if (tipsIds != null && !tipsIds.isEmpty()) {
            saveAdminTips(adminId, tipsIds);
        }
    }

    private void saveAdminTips(Long adminId, List<Long> tipsIds) {
        List<AdminTips> adminTipsList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (Long tipsId : tipsIds) {
            AdminTips adminTips = new AdminTips();
            adminTips.setAdminId(adminId);
            adminTips.setTipsId(tipsId);
            adminTips.setCreatedAt(now);
            adminTips.setUpdatedAt(now);
            adminTipsList.add(adminTips);
        }
        
        adminTipsMapper.insertBatch(adminTipsList);
    }

    private void deleteAdminTips(Long adminId) {
        LambdaQueryWrapper<AdminTips> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminTips::getAdminId, adminId);
        adminTipsMapper.delete(wrapper);
    }

    @Override
    public Admin getAdminByTipId(Long tipId) {
        AdminTips adminTips = adminTipsMapper.selectOne(new LambdaQueryWrapper<AdminTips>().eq(AdminTips::getTipsId, tipId));
        if(null == adminTips){
            return null;
        }
        Long adminId = adminTips.getAdminId();
        return adminMapper.selectById(adminId);
    }
} 