package com.qfleaf.cosmosimserver.user.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qfleaf.cosmosimserver.user.domain.entities.UserEntity;
import com.qfleaf.cosmosimserver.user.interfaces.rest.response.UserDetailResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailDTO {
    private UserEntity user;

    public UserDetailDTO(UserEntity user) {
        this.user = user;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public UserDetailResponse toVO() {
        return UserDetailResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
