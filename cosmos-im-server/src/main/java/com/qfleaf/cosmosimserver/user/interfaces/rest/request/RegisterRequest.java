package com.qfleaf.cosmosimserver.user.interfaces.rest.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String avatar;
}
