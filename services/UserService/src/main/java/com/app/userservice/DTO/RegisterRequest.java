package com.app.userservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private LocalDateTime deletedAt; // null means not deleted
}
