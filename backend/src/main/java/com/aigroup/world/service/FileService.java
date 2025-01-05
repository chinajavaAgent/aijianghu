package com.aigroup.world.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传图片文件
     * @param file 图片文件
     * @return 图片URL
     */
    String uploadImage(MultipartFile file);

    /**
     * 上传视频文件
     * @param file 视频文件
     * @return 视频URL
     */
    String uploadVideo(MultipartFile file);
} 