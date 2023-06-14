package com.example.filikov_advanced_server.security;

import com.example.filikov_advanced_server.entity.UserEntity;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.security.jwt.JwtUserFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String uuid) {
        UserEntity userEntity = userRepo.findByEmail(uuid).orElseThrow(() -> new CustomException(ValidationConstants.USER_NOT_FOUND));
        return JwtUserFactory.create(userEntity);
    }
}