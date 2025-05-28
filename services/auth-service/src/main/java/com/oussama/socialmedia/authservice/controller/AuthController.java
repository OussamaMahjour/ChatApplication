package com.oussama.socialmedia.authservice.controller;


import com.oussama.socialmedia.authservice.Dto.LoginRequestDto;
import com.oussama.socialmedia.authservice.Dto.LoginResponseDto;
import com.oussama.socialmedia.authservice.Dto.RegisterUserDto;
import com.oussama.socialmedia.authservice.entity.User;
import com.oussama.socialmedia.authservice.service.AuthService;
import com.oussama.socialmedia.authservice.service.JwtService;
import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        User user  = authService.authenticate(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        String token = jwtService.generateToken(user);
        LoginResponseDto loginResponseDto = LoginResponseDto
                .builder()
                .token(token)
                .expiresIn(System.currentTimeMillis()+jwtService.getExpirationTime())

                .build();

        return ResponseEntity.ok(loginResponseDto);

    }
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody  RegisterUserDto registerUserDto) {
        User user = authService.signUp(registerUserDto);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        authService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@FormParam("username") String username,@FormParam("password") String password){
        User user  = authService.authenticate(username, password);
        String token = jwtService.generateToken(user);
        LoginResponseDto loginResponseDto = LoginResponseDto
                .builder()
                .token(token)
                .expiresIn(System.currentTimeMillis()+jwtService.getExpirationTime())

                .build();

        return ResponseEntity.ok(loginResponseDto);
    }


}