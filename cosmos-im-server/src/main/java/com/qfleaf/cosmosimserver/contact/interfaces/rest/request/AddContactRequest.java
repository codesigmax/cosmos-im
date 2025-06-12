package com.qfleaf.cosmosimserver.contact.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AddContactRequest", description = "添加联系人请求体")
public class AddContactRequest {

    @NotNull(message = "联系人ID不能为空")
    @Schema(description = "要添加的联系人用户ID", example = "20001")
    private Long contactId;

    @Schema(description = "联系人添加到的分组ID，可为空", example = "10001")
    private Long groupId;
}
