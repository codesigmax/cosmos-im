package com.qfleaf.cosmosimserver.user.interfaces.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginResponse", description = "登录成功返回的令牌信息")
public class LoginResponse {

    @Schema(description = "令牌名称", example = "imtoken")
    private String tokenName;

    @Schema(description = "令牌值", example = "eyJhbGciOiJIUzI1...")
    private String tokenValue;
}
