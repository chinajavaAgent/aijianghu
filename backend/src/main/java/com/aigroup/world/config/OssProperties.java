package com.aigroup.world.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    /**
     * OSS endpoint
     */
    private String endpoint;

    /**
     * OSS access key
     */
    private String accessKeyId;

    /**
     * OSS secret key
     */
    private String accessKeySecret;

    /**
     * OSS bucket name
     */
    private String bucketName;

    /**
     * 基础目录
     */
    private String baseDir = "upload/";

    /**
     * 最大文件大小（默认10MB）
     */
    private long maxSize = 10 * 1024 * 1024;
} 