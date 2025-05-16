package com.app.userservice.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime deletedAt; // null means not deleted

}
