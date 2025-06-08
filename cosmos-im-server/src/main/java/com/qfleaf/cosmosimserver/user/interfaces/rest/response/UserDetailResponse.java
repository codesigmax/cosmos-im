package com.qfleaf.cosmosimserver.user.interfaces.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserDetailResponse", description = "用户详情响应信息")
public class UserDetailResponse {

    @Schema(description = "用户名", example = "qfleaf")
    private String username;

    @Schema(description = "用户邮箱", example = "qfleaf@example.com")
    private String email;

    @Schema(description = "用户昵称", example = "清风叶")
    private String nickname;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(description = "账户创建时间", example = "2025-06-09T08:00:00Z")
    private Instant createdAt;
}
