package com.qfleaf.cosmosimserver.contact.domain.services;

import com.qfleaf.cosmosimserver.contact.domain.aggregates.ContactAggregate;
import com.qfleaf.cosmosimserver.contact.domain.repositories.ContactRepository;
import com.qfleaf.cosmosimserver.core.domain.enums.ContactRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactDomainService {
    private final ContactRepository contactRepo;

    public ContactAggregate createContactFrom(Long ownerId, Long contactId, Long groupId) {
        return ContactAggregate.createContactFrom(
                ownerId,
                contactId,
                ContactRelation.FRIEND,
                null,
                groupId
        );
    }

    public ContactAggregate createContactTo(ContactAggregate contact, Long groupId) {
        return contact.createContactTo(
                contact.getFrom().getContactId(),
                contact.getFrom().getOwnerId(),
                ContactRelation.FRIEND,
                null,
                groupId
        );
    }

    public ContactAggregate saveContact(ContactAggregate contact) {
        return contactRepo.save(contact);
    }
}
