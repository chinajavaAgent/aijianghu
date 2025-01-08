package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.dto.ExternalUserImportDTO;
import com.aigroup.world.model.ExternalUser;
import com.aigroup.world.service.ExternalUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 外部用户控制器
 */
@Api(tags = "外部用户管理")
@RestController
@RequestMapping("/api/external-users")
@RequiredArgsConstructor
public class ExternalUserController {

    private final ExternalUserService externalUserService;

    @ApiOperation("批量导入外部用户")
    @PostMapping("/import")
    public Result<List<ExternalUser>> importUsers(@Validated @RequestBody List<ExternalUserImportDTO> importDTOs) {
        List<ExternalUser> users = externalUserService.importUsers(importDTOs);
        return Result.success(users);
    }

    @ApiOperation("分页查询外部用户")
    @GetMapping("/page")
    public Result<Page<ExternalUser>> pageQuery(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer size,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @ApiParam("平台") @RequestParam(required = false) String platform,
            @ApiParam("状态") @RequestParam(required = false) Integer status) {
        Page<ExternalUser> page = externalUserService.pageQuery(
                new Page<>(current, size), phone, platform, status);
        return Result.success(page);
    }

    @ApiOperation("处理外部用户")
    @PostMapping("/{id}/process")
    public Result<ExternalUser> processUser(@PathVariable Long id) {
        ExternalUser user = externalUserService.processUser(id);
        return Result.success(user);
    }
} 