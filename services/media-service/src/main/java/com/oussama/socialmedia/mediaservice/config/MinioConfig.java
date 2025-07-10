package com.oussama.socialmedia.mediaservice.config;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    public static final String BUCKET_NAME = "media";

    @Value("${minio.url}")
    private String url;
    @Value("${minio.username}")
    private String username;
    @Value("${minio.password}")
    private String password;

    @Bean
    public MinioClient minioClient() throws  Exception{
        MinioClient client = MinioClient.builder()
                .endpoint(url)
                .credentials(username,password)
                .build();
        if(!client.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())){
            client.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(BUCKET_NAME)
                            .build()
            );
        }
        return client;

    }
}
