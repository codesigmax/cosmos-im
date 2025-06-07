package com.qfleaf.cosmosimserver.user.interfaces.rest.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class UserDetailResponse {
    private String username;
    private String email;

    private String nickname;
    private String avatar;
    private Instant createdAt;
}
