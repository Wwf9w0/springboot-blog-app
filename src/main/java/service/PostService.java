package service;

import com.blog.app.config.Constants;
import com.blog.app.error.NotFoundException;
import com.blog.app.model.Post;
import com.blog.app.model.Tag;
import com.blog.app.model.enums.PostFormat;
import com.blog.app.model.enums.PostStatus;
import com.blog.app.model.enums.PostType;
import com.blog.app.repository.PostRepository;
import com.blog.app.support.FlexMarkdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final UserService userService;
    private final FlexMarkdownService flexMarkdownService;

    public Optional<Post> getPost(Long postId) {
        log.debug("Get Post" + postId);

        Optional<Post> post = postRepository.findById(postId);
        if (!Objects.nonNull(post)) {
            throw new NotFoundException("Post with id" + postId + "is not found.");
        }
        return post;
    }

    public Optional<Post> getPublishedPostByPermaLing(String permaLink) {
        log.debug("Get post with permalink " + permaLink);

        Optional<Post> post = postRepository.findByPermaLinkAndPosStatus(permaLink, PostStatus.PUBLISHED);
        if (!Objects.nonNull(post)) {
            try {
                post = postRepository.findById(Long.valueOf(permaLink));
            } catch (NumberFormatException e) {
                post = null;
            }
        }
        if (!Objects.nonNull(post)) {
            throw new NotFoundException("Post with permalink " + permaLink + "is not found.");
        }
        return post;
    }

    public Post createPost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(flexMarkdownService.renderToHtml(post.getContent()));
            post.setPostFormat(PostFormat.valueOf(flexMarkdownService.renderToHtml(post.getSummary())));
        }
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(flexMarkdownService.renderToHtml(post.getContent()));
            post.setPostFormat(PostFormat.valueOf(flexMarkdownService.renderToHtml(post.getSummary())));
        }
        return postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public List<Post> getArchivePosts() {
        log.debug("Get all archive posts from database");

        Pageable page = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt");
        return postRepository.findAllByPostTypeAndPostStatus(PostType.POST, PostStatus.PUBLISHED, page)
                .getContent().
                stream()
                .map(this::extractPostMeta)
                .collect(Collectors.toList());
    }

    public List<Tag> getPostTags(Post post) {
        log.debug("Get tags of post {}", post.getId());

        List<Tag> tags = new ArrayList<>();
        postRepository.findById(post.getId()).get().getTags().forEach(tags::add);
        return tags;
    }

    private Post extractPostMeta(Post post) {
        Post archivePost = new Post();
        archivePost.setId(post.getId());
        archivePost.setTitle(post.getTitle());
        archivePost.setPermalink(post.getPermalink());
        archivePost.setCreatAt(post.getCreatAt());
        return archivePost;
    }

    public Page<Post> getAllPublishedPostsByPage(int page, int pageSize) {
        log.debug("Get posts by page " + page);

        return postRepository.findAllByPostTypeAndPostStatus(PostType.POST, PostStatus.PUBLISHED,
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    public Post createAboutPage() {
        log.debug("Create default about page");
        Post post = new Post();
        post.setTitle(Constants.ABOUT_PAGE_PERMALINK);
        post.setContent(Constants.ABOUT_PAGE_PERMALINK.toLowerCase());
        post.setPermalink(Constants.ABOUT_PAGE_PERMALINK);
        post.setUser(userService.getSuperUser());
        post.setPostFormat(PostFormat.MARKDOWN);
        return createPost(post);
    }

    public Set<Tag> parseTagNames(String tagNames){
        Set<Tag> tags = new HashSet<>();

        if (Objects.nonNull(tagNames) && !tags.isEmpty()){
            tagNames = tagNames.toLowerCase();
            String[] names = tagNames.split("\\s*,\\s*");
            for (String name : names){
                tags.add(tagService.findOrCreateByName(name));
            }
        }
        return tags;
    }

    public String getTagNames(Set<Tag> tags){
        if (!Objects.nonNull(tags) || tags.isEmpty()){
            return "";
        }
        StringBuilder names = new StringBuilder();
        tags.forEach(tag -> names.append(tag.getName()).append(","));
        names.deleteCharAt(names.length() - 1);

        return names.toString();
    }

    @Cacheable(cacheNames = "postCache", key = "#tagName", unless = "#result == null")
    public Page<Post> findPostsByTag(String tagName, int page, int pageSize){
        return postRepository.findByTag(tagName,
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdat"));
    }

    public List<Object[]> countPostsByTags(){
        log.debug("Count posts group tags.");
        return postRepository.countPostByTags(PostStatus.PUBLISHED);
    }

    @Async
    public void incrementViews(Long postId){
        synchronized (this){
            Post post = postRepository.findById(postId).orElse(null);
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
        }
    }

}
