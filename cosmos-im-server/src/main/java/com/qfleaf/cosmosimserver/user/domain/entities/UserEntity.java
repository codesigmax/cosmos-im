package com.qfleaf.cosmosimserver.user.domain.entities;

import com.qfleaf.cosmosimserver.core.domain.enums.AccountStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;

    private String username;
    private String password;
    private String email;

    private String nickname;
    private String avatar;
    private AccountStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
