package com.oussama.socialmedia.authservice.client;


import com.oussama.socialmedia.authservice.Dto.RegisterUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="user-service",path = "/api/v1/users")
public interface UserClient {
    @PostMapping
     void saveUser(RegisterUserDto registerUserDto);

}
