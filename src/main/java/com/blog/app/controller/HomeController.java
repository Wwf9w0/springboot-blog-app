package com.blog.app.controller;

import com.blog.app.model.Post;
import com.blog.app.service.AppSetting;
import com.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;
    private final AppSetting appSetting;

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(@PathVariable int page){
        page = page < 1  ? 0 : page -1 ;
        Page<Post> posts = postService.getAllPublishedPostsByPage(page, appSetting.getPageSize());
        return ResponseEntity.ok(posts);
    }
}
