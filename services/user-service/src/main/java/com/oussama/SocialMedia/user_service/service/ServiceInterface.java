package com.oussama.SocialMedia.user_service.service;


import com.oussama.SocialMedia.user_service.dto.UserRequestDTO;
import com.oussama.SocialMedia.user_service.dto.UserResponseDTO;

import java.util.List;

public interface ServiceInterface {
    public List<UserResponseDTO> getAllUsers();
    public UserResponseDTO getUserById(Long id);
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO);
    public void softDeleteUser(UserRequestDTO userRequestDTO);
    public void hardDeleteUsers();
    public UserResponseDTO getUserByEmail(String email);
    public UserResponseDTO getUserByUsername(String username);


}
