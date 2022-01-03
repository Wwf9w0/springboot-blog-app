package com.blog.app.model;

import lombok.Data;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
public abstract class BasedEntity implements Comparable<BasedEntity>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime creatAt;

    private ZonedDateTime updateAt;


    @PrePersist
    public void prepersist() {
        creatAt = updateAt = ZonedDateTime.now();
    }

    @Override
    public int compareTo(BasedEntity o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        return this.getId().equals(((BasedEntity) other).getId());
    }

    public int hashCode(){
        return new HashCodeBuilder().append(getId()).toHashCode();
    }


}
