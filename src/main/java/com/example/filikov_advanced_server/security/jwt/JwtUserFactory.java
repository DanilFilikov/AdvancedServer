package com.example.filikov_advanced_server.security.jwt;

import com.example.filikov_advanced_server.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class JwtUserFactory {

    public static JwtUser create(UserEntity userEntity){
        return new JwtUser(userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getPassword(),
                        userEntity.getEmail(),
                        userEntity.getAvatar(),
                        mapToGrantedAuthorities(Collections.singletonList("USER")));

    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(Collection roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
    }
}