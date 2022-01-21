package service;

import com.blog.app.error.NotFoundException;
import com.blog.app.model.Post;
import com.blog.app.repository.PostRepository;
import com.blog.app.support.FlexMarkdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final UserService userService;
    private final FlexMarkdownService flexMarkdownService;

    public Optional<Post> getPost(Long postId){
        log.debug("Get Post" + postId);

        Optional<Post> post = postRepository.findById(postId);
        if (!Objects.nonNull(post)){
            throw new NotFoundException("Post with id" + postId + "is not found.");
        }
        return post;
    }

}
