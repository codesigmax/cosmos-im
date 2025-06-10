package com.qfleaf.cosmosimserver.contact.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CreateContactGroupRequest", description = "创建联系人分组请求体")
public class CreateContactGroupRequest {

    @NotBlank(message = "分组名称不能为空")
    @Size(min = 2, max = 32, message = "分组名称长度应在2到32个字符之间")
    @Schema(description = "联系人分组名称", example = "朋友", requiredMode = Schema.RequiredMode.REQUIRED)
    private String groupName;
}
