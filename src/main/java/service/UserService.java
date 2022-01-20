package service;

import com.blog.app.config.constants.BlogConstants;
import com.blog.app.model.User;
import com.blog.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getSuperUser(){
        User user = userRepository.findByEmail(BlogConstants.DEFAULT_ADMIN_EMAIL);
        if (!Objects.nonNull(user)){
            user = createUser(new User(BlogConstants.DEFAULT_ADMIN_EMAIL, BlogConstants.DEFAULT_ADMIN_PASSWORD, User.ROLE_ADMIN));
        }
        return user;

    }

}

