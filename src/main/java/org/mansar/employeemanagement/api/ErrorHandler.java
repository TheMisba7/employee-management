package org.mansar.employeemanagement.api;

import lombok.extern.slf4j.Slf4j;
import org.mansar.employeemanagement.dto.ApiError;
import org.mansar.employeemanagement.exception.AttributeAccessDeniedException;
import org.mansar.employeemanagement.exception.PermissionDeniedException;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


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

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }
}
