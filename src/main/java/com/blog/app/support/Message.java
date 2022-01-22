package com.blog.app.support;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Message implements Serializable {

    public static final String MESSAGE_ATTRIBUTE = "message";
    private final String message;
    private final Type type;
    private final Object[] args;

    public static enum Type{
        DANGER, WARNING, INFO, SUCCESS;
    }
}
