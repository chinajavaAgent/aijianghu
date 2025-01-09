package com.aigroup.world.controller;

import com.aigroup.world.utils.ShareLinkUtil;
import com.aigroup.world.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aiGroup/share")
public class ShareController {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private ShareLinkUtil shareLinkUtil;

    /**
     * 加密用户ID
     * @param userId 用户ID
     * @return 加密后的token
     */
    @GetMapping("/encrypt/{userId}")
    public Result<String> encryptUserId(@PathVariable Long userId) {
        logger.info("Encrypting userId: {}", userId);
        try {
            String encryptedId = shareLinkUtil.encryptUserId(userId);
            logger.info("Successfully encrypted userId: {}, result: {}", userId, encryptedId);
            return Result.success(encryptedId);
        } catch (Exception e) {
            logger.error("Failed to encrypt userId: {}", userId, e);
            return Result.error("加密失败：" + e.getMessage());
        }
    }

    /**
     * 解密用户ID
     * @param token 加密后的token
     * @return 解密后的用户ID
     */
    @GetMapping("/decrypt/{token}")
    public Result<Long> decryptUserId(@PathVariable String token) {
        logger.info("Decrypting token: {}", token);
        try {
            Long userId = shareLinkUtil.decryptUserId(token);
            logger.info("Successfully decrypted token: {}, userId: {}", token, userId);
            return Result.success(userId);
        } catch (Exception e) {
            logger.error("Failed to decrypt token: {}", token, e);
            return Result.error("解密失败：" + e.getMessage());
        }
    }

    /**
     * 测试接口是否可访问
     */
    @GetMapping("/test")
    public Result<String> test() {
        logger.info("Share controller test endpoint accessed");
        return Result.success("Share controller is working!");
    }
} 