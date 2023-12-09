package com.decagon.demo1.controller;

import com.decagon.demo1.dto.PasswordDto;
import com.decagon.demo1.dto.UserDto;
import com.decagon.demo1.model.Users;
import com.decagon.demo1.userService.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@Slf4j
@RequestMapping("/user" )
public class UserController {
    private UserServiceImplementation userServiceImplementation;
    @Autowired
    public UserController(UserServiceImplementation userServiceImplementation){
        this.userServiceImplementation= userServiceImplementation;
    }
    @GetMapping("/sign-up")
public String signUpPage(Model model){
        model.addAttribute("user",new UserDto());
        return "sign-up";
}
@GetMapping("/login")
public ModelAndView logInPage(){
        return new ModelAndView("login")
                .addObject("user", new UserDto());

}
@PostMapping("/sign-up")
public String  signUp(@ModelAttribute UserDto userDto){
        Users users =userServiceImplementation.saveUser.apply(new Users(userDto));
    System.out.println("user details----> {}"+ users);
        return "successful-register";
}
@PostMapping("/login")
public String loginUser(@ModelAttribute UserDto userDto, HttpServletRequest request, Model model ){
        Users users = userServiceImplementation.findUserByUsername.apply(userDto.getUsername());

        log.info("user's info --> {}", users);
        if(userServiceImplementation.verifyUser.apply(PasswordDto.builder()
                .password(userDto.getPassword())
                .hashPassword(users.getPassword()
                        ).build())){
            HttpSession session= request.getSession();
            session.setAttribute("userID",users.getId());
            return "redirect:/products/all";
        }
return "redirect:/user/login";
}

    @PostMapping("/admin-sign-up")
    public String  signAdminUp(@ModelAttribute UserDto userDto){
        Users users =userServiceImplementation.saveUser.apply(new Users(userDto));
        System.out.println("user details----> {}"+ users);
        return "admin-successful-register";
    }

    @GetMapping("/admin-sign-up")
    public String signUpAdminPage(Model model){
        model.addAttribute("user",new UserDto());
        return "admin-signup";
    }




    @GetMapping("/admin-login")
    public ModelAndView AdminLogInPage(){
        return new ModelAndView("admin-login")
                .addObject("user", new UserDto());

    }
    @PostMapping("/admin-login")
    public String loginAdminUser(@ModelAttribute UserDto userDto, HttpServletRequest request, Model model ){
        Users users =userServiceImplementation.findUserByAdminUsername.apply(userDto.getUsername());
        log.info("admin's info --> {}", users);
        if(userServiceImplementation.verifyUser.apply(PasswordDto.builder()
                .password(userDto.getPassword())
                .hashPassword(users.getPassword()
                ).build())&& Objects.equals(users.getRole(), "admin")){
            HttpSession session= request.getSession();
            session.setAttribute("userID",users.getId());
            return "redirect:/products/admin-view";
        }
        return "redirect:/user/admin-login";
    }





@GetMapping("/logout")
    public String logout (HttpSession session){
        session.invalidate();
        return "index";
}



}
