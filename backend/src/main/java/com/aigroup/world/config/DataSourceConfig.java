package com.aigroup.world.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    // 使用dynamic-datasource-spring-boot-starter的自动配置，这里不需要额外的配置
} 