package com.app.userservice.Mapper;

import com.app.userservice.DTO.ResponseDto;
import com.app.userservice.Entity.User;

public interface UserMapper {
   public ResponseDto userToResponseDto(User user);
   public User responseDtoToUser(ResponseDto responseDto);
}
