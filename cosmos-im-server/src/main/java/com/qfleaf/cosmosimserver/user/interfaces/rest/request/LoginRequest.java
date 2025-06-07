package com.qfleaf.cosmosimserver.user.interfaces.rest.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    private String account;
    private String password;
}
