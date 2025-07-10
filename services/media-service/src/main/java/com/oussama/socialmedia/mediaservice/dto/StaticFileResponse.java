package com.oussama.socialmedia.mediaservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Builder
@Getter
@Setter
public class StaticFileResponse {
    private String id;
    private String context;
    private String httpContentType;
    private Resource resource;
}