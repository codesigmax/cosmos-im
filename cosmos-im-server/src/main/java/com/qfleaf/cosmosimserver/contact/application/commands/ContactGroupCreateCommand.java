package com.qfleaf.cosmosimserver.contact.application.commands;

public record ContactGroupCreateCommand(
        Long ownerId,
        String groupName
) {
}
