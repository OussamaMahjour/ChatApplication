package com.oussama.socialmedia.mediaservice.service;

import com.oussama.socialmedia.mediaservice.dto.StaticFileResponse;
import com.oussama.socialmedia.mediaservice.dto.StreamFileResponse;
import com.oussama.socialmedia.mediaservice.entity.Range;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    String saveMedia(String context, MultipartFile multipartFile);
    StaticFileResponse getStaticMedia(String mediaId);
    void DeleteMediaById(String id);
    void DeleteContext(String context);
    boolean isMediaStreamable(String mediaId);
    StreamFileResponse fetchChunk(String mediaId, Range range);

}
