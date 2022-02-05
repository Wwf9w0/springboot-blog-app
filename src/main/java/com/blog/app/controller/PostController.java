package com.blog.app.controller;

import com.blog.app.model.Post;
import com.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
