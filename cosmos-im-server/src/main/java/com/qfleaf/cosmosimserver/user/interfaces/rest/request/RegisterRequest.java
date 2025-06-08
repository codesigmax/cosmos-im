package com.qfleaf.cosmosimserver.user.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RegisterRequest", description = "用户注册请求体")
public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 16, message = "用户名长度应为3～16个字符")
    @Schema(description = "用户名", example = "qfleaf")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码长度不能小于8位")
    @Schema(description = "密码", example = "securePass123")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;

    @Size(max = 32, message = "昵称长度不能超过32个字符")
    @Schema(description = "昵称", example = "小宇")
    private String nickname;

    @Schema(description = "头像 URL", example = "https://example.com/avatar.png")
    private String avatar;
}
