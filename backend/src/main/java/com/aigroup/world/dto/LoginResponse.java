package com.aigroup.world.dto;

import com.aigroup.world.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private User user;

    public static LoginResponse of(String token, User user) {
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(user);
        return response;
    }
} 