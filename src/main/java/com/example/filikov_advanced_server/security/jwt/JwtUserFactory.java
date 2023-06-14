package com.example.filikov_advanced_server.security.jwt;

import com.example.filikov_advanced_server.entity.UserEntity;

public final class JwtUserFactory {
    public static JwtUser create(UserEntity userEntity){
        return new JwtUser(userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getPassword(),
                        userEntity.getEmail(),
                        userEntity.getAvatar(),
                        userEntity.getRole());
    }

}