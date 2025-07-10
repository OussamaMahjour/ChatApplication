package com.oussama.socialmedia.mediaservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



/**
 *
 *
 * */
@Builder
@Getter
@Setter
public class StreamFileResponse {
    private String id;
    private String context;
    private long size;
    private String httpContentType;
    private byte[] chunk;
}
