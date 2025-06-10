package com.qfleaf.cosmosimserver.chat.interfaces.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRequest {
    private Long receiverId;
    private String content;
}
