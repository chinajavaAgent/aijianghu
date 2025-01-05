package com.aigroup.world.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import com.aigroup.world.security.JwtAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 添加cors过滤器
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            // 添加JWT过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // 禁用csrf
            .csrf().disable()
            // 基于token，不需要session
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 下面开始设置权限
            .authorizeRequests()
            // 设置允许访问的路径
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(
                "/api/users/register",
                "/api/users/login",
                "/api/users/check-phone"
            ).permitAll()
            // 允许未登录用户访问锦囊列表和详情
            .antMatchers(HttpMethod.GET, "/api/tips/**").permitAll()
            // swagger相关接口
            .antMatchers(
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/webjars/**"
            ).permitAll()
            // 其他所有请求需要认证
            .anyRequest().authenticated();
    }
} 