package org.mansar.employeemanagement.api;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.mansar.employeemanagement.dto.ApiError;
import org.mansar.employeemanagement.exception.AttributeAccessDeniedException;
import org.mansar.employeemanagement.exception.PermissionDeniedException;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorBuilder = new StringBuilder();
        for (FieldError fieldError: fieldErrors) {
            errorBuilder
                    .append(fieldError.getField()).append(":")
                    .append(fieldError.getDefaultMessage())
                    .append('\n');
        }
        return new ApiError(HttpStatus.BAD_REQUEST.value(), errorBuilder.toString());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessDeniedException(AccessDeniedException ex) {
        log.info(ex.getMessage(), ex);
        return new ApiError(HttpStatus.FORBIDDEN.value(), "Access Denied");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleExpiredJwtException(ExpiredJwtException ex) {
        log.info(ex.getMessage(), ex);
        return new ApiError(HttpStatus.FORBIDDEN.value(), "invalid token");
    }
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleRecordNotFoundException(RecordNotFoundException ex) {
      log.error(ex.getMessage(), ex);
      return new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handlePermissionDeniedException(PermissionDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(AttributeAccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleAttributeAccessDeniedException(AttributeAccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }
}
