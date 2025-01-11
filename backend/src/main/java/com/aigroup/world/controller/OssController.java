package com.aigroup.world.controller;

import com.aigroup.world.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件上传控制器
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/aiGroup/oss")
@RequiredArgsConstructor
public class OssController {

    private final OssService ossService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "目录", required = false, defaultValue = "default") @RequestParam(value = "directory", defaultValue = "default") String directory) {
        String url = ossService.uploadFile(file, directory);
        return ResponseEntity.ok(url);
    }

    @ApiOperation("文件删除")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(
            @ApiParam(value = "文件URL", required = true) @RequestParam("fileUrl") String fileUrl) {
        ossService.deleteFile(fileUrl);
        return ResponseEntity.ok().build();
    }
} 