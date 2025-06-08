package com.qfleaf.cosmosimserver.contact.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AcceptContactRequest", description = "接受联系人添加请求体")
public class AcceptContactRequest {

    @NotBlank(message = "事件ID不能为空")
    @Schema(description = "事件ID，用于唯一标识这次添加请求", example = "evt_1688712300123")
    private String eventId;

    @NotNull(message = "群组ID不能为空")
    @Schema(description = "群组ID，接受者希望将联系人加入的分组", example = "10001")
    private Long groupId;
}
