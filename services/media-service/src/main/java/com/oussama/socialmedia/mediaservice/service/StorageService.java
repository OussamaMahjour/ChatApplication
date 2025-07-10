package com.oussama.socialmedia.mediaservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {
    void save(MultipartFile file, String key, String prefix) throws Exception;
    InputStream getInputStream(String key, String prefix, long offset, long length) throws Exception;
    Resource getResource(String key, String prefix) throws Exception;
    void deleteObjectByKey(String key) throws Exception;
}
