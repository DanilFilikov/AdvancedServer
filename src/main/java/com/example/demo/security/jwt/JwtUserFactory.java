package com.example.demo.security.jwt;

import com.example.demo.entity.UserEntity;

public final class JwtUserFactory {
    public static JwtUser create(UserEntity userEntity){
        return new JwtUser(userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getPassword(),
                        userEntity.getEmail(),
                        null,
                        userEntity.getAvatar(),
                        userEntity.getRole());
    }
}