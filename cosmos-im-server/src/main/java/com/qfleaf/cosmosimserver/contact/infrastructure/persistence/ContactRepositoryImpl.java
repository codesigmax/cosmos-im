package com.qfleaf.cosmosimserver.contact.infrastructure.persistence;

import com.qfleaf.cosmosimserver.contact.domain.aggregates.ContactAggregate;
import com.qfleaf.cosmosimserver.contact.domain.entities.ContactEntity;
import com.qfleaf.cosmosimserver.contact.domain.repositories.ContactRepository;
import com.qfleaf.cosmosimserver.contact.domain.repositories.ContactWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {
    private final ContactWriteRepository writeRepo;

    @Override
    public ContactAggregate save(ContactAggregate contact) {
        ContactEntity from = contact.getFrom();
        ContactEntity to = contact.getTo();
        int insert = writeRepo.insert(from) + writeRepo.insert(to);
        log.info("用户 {} 新增联系人成功: {}，影响行数: {}", from.getOwnerId(), to.getOwnerId(), insert);
        return contact;
    }
}
