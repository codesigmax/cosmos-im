package com.qfleaf.cosmosimserver.user.application.commands;

public record LoginByAccountCommand(
        String account,
        String password
) {
}
