package com.decagon.demo1.dto;

import com.decagon.demo1.Roles;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto {
    private String id;
    private String username;
    private String fullName;
    private String password;
    private String hashPassword;
    private String role;


}
