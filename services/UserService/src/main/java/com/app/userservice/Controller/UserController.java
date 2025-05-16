package com.app.userservice.Controller;

import com.app.userservice.DTO.LoginRequest;
import com.app.userservice.DTO.RegisterRequest;
import com.app.userservice.DTO.ResponseDto;
import com.app.userservice.Entity.User;
import com.app.userservice.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/all")
    public List<ResponseDto> allUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest){
        if (userService.login(loginRequest)){
            return "User Logged In Successfully";
        }else {
            return "User Logged In Failed";
        }
    }
    @PostMapping("/register")
    public User register(@Valid @RequestBody User registerRequest){
        return userService.registerUser(registerRequest);
    }
    @GetMapping("/user/{id}")
    public  String userProfile(@PathVariable("id") int id){
        return "userProfile "+"id: "+id;
    }




}
