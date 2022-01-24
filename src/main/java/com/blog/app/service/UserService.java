package com.blog.app.service;

import com.blog.app.advice.constants.EnviromentConstants;
import com.blog.app.model.User;
import com.blog.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getSuperUser() {
        User user = userRepository.findByEmail(EnviromentConstants.DEFAULT_ADMIN_EMAIL);
        if (Objects.isNull(user)) {
            user = createUser(new User(EnviromentConstants.DEFAULT_ADMIN_EMAIL, EnviromentConstants.DEFAULT_ADMIN_PASSWORD, User.ROLE_ADMIN));
        }
        return user;
    }

    public UserDetails loadUserByUserName(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User Not Found!");
        }
        return createSpringUser(user);
    }

    private org.springframework.security.core.userdetails.User createSpringUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singleton(createAuthority(user)));
    }

    private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority(user.getRole());
    }

    public void signIn(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) {
        return new UsernamePasswordAuthenticationToken(createSpringUser(user),
                null, Collections.singleton(createAuthority(user)));
    }

    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth) || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        return userRepository.findByEmail(email);
    }

    public boolean changePassword(User user, String password, String newPassword) {
        if (Objects.isNull(password) || Objects.isNull(newPassword) || password.isEmpty()
                || newPassword.isEmpty()) {
            return false;
        }
        boolean match = passwordEncoder.matches(password, user.getPassword());
        if (!match) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("User @{} changed password.", user.getEmail());
        return true;
    }
}

