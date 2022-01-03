package com.blog.app.model;

import com.blog.app.model.enums.PostFormat;
import com.blog.app.model.enums.PostStatus;
import com.blog.app.model.enums.PostType;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "posts")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "postCache")
public class Post extends BasedEntity {
    private static final SimpleDateFormat SLUG_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");


    private String title;
    private String content;
    private String renderedContent;
    private String summary;
    private String renderedSummary;
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.PUBLISHED;
    @Enumerated(EnumType.STRING)
    private PostFormat postFormat = PostFormat.MARKDOWN;
    @Enumerated(EnumType.STRING)
    private PostType postType = PostType.POST;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "posts_tags", joinColumns = {@JoinColumn(name = "post_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, updatable = false)})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "tagCache")
    private Set<Tag> tags = new HashSet<>();


    private String permalink;

    private Integer views = 0;

    @ManyToOne
    private User user;

    public Integer getViews() {
        return views == null ? 0 : views;
    }

    public String getRenderedContent() {
        if (this.postFormat == PostFormat.MARKDOWN) {
            return renderedContent;
        }

        return getContent();
    }

    public void setPermalink(String permalink) {
        String token = permalink.toLowerCase().replace("\n", " ").replaceAll("[^a-z\\d\\s]", " ");
        this.permalink = StringUtils.arrayToDelimitedString(StringUtils.tokenizeToStringArray(token, " "), "-");
    }


}
