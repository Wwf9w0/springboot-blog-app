package com.blog.app.admin;

import com.blog.app.repository.PostRepository;
import com.blog.app.service.PostService;
import com.blog.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserService userService;
}
