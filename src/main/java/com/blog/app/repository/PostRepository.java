package com.blog.app.repository;

import com.blog.app.model.Post;
import com.blog.app.model.enums.PostStatus;
import com.blog.app.model.enums.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserName(String userName);
    Optional<Post> findByPermaLinkAndPosStatus(String permaLink, PostStatus postStatus);
    Page<Post> findAllByPostType(PostType postType, Pageable pageable);
    Page<Post> findAllByPostTypeAndPostStatus(PostType postType, PostStatus postStatus, Pageable page);

    @Query("SELECT p FROM Post p INNER JOIN p.tags t WHERE t.name = :tag")
    Page<Post> findByTag(@Param("tag") String tag, Pageable pageable);

    @Query("SELECT t.name, count(p) as tag_count from Post p INNER JOIN p.tags t WHERE p.postStatus = :status  GROUP BY t.id ORDER BY tag_count DESC")
    List<Object[]> countPostByTags(@Param("status") PostStatus postStatus);
}
