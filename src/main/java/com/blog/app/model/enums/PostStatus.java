package com.blog.app.model.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum PostStatus {
    DRAFT("Draft"), PUBLISHED("Published");

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
