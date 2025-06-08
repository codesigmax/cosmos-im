package com.qfleaf.cosmosimserver.contact.domain.repositories;

import com.qfleaf.cosmosimserver.contact.domain.aggregates.ContactAggregate;

public interface ContactRepository {
    ContactAggregate save(ContactAggregate contact);
}
