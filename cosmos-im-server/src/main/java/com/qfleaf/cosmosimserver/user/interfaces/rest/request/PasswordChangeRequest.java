package com.qfleaf.cosmosimserver.user.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PasswordChangeRequest", description = "密码修改请求体")
public class PasswordChangeRequest {

    @NotBlank(message = "原密码不能为空")
    @Schema(description = "原密码", example = "oldPassword123")
    private String originalPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 8, message = "新密码长度不能小于8位")
    @Schema(description = "新密码", example = "newStrongPassword456")
    private String newPassword;
}
