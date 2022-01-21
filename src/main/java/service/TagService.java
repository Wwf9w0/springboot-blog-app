package service;

import com.blog.app.model.Tag;
import com.blog.app.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag findOrCreateByName(String name) {
        Tag tag = tagRepository.findByName(name);
        if (!Objects.nonNull(tag)) {
            tag = tagRepository.save(new Tag(name));
        }
        return tag;
    }

    public Tag getTag(String tagName){
        return tagRepository.findByName(tagName);
    }

    public void deleteTag(Tag tag){
        tagRepository.delete(tag);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

}
