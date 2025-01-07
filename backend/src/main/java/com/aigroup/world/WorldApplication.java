package com.aigroup.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot 应用程序启动类
 * 
 * @author AI Group
 * @since 2024-01-06
 */
@SpringBootApplication
@MapperScan("com.aigroup.world.mapper")
@EnableSwagger2
public class WorldApplication {
    
    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WorldApplication.class, args);
    }
} 