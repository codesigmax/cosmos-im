package com.qfleaf.cosmosimserver.contact.domain.aggregates;

import com.qfleaf.cosmosimserver.contact.domain.entities.ContactEntity;
import com.qfleaf.cosmosimserver.core.domain.DomainEvent;
import com.qfleaf.cosmosimserver.core.domain.base.BaseAggregateRoot;
import com.qfleaf.cosmosimserver.core.domain.enums.ContactRelation;
import com.qfleaf.cosmosimserver.shared.exception.InvalidArgsException;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
public class ContactAggregate extends BaseAggregateRoot {
    private ContactEntity from;
    private ContactEntity to;

    public ContactAggregate() {
    }

    public static ContactAggregate createContactFrom(
            Long ownerId,
            Long contactId,
            ContactRelation relation,
            String remarkName,
            Long groupId
    ) {
        if (Objects.equals(ownerId, contactId)) {
            throw new InvalidArgsException("你不能添加自己为联系人");
        }
        ContactAggregate contact = new ContactAggregate();
        contact.from = ContactEntity.builder()
                .ownerId(ownerId)
                .contactId(contactId)
                .relation(relation)
                .remarkName(remarkName)
                .groupId(groupId)
                .createdAt(Instant.now())
                .build();

//        contact.group = ContactGroup.builder().id(groupId).build();
//        contact.registerEvent(new ContactCreateEvent(contact));
        return contact;
    }

    public ContactAggregate createContactTo(
            Long ownerId,
            Long contactId,
            ContactRelation relation,
            String remarkName,
            Long groupId
    ) {
        this.to = ContactEntity.builder()
                .ownerId(ownerId)
                .contactId(contactId)
                .relation(relation)
                .remarkName(remarkName)
                .groupId(groupId)
                .createdAt(Instant.now())
                .build();
        return this;
    }

    @Override
    public List<DomainEvent> domainEvents() {
        return super.getDomainEvents();
    }
}
