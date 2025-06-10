package com.qfleaf.cosmosimserver.user.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "EditProfileRequest", description = "修改个人信息请求体")
public class EditProfileRequest {
    @Schema(description = "用户昵称", example = "SpringFaker")
    private String nickname;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
}
