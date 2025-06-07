package com.qfleaf.cosmosimserver.user.application.commands;

public record RegisterByAccountCommand(
        String username,
        String password,
        String email,
        String nickname,
        String avatar
) {
}
