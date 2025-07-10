package com.oussama.socialmedia.mediaservice.controller;


import com.oussama.socialmedia.mediaservice.dto.FileRequestDto;
import com.oussama.socialmedia.mediaservice.dto.StaticFileResponse;
import com.oussama.socialmedia.mediaservice.dto.StreamFileResponse;
import com.oussama.socialmedia.mediaservice.entity.Range;
import com.oussama.socialmedia.mediaservice.exception.ContextNotFoundException;
import com.oussama.socialmedia.mediaservice.exception.MediaNotFoundException;
import com.oussama.socialmedia.mediaservice.service.MediaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

@Slf4j
@RestController
@RequestMapping("api/v1/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @Value("${media.streaming.default-chunk-size}")
    public Integer defaultChunkSize;

    @PostMapping
    public ResponseEntity<List<String>> saveMedias(@ModelAttribute FileRequestDto requestDto) {
            List<String> MediaIds = new ArrayList<>();
            requestDto.getMedias().forEach(
                    media ->MediaIds.add(
                            mediaService.saveMedia(
                                    requestDto.getContext(),
                                    media
                            )
                    )
            );
            return ResponseEntity.ok(MediaIds);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedia(@PathVariable String id, HttpServletRequest request) {
        try {
            if(!mediaService.isMediaStreamable(id)){

                    StaticFileResponse mediaStaticResponse = mediaService.getStaticMedia(id);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, mediaStaticResponse.getHttpContentType())
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaStaticResponse.getResource().getFilename() + "\"")
                            .body(mediaStaticResponse.getResource());

            }
            String range = request.getHeader(HttpHeaders.RANGE);
            Range parsedRange = Range.parseHttpRangeString(range,defaultChunkSize);
            StreamFileResponse mediaStreamResponse = mediaService.fetchChunk(id, parsedRange);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(CONTENT_TYPE,mediaStreamResponse.getHttpContentType())
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(
                            HttpHeaders.CONTENT_LENGTH,
                            String.valueOf(
                                    parsedRange.getRangeEnd(mediaStreamResponse.getSize() - parsedRange.getStart() +1
                                    )
                            )
                    )
                    .header(
                            HttpHeaders.CONTENT_RANGE,
                            "bytes "+parsedRange.getStart() +
                                    "-" + parsedRange.getRangeEnd(mediaStreamResponse.getSize() )+
                                    "/" + mediaStreamResponse.getSize()
                    )
                    .body(mediaStreamResponse.getChunk());
        }catch (MediaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedia(@PathVariable String id) {
        try{
            mediaService.DeleteMediaById(id);
        }catch (MediaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/context/{context}")
    public ResponseEntity<Object> deleteContext(@PathVariable String context) {
        try{
            mediaService.DeleteContext(context);
        }catch (ContextNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
