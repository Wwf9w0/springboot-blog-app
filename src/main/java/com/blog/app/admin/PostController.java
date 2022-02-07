package com.blog.app.admin;

import com.blog.app.model.Post;
import com.blog.app.model.forms.PostForm;
import com.blog.app.repository.PostRepository;
import com.blog.app.service.PostService;
import com.blog.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserService userService;
}
