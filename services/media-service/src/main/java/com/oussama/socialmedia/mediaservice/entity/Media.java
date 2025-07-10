package com.oussama.socialmedia.mediaservice.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "medias")
@Builder
public class Media {
    @Id
    String id;
    ContextType contextType;
    String context;
    String httpContentType;
    long size;
}
