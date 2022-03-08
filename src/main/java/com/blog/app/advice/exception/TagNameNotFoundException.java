package com.blog.app.advice.exception;

import com.blog.app.advice.constants.ErrorCodes;
import lombok.Getter;

@Getter
public class TagNameNotFoundException extends RuntimeException{

    private final int  code;

    public TagNameNotFoundException(){
        super("Tag Name Not found");
        this.code = ErrorCodes.TAG_NAME_NOT_FOUND;
    }
}
