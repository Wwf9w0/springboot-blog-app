package com.blog.app.advice;

import com.blog.app.advice.exception.UserNotFoundException;
import com.blog.app.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(constructError(e.getCode(), e.getMessage()));
    }

    private Error constructError(final int code, final String message){
        return Error.builder()
                .code(code)
                .message(message)
                .timestamp(new DateTime().getMillis())
                .build();
    }
}
