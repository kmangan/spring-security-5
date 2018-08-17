package com.kieranmangan.auth.service;

import com.kieranmangan.auth.db.Authority;
import com.kieranmangan.auth.db.User;
import com.kieranmangan.auth.model.UserForm;
import com.kieranmangan.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UUID save(UserForm form) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(form.getUsername())
                // Spring Security 5 uses BCrypt by default, which has a default 'strength' of 10. This is good enough for most enterprise implementations.
                // If you increase the strength the encoding, the encoding time will increase exponentially.
                .password(passwordEncoder.encode(form.getPassword()))
                // It's a requirement of Spring Security OAuth2 to have at least one authority.
                // In this case we simply have one authority called 'all'.
                .authorities(Collections.singletonList(
                        Authority.builder()
                                .authority("all")
                                .username(form.getUsername())
                                .build()))
                .build();
        userRepository.save(user);
        return user.getId();
    }
}