package com.aigroup.world.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    /**
     * 主数据源SqlSessionFactory配置
     */
    @Primary
    @Bean("primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(
            @Qualifier("primaryDataSource") DataSource dataSource,
            MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        // 设置MyBatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        bean.setConfiguration(configuration);
        
        // 设置Mapper位置
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        if (resources != null && resources.length > 0) {
            bean.setMapperLocations(resources);
        }
        
        // 设置实体类包路径
        bean.setTypeAliasesPackage("com.aigroup.world.model");
        
        // 设置插件
        bean.setPlugins(mybatisPlusInterceptor);
        
        return bean.getObject();
    }

    /**
     * 第二数据源SqlSessionFactory配置
     */
    @Bean("secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(
            @Qualifier("secondaryDataSource") DataSource dataSource,
            MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        // 设置MyBatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        bean.setConfiguration(configuration);
        
        // 设置Mapper位置
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/secondary/*.xml");
        if (resources != null && resources.length > 0) {
            bean.setMapperLocations(resources);
        }
        
        // 设置实体类包路径
        bean.setTypeAliasesPackage("com.aigroup.world.entity");
        
        // 设置插件
        bean.setPlugins(mybatisPlusInterceptor);
        
        return bean.getObject();
    }

    /**
     * 主数据源SqlSessionTemplate配置
     */
    @Primary
    @Bean("primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 第二数据源SqlSessionTemplate配置
     */
    @Bean("secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
} 