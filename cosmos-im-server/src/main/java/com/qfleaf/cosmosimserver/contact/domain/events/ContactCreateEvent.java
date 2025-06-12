package com.qfleaf.cosmosimserver.contact.domain.events;

import com.qfleaf.cosmosimserver.contact.domain.aggregates.ContactAggregate;
import com.qfleaf.cosmosimserver.contact.domain.entities.ContactEntity;
import com.qfleaf.cosmosimserver.core.domain.id.HashIdGenerator;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ContactCreateEvent extends ApplicationEvent {
    private final String eventId;
    private final ContactAggregate contact;

    public ContactCreateEvent(ContactAggregate contact) {
        super(contact);
        ContactEntity from = contact.getFrom();
        this.eventId = HashIdGenerator.hashFromLong(from.getOwnerId()) + HashIdGenerator.hashFromLong(from.getContactId());
        this.contact = contact;
    }
}
