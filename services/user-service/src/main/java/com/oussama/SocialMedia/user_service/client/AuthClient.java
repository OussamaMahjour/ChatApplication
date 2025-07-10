package com.oussama.SocialMedia.user_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="auth-service",path = "/api/v1/auth")
public interface AuthClient {
    @DeleteMapping("/{username}")
    void deleteUser(@PathVariable("username") String username);
}
