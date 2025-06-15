package com.oussama.SocialMedia.user_service.mapper;


import com.oussama.SocialMedia.user_service.dto.UserRequestDTO;
import com.oussama.SocialMedia.user_service.dto.UserResponseDTO;
import com.oussama.SocialMedia.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper implements MapperInterface{
    @Override
    public User UserRequestDTOToUser(UserRequestDTO userRequestDTO) {
        if(userRequestDTO==null) {
            return null;
        }
        return  User.builder()
                .username(userRequestDTO.getUsername())
                .firstname(userRequestDTO.getFirstname())
                .lastname(userRequestDTO.getLastname())
                .email(userRequestDTO.getEmail())
                .about(userRequestDTO.getAbout())
                .gender(userRequestDTO.getGender())
                .birthday(userRequestDTO.getBirthday())
                .phone(userRequestDTO.getPhone())
                .build();
    }

    @Override
    public UserResponseDTO UserToUserResponseDTO(User user) {
            if(user==null) {
                return null;
            }
            return UserResponseDTO.builder()
                    .username(user.getUsername())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .about(user.getAbout())
                    .gender(user.getGender())
                    .birthday(user.getBirthday())
                    .phone(user.getPhone())
                    .profilePicture(user.getProfilePicture())
                    .build();


    }
}
