package com.decagon.demo1.userService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.demo1.Repository.AdminRepository;
import com.decagon.demo1.Repository.ProductRepository;
import com.decagon.demo1.Repository.UserRepository;
import com.decagon.demo1.dto.PasswordDto;
import com.decagon.demo1.model.Product;
import com.decagon.demo1.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserServiceImplementation {
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    @Autowired
    public UserServiceImplementation (UserRepository userRepository, AdminRepository adminRepository){
        this.userRepository= userRepository;
        this.adminRepository= adminRepository;


    }
   public Function<String, Users> findUserByUsername= (username)-> userRepository.findUserByUsername(username)
            .orElseThrow(()->new NullPointerException("user not found"));
public Function<Long, Users> findUserById =(id)->userRepository.findById(id).orElseThrow(()->new NullPointerException("User not found! "));
    public Function<Users ,Users> saveUser = (users)-> userRepository.save(users);


    public Function<PasswordDto, Boolean> verifyUser = passwordDto -> BCrypt.verifyer().verify(passwordDto.getPassword().toCharArray(),passwordDto.getHashPassword().
            toCharArray()).verified;
    public Function<String, Users> findUserByAdminUsername= (username)-> adminRepository.findUsersByUsername(username)
            .orElseThrow(()->new NullPointerException("user not found"));
    public Function<Long, Users> findUserByAdminId =(id)->adminRepository.findById(id).orElseThrow(()->new NullPointerException("User not found! "));

}
