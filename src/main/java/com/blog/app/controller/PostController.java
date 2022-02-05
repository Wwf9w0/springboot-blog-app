package com.blog.app.controller;

import com.blog.app.advice.exception.PostNotFoundException;
import com.blog.app.model.Post;
import com.blog.app.model.enums.PostType;
import com.blog.app.service.PostService;
import javafx.geometry.Pos;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/archive")
    public ResponseEntity<Map<Integer, List<Post>>> archivePost(){
        Map<Integer, List<Post>> posts = new HashMap<>();
        postService.getArchivePosts()
                .forEach(post -> {
                    if (!posts.containsKey(post.getCreatAt().getYear())){
                        posts.put(post.getCreatAt().getYear(), new ArrayList<>());
                    }
                    posts.get(post.getCreatAt().getYear()).add(post);
                });
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{permaLink}")
    public ResponseEntity<Optional<Post>> show(@PathVariable String permaLink){
        return showPost(permaLink, PostType.PAGE);
    }


    private ResponseEntity<Optional<Post>> showPost(String permaLink, PostType postType){
        Optional<Post> post;
        try {

           post = postService.getPublishedPostByPermaLink(permaLink);

        }catch (PostNotFoundException ex){
            if (permaLink.matches("\\d+") && postType.equals(PostType.POST)){
                post = postService.getPost(Long.valueOf(permaLink));
            }else{
                throw  new PostNotFoundException();
            }

        }
        if (!post.get().getPostType().equals(postType)){
            throw new PostNotFoundException();
        }
        postService.incrementViews(post.get().getId());
        return ResponseEntity.ok(post);
    }
}
