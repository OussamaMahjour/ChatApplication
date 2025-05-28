package com.oussama.SocialMedia.user_service.dto;

import com.oussama.SocialMedia.user_service.entity.Gender;
import lombok.*;
import org.springframework.lang.Nullable;

import java.net.URL;
import java.time.LocalDate;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDTO {

    private String username;
    @Nullable
    private String firstname;
    @Nullable
    private String lastname;
    @Nullable
    private String about;
    @Nullable
    private Long phone;
    @Nullable
    private String email;
    @Nullable
    private Gender gender;
    @Nullable
    private LocalDate birthday;
}
