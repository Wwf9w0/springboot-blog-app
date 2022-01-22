package com.blog.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private int code;
    private String message;
    private long timestamp;
}
