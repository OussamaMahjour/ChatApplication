package com.oussama.socialmedia.mediaservice.exception;

public class ContextNotFoundException extends RuntimeException {
    public ContextNotFoundException(String context) {
        super("Could not find context " + context);
    }
}
