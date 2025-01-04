package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("Hello, AI群江湖!");
    }
} 