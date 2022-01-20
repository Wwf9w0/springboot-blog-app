package com.blog.app.model.forms;

import com.blog.app.model.enums.PostFormat;
import com.blog.app.model.enums.PostStatus;
import com.blog.app.model.enums.PostType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class PostForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String summary;

    @NotNull
    private PostFormat postFormat;

    @NotNull
    private PostStatus postStatus;

    @NotNull
    private String permaLink;

    private String postTags;

    @NotNull
    private PostType postType;
}
