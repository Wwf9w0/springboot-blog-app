package com.blog.app.controller;

import com.blog.app.advice.exception.TagNameNotFoundException;
import com.blog.app.model.Post;
import com.blog.app.model.Tag;
import com.blog.app.service.AppSetting;
import com.blog.app.service.PostService;
import com.blog.app.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class TagController {

    private final TagService tagService;
    private final PostService postService;
    private final AppSetting appSetting;

    @GetMapping
    public ResponseEntity<List<Object[]>> index(){
        return ResponseEntity.ok(postService.countPostsByTags());
    }

    @GetMapping("/tags/{tagName}")
    public ResponseEntity<Page<Post>> showTag(@PathVariable String tagName, @RequestParam(defaultValue = "1") int page){
        Tag tag = tagService.getTag(tagName);
        if (Objects.isNull(tag)){
            throw new TagNameNotFoundException();
        }
        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.findPostsByTag(tagName, page, appSetting.getPageSize());
        return ResponseEntity.ok(posts);
    }
}
