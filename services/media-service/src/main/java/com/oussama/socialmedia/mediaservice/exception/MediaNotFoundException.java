package com.oussama.socialmedia.mediaservice.exception;

public class MediaNotFoundException extends RuntimeException {
    public MediaNotFoundException(String mediaId) {
        super("Media with id " + mediaId + "not found");
    }
}
