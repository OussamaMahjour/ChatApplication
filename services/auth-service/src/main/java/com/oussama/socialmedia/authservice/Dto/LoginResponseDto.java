package com.oussama.socialmedia.authservice.Dto;


import com.oussama.socialmedia.authservice.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter
@Getter
public class LoginResponseDto {
    private String token;
    private long expiresIn;
    private User user;
}
