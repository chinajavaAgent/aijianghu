package com.aigroup.world.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 添加cors过滤器
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            // 禁用csrf
            .csrf().disable()
            // 基于token，不需要session
            .sessionManagement().disable()
            // 下面开始设置权限
            .authorizeRequests()
            // 设置允许访问的路径
            .antMatchers(
                "/api/users/register",
                "/api/users/login",
                "/api/users/check-phone"
            ).permitAll()
            // 其他所有请求需要认证
            .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 