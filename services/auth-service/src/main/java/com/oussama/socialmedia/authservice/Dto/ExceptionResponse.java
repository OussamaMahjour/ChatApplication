package com.oussama.socialmedia.authservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExceptionResponse {
    private String error;
    private ExceptionType type;

}
