package com.oussama.socialmedia.mediaservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@Setter
@Getter
public class FileRequestDto {
    private String context;
    private List<MultipartFile> medias;
}
