package com.decagon.demo1.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.demo1.Repository.AdminRepository;
import com.decagon.demo1.Repository.ProductRepository;
import com.decagon.demo1.Repository.UserRepository;
import com.decagon.demo1.Roles;
import com.decagon.demo1.model.Product;
import com.decagon.demo1.model.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

@Component
public class CSVUtils {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private AdminRepository adminRepository;
    @Autowired
    public CSVUtils(UserRepository userRepository, ProductRepository productRepository, AdminRepository adminRepository){
        this.productRepository = productRepository;
        this.userRepository=userRepository;
        this.adminRepository= adminRepository;

    }
    @PostConstruct
    public  void readUserCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/mac/Downloads/demo1/src/main/java/com/decagon/demo1/users.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] user = line.split(",");
                if (lineOne) {
                    Users users = Users.builder()
                            .username(user[0])
                            .fullName(user[1])
                            .password(BCrypt.withDefaults().hashToString(12, user[2].trim().toCharArray()))
                            .role(user[3])
                            .balance(new BigDecimal(500000))
                            .build();
                    userRepository.save(users);

                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/mac/Downloads/demo1/src/main/java/com/decagon/demo1/admin.csv"))) {
            String line="";
            boolean lineOne = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] user = line.split(",");
                if (lineOne) {
                    Users users = Users.builder()
                            .username(user[0])
                            .fullName(user[1])
                            .password(BCrypt.withDefaults().hashToString(12, user[2].trim().toCharArray()))
                            .role(user[3])
                            .balance(new BigDecimal(500000))
                            .build();
                    adminRepository.save(users);

                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/mac/Downloads/demo1/src/main/java/com/decagon/demo1/products.csv"))){

               String line="";
                boolean lineOne= false;
                while (( line = bufferedReader.readLine())!= null){
                    String[]product = line.split(",");
                    if(lineOne){
                        Product product1= Product.builder()
                                .name(product[0])
                                .quantity(Long.parseLong(product[1]))
                                .category(product[2])
                                .price(new BigDecimal(product[3]))
                                .img(product[4])
                                .build();
                        productRepository.save(product1);
                    }
                    lineOne=true;


                }
            }catch(Exception exception){
                throw new RuntimeException(exception);
            }
            }

        }

