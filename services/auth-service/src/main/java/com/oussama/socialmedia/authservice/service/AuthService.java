package com.oussama.socialmedia.authservice.service;


import com.oussama.socialmedia.authservice.Dto.LoginRequestDto;
import com.oussama.socialmedia.authservice.Dto.RegisterUserDto;
import com.oussama.socialmedia.authservice.client.UserClient;
import com.oussama.socialmedia.authservice.entity.User;
import com.oussama.socialmedia.authservice.exception.InvalidCredentialException;
import com.oussama.socialmedia.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserClient userClient;
    public User authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return userRepository.findByUsername(username).orElseThrow();
    }


    @Transactional
    public User signUp(RegisterUserDto registerUserDto) {
        userClient.saveUser(registerUserDto);
        User user = User.builder()
                .username(registerUserDto.getUsername())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .email(registerUserDto.getEmail())
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }
}
