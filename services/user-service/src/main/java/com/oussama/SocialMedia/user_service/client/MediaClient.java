package com.oussama.SocialMedia.user_service.client;

import com.oussama.SocialMedia.user_service.dto.FileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="media-service",path = "/api/v1/media")
public interface MediaClient {
    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> saveMedia(@RequestBody  FileRequestDto fileRequestDto);

}