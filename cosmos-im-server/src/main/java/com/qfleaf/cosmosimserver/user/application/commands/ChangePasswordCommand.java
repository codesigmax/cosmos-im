package com.qfleaf.cosmosimserver.user.application.commands;

public record ChangePasswordCommand(
        String originalPassword,
        String newPassword
) {
}
