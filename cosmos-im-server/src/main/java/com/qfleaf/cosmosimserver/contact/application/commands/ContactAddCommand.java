package com.qfleaf.cosmosimserver.contact.application.commands;

public record ContactAddCommand(
        Long ownerId,
        Long contactId,
        Long groupId
) {
}
