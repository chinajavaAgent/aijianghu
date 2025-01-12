package com.aigroup.world.service.impl;

import com.aigroup.world.entity.Admin;
import com.aigroup.world.entity.AdminTips;
import com.aigroup.world.entity.AiTips;
import com.aigroup.world.enums.UserLevel;
import com.aigroup.world.exception.BusinessException;
import com.aigroup.world.mapper.AdminMapper;
import com.aigroup.world.mapper.AdminTipsMapper;
import com.aigroup.world.mapper.AiTipsMapper;
import com.aigroup.world.mapper.UserMapper;
import com.aigroup.world.mapper.secondary.TUserMapper;
import com.aigroup.world.model.User;
import com.aigroup.world.entity.TUser;
import com.aigroup.world.service.AdminService;
import com.aigroup.world.service.AiTipsService;
import com.aigroup.world.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private final FileService fileService;
    private final AdminTipsMapper adminTipsMapper;
    private final AiTipsService aiTipsService;
    private final AiTipsMapper aiTipsMapper;
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;
    private final TUserMapper tUserMapper;

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

    /**
     * 根据手机，去寻找加ai群符合要求的师傅（往上找两级）
     * @param currentUserPhone
     * @param level
     * @return
     */
    public User findReferrerUser(String currentUserPhone, int level) {
        // 在第二数据源中查询推荐人ID
        TUser tUser = tUserMapper.selectOne(new LambdaQueryWrapper<TUser>()
                .eq(TUser::getPhoneNumber, currentUserPhone));
        if (tUser == null) {
            return null;
        }
        if(null == tUser.getRecommendId()){
            return null;
        }
        // 通过推荐人ID查询推荐人的手机号
        TUser recommender = tUserMapper.selectById(tUser.getRecommendId());
        if (recommender == null) {
            return null;
        }
        Long recommendId = recommender.getRecommendId();
        if (recommendId == null) {
            return null;
        }
        TUser parentRecommender = tUserMapper.selectById(tUser.getRecommendId());
        if (parentRecommender == null) {
            return null;
        }
        log.info("推荐人手机号：{}", parentRecommender.getPhoneNumber());
        // 在第一数据源中查询推荐人用户信息
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .ge(User::getLevel, level+1)
                .eq(User::getPhone, parentRecommender.getPhoneNumber()));
    }

    @Override
    public Admin getAdminByTipId(Long tipId) {
        AiTips aiTips = aiTipsMapper.selectById(tipId);
        // 获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String currentUserPhone = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, currentUserPhone));
            Integer level = user.getLevel();
            Integer requiredLevel = aiTips.getRequiredLevel();
            if(requiredLevel> level+1){
                String titleByLevel = UserLevel.getTitleByLevel(requiredLevel-1);
                throw new BusinessException("少侠，练功需循序渐进，请先提升至"+titleByLevel+"境，才能查看此锦囊");
            }
            AdminTips adminTips = adminTipsMapper.selectOne(new LambdaQueryWrapper<AdminTips>().eq(AdminTips::getTipsId, tipId));
            if(null == adminTips){
                return null;
            }
            Long adminId = adminTips.getAdminId();
            admin = adminMapper.selectById(adminId);
            User referrerUser = findReferrerUser(currentUserPhone, requiredLevel);
            if(null!=referrerUser) {
                log.info("referrerUser：{}", referrerUser.getPhone());
            }
            admin.setReferrerUser(referrerUser);
        }

        return admin;
    }
} 