package com.blog.app.model.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum PostFormat {

    HTML("Html"), MARKDOWN("Markdown");

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
