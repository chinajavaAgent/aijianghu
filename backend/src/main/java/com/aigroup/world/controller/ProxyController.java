package com.aigroup.world.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {
    private static final Logger logger = LoggerFactory.getLogger(ProxyController.class);
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    private final RestTemplate restTemplate;

    public ProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> proxyImage(@RequestParam("url") String imageUrl) {
        logger.info("Proxying image request for URL: {}", imageUrl);
        
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // 获取图片数据
            ResponseEntity<byte[]> response = restTemplate.exchange(
                imageUrl,
                HttpMethod.GET,
                entity,
                byte[].class
            );
            
            // 获取原始内容类型
            MediaType contentType = response.getHeaders().getContentType();
            if (contentType == null) {
                contentType = MediaType.IMAGE_JPEG;
            }
            
            // 验证内容类型
            if (!ALLOWED_CONTENT_TYPES.contains(contentType.toString())) {
                logger.warn("Unsupported content type: {}", contentType);
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }
            
            // 设置响应头
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(contentType);
            responseHeaders.setCacheControl(CacheControl.maxAge(java.time.Duration.ofHours(24)));
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            
            logger.info("Successfully proxied image, content type: {}, size: {} bytes", 
                    contentType, 
                    response.getBody() != null ? response.getBody().length : 0);
            
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(response.getBody());
                    
        } catch (Exception e) {
            logger.error("Error proxying image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }
    }
} 