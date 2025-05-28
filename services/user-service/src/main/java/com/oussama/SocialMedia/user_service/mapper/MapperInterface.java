package com.oussama.SocialMedia.user_service.mapper;

import com.oussama.SocialMedia.user_service.dto.UserRequestDTO;
import com.oussama.SocialMedia.user_service.dto.UserResponseDTO;
import com.oussama.SocialMedia.user_service.entity.User;

public interface MapperInterface {
        public User UserRequestDTOToUser(UserRequestDTO userRequestDTO);
        public UserResponseDTO UserToUserResponseDTO(User user);

}
