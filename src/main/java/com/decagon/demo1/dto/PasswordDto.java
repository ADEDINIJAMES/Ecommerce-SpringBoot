package com.decagon.demo1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordDto {
    private String password ;
    private String hashPassword;
}
