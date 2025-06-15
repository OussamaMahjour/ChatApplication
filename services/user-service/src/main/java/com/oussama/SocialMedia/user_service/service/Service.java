package com.oussama.SocialMedia.user_service.service;


import com.oussama.SocialMedia.user_service.client.ChatClient;
import com.oussama.SocialMedia.user_service.dto.UserRequestDTO;
import com.oussama.SocialMedia.user_service.dto.UserResponseDTO;
import com.oussama.SocialMedia.user_service.entity.User;
import com.oussama.SocialMedia.user_service.mapper.MapperInterface;
import com.oussama.SocialMedia.user_service.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class Service implements ServiceInterface {
    public Repository repository;
    public MapperInterface mapper;

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
    public void hardDeleteUsers() {
        List<User> users = repository.findAllUserScheduledToBeDeleted();
        System.out.println("deleting users");
        repository.deleteAll(users);

    }
    @Override
    public void softDeleteUser(UserRequestDTO userRequestDTO) {
        User user = repository.findUserByUsername(userRequestDTO.getUsername());
        if(user!= null){
            user.setScheduledToBeDeletedAt(
                    new Date(System.currentTimeMillis())
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .plusSeconds(30)
            );
            repository.save(user);
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
}
