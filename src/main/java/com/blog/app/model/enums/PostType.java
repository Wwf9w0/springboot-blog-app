package com.blog.app.model.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum PostType {
    PAGE("Page"), POST("Post");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
