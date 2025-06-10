package com.qfleaf.cosmosimserver.chat.interfaces.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MessageQueryResponse", description = "消息查询响应")
public class MessageQueryResponse {

    @Schema(description = "消息ID", example = "1001")
    private Long id;

    @Schema(description = "发送者ID", example = "2001")
    private Long senderId;

    @Schema(description = "接收者ID", example = "2002")
    private Long receiverId;

    @Schema(description = "消息内容", example = "你好呀！")
    private String content;

    @Schema(description = "发送时间", example = "2025-06-09T10:30:00Z")
    private Instant createdAt;

    // todo 区分发送方向
//    @Schema(description = "消息方向（SENT: 自己发出的；RECEIVED: 接收到的）", example = "SENT")
//    private MessageDirection direction;
//
//    public enum MessageDirection {
//        SENT,      // 我发送的
//        RECEIVED   // 我接收的
//    }
}
