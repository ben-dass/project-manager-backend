package org.theonlydodo.backend.exception;

public class ApiException extends RuntimeException {
    public ApiException( String message ) {
        super( message );
    }
}
