package com.app.userservice.Mapper;

import com.app.userservice.DTO.ResponseDto;
import com.app.userservice.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class MapperImplementation implements UserMapper{
    @Override
    public ResponseDto userToResponseDto(User user){
        return ResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .deletedAt(user.getDeletedAt())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .username(user.getUsername()).build();
    }
    @Override
    public User responseDtoToUser(ResponseDto responseDto){
        return User.builder()
                .id(responseDto.getId())
                .email(responseDto.getEmail())
                .firstName(responseDto.getFirstName())
                .lastName(responseDto.getLastName())
                .deletedAt(responseDto.getDeletedAt())
                .username(responseDto.getUsername())
                .password(responseDto.getPassword())
                .build();
    }
}
