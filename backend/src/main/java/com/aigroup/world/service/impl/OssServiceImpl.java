package com.aigroup.world.service.impl;

import com.aigroup.world.config.OssProperties;
import com.aigroup.world.service.OssService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * OSS文件服务实现类
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    private final OSS ossClient;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    public OssServiceImpl(OSS ossClient) {
        this.ossClient = ossClient;
    }

    @Override
    public String uploadFile(MultipartFile file, String directory) {
        // 检查文件大小
        if (file.getSize() > ossProperties.getMaxSize()) {
            throw new RuntimeException("文件大小超过限制");
        }

        try {
            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

            // 生成文件路径（按年月日分目录）
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String fileKey = ossProperties.getBaseDir() + directory + "/" + datePath + "/" + fileName;

            // 设置文件元信息
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // 上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                ossProperties.getBucketName(),
                fileKey,
                file.getInputStream(),
                metadata
            );
            ossClient.putObject(putObjectRequest);

            // 返回文件访问URL
            return "https://" + ossProperties.getBucketName() + "." + ossProperties.getEndpoint() + "/" + fileKey;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败");
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // 从URL中提取文件路径
            String fileKey = fileUrl.substring(fileUrl.indexOf("/", 8) + 1);
            // 删除文件
            ossClient.deleteObject(ossProperties.getBucketName(), fileKey);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            throw new RuntimeException("文件删除失败");
        }
    }
} 