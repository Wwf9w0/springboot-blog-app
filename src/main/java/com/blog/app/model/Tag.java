package com.blog.app.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "tagCache")
@RequiredArgsConstructor
public class Tag extends BasedEntity{

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();

    public Tag(String name){
        this.setName(name);
    }


}
