package com.oussama.socialmedia.authservice.Dto;


import lombok.*;
import org.springframework.lang.Nullable;

import java.net.URL;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    @Nullable
    private String about;
    @Nullable
    private Long phone;
    private String email;
    @Nullable
    private URL profilePicture;
    private String gender;
    private LocalDate birthday;
}
