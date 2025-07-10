package com.oussama.socialmedia.mediaservice.repository;

import com.oussama.socialmedia.mediaservice.entity.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MediaRepository extends MongoRepository<Media,String> {
    List<Media> findAllByContext(String context);
}
