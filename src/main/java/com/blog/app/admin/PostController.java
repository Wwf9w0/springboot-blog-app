package com.blog.app.admin;

import com.blog.app.model.Post;
import com.blog.app.model.User;
import com.blog.app.repository.PostRepository;
import com.blog.app.service.PostService;
import com.blog.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Page<Post>> getPostByUser(@RequestParam String userName){
        Page<Post> postByUser = postService.findPostsByUserName(userName);
        return ResponseEntity.ok(postByUser);
    }

    @GetMapping("/{tagName}/{page}/{pageSize}")
    public ResponseEntity<Page<Post>> getPostByTagName(@RequestParam String tagName, @RequestParam int page,
                                                       @RequestParam int pageSize){
        Page<Post> postsByTagName = postService.findPostsByTag(tagName, page, pageSize);
        return ResponseEntity.ok(postsByTagName);
    }
}
