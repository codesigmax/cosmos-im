package com.qfleaf.cosmosimserver.contact.application.services;

import com.qfleaf.cosmosimserver.contact.application.commands.ContactGroupCreateCommand;
import com.qfleaf.cosmosimserver.contact.domain.entities.ContactGroup;
import com.qfleaf.cosmosimserver.contact.domain.repositories.ContactGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactGroupOpsService {
    private final ContactGroupRepository contactGroupRepo;

    public void create(ContactGroupCreateCommand command) {
        contactGroupRepo.insert(
                ContactGroup.builder()
                        .ownerId(command.ownerId())
                        .groupName(command.groupName())
                        .build()
        );
    }
}
