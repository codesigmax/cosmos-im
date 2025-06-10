package com.qfleaf.cosmosimserver.chat.interfaces.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "SendMessageRequest", description = "发送私聊消息请求体")
public class SendMessageRequest {

    @NotNull(message = "接收者ID不能为空")
    @Schema(description = "消息接收者用户ID", example = "10002")
    private Long receiverId;

    @NotNull(message = "消息内容不能为空")
    @Size(min = 1, max = 1000, message = "消息内容长度应在1~1000个字符之间")
    @Schema(description = "消息内容", example = "你好，请问我们可以加个好友吗？")
    private String content;
}
