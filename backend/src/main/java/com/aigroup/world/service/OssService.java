package com.aigroup.world.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件服务接口
 */
public interface OssService {
    /**
     * 上传文件到OSS
     * @param file 文件对象
     * @param directory 上传目录
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String directory);
    
    /**
     * 删除OSS文件
     * @param fileUrl 文件URL
     */
    void deleteFile(String fileUrl);
} 