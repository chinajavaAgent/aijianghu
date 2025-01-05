package com.aigroup.world.service.impl;

import com.aigroup.world.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    @Value("${file.access.url:http://localhost:8080/uploads}")
    private String accessUrl;

    @Override
    public String uploadImage(MultipartFile file) {
        return uploadFile(file, "images");
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        return uploadFile(file, "videos");
    }

    private String uploadFile(MultipartFile file, String type) {
        try {
            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // 按日期生成目录
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String relativePath = String.format("/%s/%s/%s", type, datePath, filename);
            
            // 创建目录
            Path uploadDir = Paths.get(uploadPath + "/" + type + "/" + datePath);
            Files.createDirectories(uploadDir);

            // 保存文件
            Path destPath = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), destPath);

            // 返回访问URL
            return accessUrl + relativePath;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }
} 