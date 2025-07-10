package com.oussama.socialmedia.mediaservice.service;

import com.oussama.socialmedia.mediaservice.config.MinioConfig;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Component
@RequiredArgsConstructor
public class MinioService implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.put-object-part-size}")
    private Long putObjectPartSize;

    public void save(MultipartFile file, String key,String prefix) throws Exception {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(MinioConfig.BUCKET_NAME)
                        .object(prefix+"/"+key)
                        .stream(file.getInputStream(), file.getSize(), putObjectPartSize)
                        .build()
        );
    }

    public InputStream getInputStream(String key,String prefix, long offset, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(MinioConfig.BUCKET_NAME)
                        .offset(offset)
                        .length(length)
                        .object(prefix+"/"+key)
                        .build());
    }

    public Resource getResource(String key,String prefix) throws Exception {
        return new InputStreamResource(minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(MinioConfig.BUCKET_NAME)
                        .object(prefix+"/"+key)
                        .build()
        ));
    }

    public void deleteObjectByKey(String key) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(MinioConfig.BUCKET_NAME)
                        .object(key)
                        .build()
        );
    }



}