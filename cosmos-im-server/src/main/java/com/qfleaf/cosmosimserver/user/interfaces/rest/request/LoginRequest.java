package com.qfleaf.cosmosimserver.user.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "LoginRequest", description = "登录请求体")
public class LoginRequest {

    @NotBlank(message = "账号不能为空")
    @Schema(description = "账号（用户名或邮箱）", example = "john_doe")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "P@ssw0rd123")
    private String password;
}
