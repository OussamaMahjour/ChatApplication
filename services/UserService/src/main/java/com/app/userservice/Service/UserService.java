package com.app.userservice.Service;


import com.app.userservice.Config.PasswordUtil;
import com.app.userservice.DTO.LoginRequest;
import com.app.userservice.DTO.ResponseDto;
import com.app.userservice.Entity.User;
import com.app.userservice.Mapper.UserMapper;
import com.app.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public List<ResponseDto> getAllUsers (){
        List<User> usersFromDb =  userRepository.findAll();
        List<ResponseDto> users = new ArrayList<>();
        for (User user : usersFromDb) {
            users.add(userMapper.userToResponseDto(user));
        }
        return users;
    }
    public Optional<ResponseDto> getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return Optional.ofNullable(userMapper.userToResponseDto(user));
    }

    public User registerUser(User user) {
        String hashed = PasswordUtil.hash(user.getPassword());
        user.setPassword(hashed);

        return userRepository.save(user);
    }

    public boolean login(LoginRequest loginRequest){//String raw, String storedHash) {

        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            return false;
        }
        return PasswordUtil.verify(loginRequest.getPassword(), user.get().getPassword());
    }

}
