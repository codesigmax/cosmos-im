package com.qfleaf.cosmosimserver.user.application.commands;

public record ModifyProfileCommand(
        String avatarUrl,
        String nickname
) {
}
