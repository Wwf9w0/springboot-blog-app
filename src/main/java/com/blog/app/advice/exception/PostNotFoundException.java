package com.blog.app.advice.exception;

import com.blog.app.advice.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException{

    private final int  code;

    public PostNotFoundException(){
        super("Post Not found");
        this.code = ErrorCodes.POST_NOT_FOUND;
    }
}
