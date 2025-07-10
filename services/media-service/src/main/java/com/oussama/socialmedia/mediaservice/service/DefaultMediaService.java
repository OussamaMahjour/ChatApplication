package com.oussama.socialmedia.mediaservice.service;


import com.oussama.socialmedia.mediaservice.dto.StaticFileResponse;
import com.oussama.socialmedia.mediaservice.dto.StreamFileResponse;
import com.oussama.socialmedia.mediaservice.entity.Media;
import com.oussama.socialmedia.mediaservice.entity.Range;
import com.oussama.socialmedia.mediaservice.exception.ContextNotFoundException;
import com.oussama.socialmedia.mediaservice.exception.FIleStorageException;
import com.oussama.socialmedia.mediaservice.exception.MediaNotFoundException;
import com.oussama.socialmedia.mediaservice.repository.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class DefaultMediaService implements MediaService {

    private MediaRepository mediaRepository;
    private StorageService storageService;


    @Override
    @Transactional
    public String saveMedia(String context, MultipartFile multipartFile) {
        UUID id = UUID.randomUUID();
        try {
            storageService.save(multipartFile,id.toString(),context);
        } catch (Exception ex) {
            throw new FIleStorageException("Could not store file " + id + ". Please try again!");
        }
        Media media = Media.builder()
                .id(id.toString())
                .context(context)
                .size(multipartFile.getSize())
                .httpContentType(multipartFile.getContentType())
                .build();
        mediaRepository.save(media);
        return media.getId();
    }

    @Override
    public StaticFileResponse getStaticMedia(String mediaId) {

        Media media = mediaRepository.findById(mediaId).orElse(null);
        if (media == null) throw new MediaNotFoundException(mediaId);

        try{
            return StaticFileResponse.builder()
                    .resource(storageService.getResource(mediaId,media.getContext()))
                    .context(media.getContext())
                    .httpContentType(media.getHttpContentType())
                    .id(media.getId())
                    .build();

        } catch (Exception e) {
            throw new FIleStorageException("Could not load image " + media.getId() + ". Please try again!");
        }

    }

    @Override
    @Transactional
    public void DeleteMediaById(String id) {
        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) throw new MediaNotFoundException(id);
        mediaRepository.deleteById(id);
        try {
            storageService.deleteObjectByKey(media.getContext()+"/"+media.getId());
        } catch (Exception e) {
            throw new FIleStorageException("Failed to delete file "+ id);
        }

    }

    @Override
    @Transactional
    public void DeleteContext(String context) {
        List<Media> medias = mediaRepository.findAllByContext(context);
        if(medias.isEmpty()) throw new ContextNotFoundException(context);
        medias.forEach(media -> {
            mediaRepository.deleteById(media.getId());
            try {
                storageService.deleteObjectByKey(media.getContext()+"/"+media.getId());
            } catch (Exception e) {
                throw new FIleStorageException("Couldn't delete File "+media.getId());
            }
        });
    }

    @Override
    public boolean isMediaStreamable(String mediaId){
            Media  media = mediaRepository.findById(mediaId).orElse(null);
            if(media == null) throw new MediaNotFoundException(mediaId);
            return !media.getHttpContentType().startsWith("image");
    }

    @Override
    public StreamFileResponse fetchChunk(String mediaId, Range range){
        Media  media = mediaRepository.findById(mediaId).orElse(null);
        if(media == null) throw new MediaNotFoundException(mediaId);
        long startPosition = range.getStart();
        long endPosition = range.getRangeEnd(media.getSize());
        int chunkSize = (int) (endPosition - startPosition + 1);
        try(InputStream inputStream = storageService.getInputStream(mediaId,media.getContext(),startPosition,chunkSize)){
                return StreamFileResponse.builder()
                        .size(media.getSize())
                        .httpContentType(media.getHttpContentType())
                        .chunk(inputStream.readAllBytes())
                        .build();
        }catch (Exception exception) {
            log.error("Exception occurred when trying to read file with ID = {}", mediaId);
            throw new FIleStorageException(exception.getMessage());
        }

    }

}

