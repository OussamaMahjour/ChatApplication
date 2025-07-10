package com.oussama.SocialMedia.user_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@Setter
@Getter
public class FileRequestDto {
    @Nullable
    private String context;
    private List<MultipartFile> medias;
}