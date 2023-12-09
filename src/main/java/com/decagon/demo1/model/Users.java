package com.decagon.demo1.model;

import com.decagon.demo1.Roles;
import com.decagon.demo1.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String fullName;
    private String password;
    private String role;
    private BigDecimal balance;



    public Users(UserDto signedUpUser) {
    this. username= signedUpUser.getUsername();
    this.fullName = signedUpUser.getFullName();
    this.password=  BCrypt.withDefaults()
            .hashToString(12, signedUpUser.getPassword().toCharArray());
    this.role= signedUpUser.getRole();


    }

}
