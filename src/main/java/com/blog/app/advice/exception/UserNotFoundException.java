package com.blog.app.advice.exception;

import com.blog.app.advice.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{

    private final int  code;

    public UserNotFoundException(){
        super("User not found");
        this.code = ErrorCodes.USER_NOT_FOUND;
    }
}
