package com.oussama.SocialMedia.user_service.service;


import com.oussama.SocialMedia.user_service.client.ChatClient;
import com.oussama.SocialMedia.user_service.client.MediaClient;
import com.oussama.SocialMedia.user_service.dto.FileRequestDto;
import com.oussama.SocialMedia.user_service.dto.UserRequestDTO;
import com.oussama.SocialMedia.user_service.dto.UserResponseDTO;
import com.oussama.SocialMedia.user_service.entity.User;
import com.oussama.SocialMedia.user_service.mapper.MapperInterface;
import com.oussama.SocialMedia.user_service.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class Service implements ServiceInterface {
    public Repository repository;
    public MapperInterface mapper;
    public MediaClient mediaClient;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return repository.findAll().stream().map(mapper::UserToUserResponseDTO).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return mapper.UserToUserResponseDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = mapper.UserRequestDTOToUser(userRequestDTO);
        System.out.println(user);
        return mapper.UserToUserResponseDTO(repository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        User oldUser = repository.findUserByUsername(userRequestDTO.getUsername());
        User newUser = mapper.UserRequestDTOToUser(userRequestDTO);
        newUser.setId(oldUser.getId());
        newUser.setCreatedAt(oldUser.getCreatedAt());
        repository.save(newUser);
        return mapper.UserToUserResponseDTO(newUser);
    }


    @Override
    @Transactional
    public void softDeleteUser(String username) {
        User user = repository.findUserByUsername(username);

        if(user!= null){
            user.setDeleted(true);
            user.setDeletedAt(LocalDateTime.now());
            repository.save(user);
            System.out.println("Test1");

        }
        else{
            System.out.println("user not found to delete");
        }


    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return mapper.UserToUserResponseDTO(repository.findUserByEmail(email));
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        return mapper.UserToUserResponseDTO(repository.findUserByUsername(username));
    }

    @Override
    public UserResponseDTO updateProfile(String username, List<MultipartFile> files){
        User user = repository.findUserByUsername(username);
        if(user != null){

            List<String> pfpId = mediaClient.saveMedia(
                    FileRequestDto.builder()
                            .medias(files)
                            .context("PROFILE").build()
            );

            user.setProfilePicture(pfpId.get(0));

            repository.save(user);
        }
        return mapper.UserToUserResponseDTO(user);

    }
}
