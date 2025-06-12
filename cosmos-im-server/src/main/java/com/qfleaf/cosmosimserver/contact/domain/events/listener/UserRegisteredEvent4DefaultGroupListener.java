package com.qfleaf.cosmosimserver.contact.domain.events.listener;

import com.qfleaf.cosmosimserver.contact.domain.entities.ContactGroup;
import com.qfleaf.cosmosimserver.contact.domain.repositories.ContactGroupRepository;
import com.qfleaf.cosmosimserver.user.domain.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRegisteredEvent4DefaultGroupListener
        implements ApplicationListener<UserRegisteredEvent> {
    private final ContactGroupRepository contactGroupRepository;

    public UserRegisteredEvent4DefaultGroupListener(ContactGroupRepository contactGroupRepository) {
        this.contactGroupRepository = contactGroupRepository;
    }

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        Long userId = event.getUser().getUserId().value();
        log.info("监听到事件：新用户注册 {}", userId);
        ContactGroup defaultGroup = ContactGroup.builder()
                .ownerId(userId)
                .groupName("默认分组")
                .build();
        int insert = contactGroupRepository.insert(defaultGroup);
        log.info("默认分组 {} 创建完毕 , 受影响行数 {}", defaultGroup.getId(), insert);
    }
}
