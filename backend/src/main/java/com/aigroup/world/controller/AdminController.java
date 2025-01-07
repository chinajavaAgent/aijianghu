package com.aigroup.world.controller;

import com.aigroup.world.entity.Admin;
import com.aigroup.world.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/list")
    public List<Admin> list() {
        return adminService.list();
    }

    @PostMapping
    public Admin add(
            @RequestParam String phone,
            @RequestParam String wechatId,
            @RequestParam String realName,
            @RequestParam(required = false) MultipartFile wechatQrCode
    ) {
        return adminService.add(phone, wechatId, realName, wechatQrCode);
    }

    @PutMapping("/{id}")
    public Admin update(
            @PathVariable Long id,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String wechatId,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) MultipartFile wechatQrCode
    ) {
        return adminService.update(id, phone, wechatId, realName, wechatQrCode);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminService.delete(id);
    }
} 