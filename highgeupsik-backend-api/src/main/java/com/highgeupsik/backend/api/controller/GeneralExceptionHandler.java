package com.highgeupsik.backend.api.controller;

import static com.highgeupsik.backend.api.controller.ApiUtils.error;
import static org.springframework.http.HttpStatus.*;

import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.exception.TokenException;
import com.highgeupsik.backend.core.exception.WriterException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler
    public ApiResult<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        return error(new ApiError("NOT FOUND", e.getMessage()));
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler
    public ApiResult<ApiError> handleWriterException(WriterException e) {
        return error(new ApiError("FORBIDDEN", e.getMessage()));
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler
    public ApiResult<ApiError> handleTokenException(TokenException e) {
        return error(new ApiError("UNAUTHORIZED", e.getMessage()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ApiResult<ApiError> handleValidException(BindException e) {
        List<String> messages = e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        return error(new ApiError("BAD", messages.toString()));
    }
}
