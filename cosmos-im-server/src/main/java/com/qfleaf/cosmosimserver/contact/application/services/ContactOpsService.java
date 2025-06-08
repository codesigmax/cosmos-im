package com.qfleaf.cosmosimserver.contact.application.services;

import com.qfleaf.cosmosimserver.contact.application.commands.ContactAcceptCommand;
import com.qfleaf.cosmosimserver.contact.application.commands.ContactAddCommand;
import com.qfleaf.cosmosimserver.contact.domain.aggregates.ContactAggregate;
import com.qfleaf.cosmosimserver.contact.domain.entities.ContactEntity;
import com.qfleaf.cosmosimserver.contact.domain.events.ContactCreateEvent;
import com.qfleaf.cosmosimserver.contact.domain.services.ContactDomainService;
import com.qfleaf.cosmosimserver.contact.infrastructure.cache.ContactCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactOpsService {
    private final ContactDomainService domainService;
    // todo 优化缓存层注入
    private final ContactCacheRepository contactCacheRepo;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void addContact(ContactAddCommand command) {
        ContactAggregate contact = domainService.createContactFrom(
                command.ownerId(),
                command.contactId(),
                command.groupId()
        );
        ContactEntity from = contact.getFrom();
        log.info("用户 {} 请求添加联系人 {} 到 {} 组", from.getOwnerId(), from.getContactId(), from.getGroupId());
        applicationEventPublisher.publishEvent(new ContactCreateEvent(contact));
    }

    // 同意添加请求
    public void acceptContact(ContactAcceptCommand command) {
        ContactAggregate contactAggregate = contactCacheRepo.getContactAggregate(command.eventId());
        ContactAggregate contact = domainService.createContactTo(
                contactAggregate,
                command.groupId()
        );
        // 保存联系人记录
        domainService.saveContact(contact);
    }
}
