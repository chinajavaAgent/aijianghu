package com.aigroup.world.service;

import com.aigroup.world.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface AdminService extends IService<Admin> {
    List<Admin> list();
    
    Admin add(String phone, String wechatId, String realName, MultipartFile wechatQrCode);
    
    Admin update(Long id, String phone, String wechatId, String realName, MultipartFile wechatQrCode);
    
    void delete(Long id);
} 