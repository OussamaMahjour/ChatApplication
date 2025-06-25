package com.oussama.SocialMedia.user_service.controller;


import com.oussama.SocialMedia.user_service.dto.FileRequestDto;
import com.oussama.SocialMedia.user_service.dto.UserRequestDTO;
import com.oussama.SocialMedia.user_service.dto.UserResponseDTO;
import com.oussama.SocialMedia.user_service.service.ServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class Controller {
    private ServiceInterface userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(
                userService.getUserByUsername(username)
        );
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserFromHeader(@RequestHeader("username") String username) {
        return ResponseEntity.ok(
                userService.getUserByUsername(username)
        );
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {

        return ResponseEntity.ok(
                userService.createUser(userRequestDTO)
        );
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO,@RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(
            userService.updateUser(userRequestDTO)
                );
    }

    @PutMapping("/{username}/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(@PathVariable String username , @ModelAttribute FileRequestDto fileRequestDto){
        return ResponseEntity.ok(userService.updateProfile(username,fileRequestDto.getMedias()));
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestHeader("username") String username) {
        userService.softDeleteUser(username);

        return ResponseEntity.ok().build();
    }

}
