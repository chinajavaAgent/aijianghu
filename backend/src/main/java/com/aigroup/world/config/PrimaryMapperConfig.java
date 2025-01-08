package com.aigroup.world.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 主数据源Mapper扫描配置
 */
@Configuration
@MapperScan(basePackages = "com.aigroup.world.mapper", 
        sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryMapperConfig {
} 