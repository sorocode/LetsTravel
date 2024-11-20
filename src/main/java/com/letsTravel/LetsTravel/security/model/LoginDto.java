package com.letsTravel.LetsTravel.security.model;

import lombok.Data;

@Data
public class LoginDto {
    
    private String username;
    private String password;
    private UserRole role;
    
}
