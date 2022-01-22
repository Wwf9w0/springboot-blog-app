package com.blog.app.error;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{

    private final int  code;

    public UserNotFoundException(){
        super("User not found");
        this.code = ErrorCodes.USER_NOT_FOUND;
    }
}
