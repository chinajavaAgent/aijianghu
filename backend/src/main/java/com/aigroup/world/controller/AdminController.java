package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
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
    public Result<List<Admin>> list() {
        return Result.success(adminService.list());
    }

    @PostMapping
    public Result<Admin> add(
            @RequestParam String phone,
            @RequestParam String wechatId,
            @RequestParam String realName,
            @RequestParam(required = false) MultipartFile wechatQrCode
    ) {
        return Result.success(adminService.add(phone, wechatId, realName, wechatQrCode));
    }

    @PutMapping("/{id}")
    public Result<Admin> update(
            @PathVariable Long id,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String wechatId,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) MultipartFile wechatQrCode
    ) {
        return Result.success(adminService.update(id, phone, wechatId, realName, wechatQrCode));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        adminService.delete(id);
        return Result.success();
    }
} 