package com.qfleaf.cosmosimserver.contact.application.commands;

public record ContactAcceptCommand(
        Long groupId,
        String eventId
) {
}
