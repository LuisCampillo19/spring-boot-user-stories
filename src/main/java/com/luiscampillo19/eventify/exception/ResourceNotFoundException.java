package com.luiscampillo19.eventify.exception;

/**
 * Excepción lanzada cuando un recurso no existe en la base de datos.
 * Mapeada a HTTP 404 Not Found por el GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
