package com.qfleaf.cosmosimserver.chat.interfaces.rest.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageQueryResponse {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Instant createdAt;
    // todo 增加分类（发送、接收）
}
