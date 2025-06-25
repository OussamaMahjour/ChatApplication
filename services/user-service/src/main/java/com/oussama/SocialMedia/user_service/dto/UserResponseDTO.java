package com.oussama.SocialMedia.user_service.dto;

import com.oussama.SocialMedia.user_service.entity.Gender;
import lombok.*;

import java.net.URL;
import java.time.LocalDate;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO
{
        private String username;
        private String firstname;
        private String lastname;
        private String about;
        private Long phone;
        private String email;
        private String profilePicture;
        private Gender gender;
        private LocalDate birthday;
        private Boolean deleted;

}
