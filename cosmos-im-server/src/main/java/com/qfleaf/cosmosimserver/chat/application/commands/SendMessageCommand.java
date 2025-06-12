package com.qfleaf.cosmosimserver.chat.application.commands;

public record SendMessageCommand(
        Long senderId,
        Long receiverId,
        String content
) {
}
