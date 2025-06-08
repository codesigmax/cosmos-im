package com.qfleaf.cosmosimserver.contact.application.queries;

import com.qfleaf.cosmosimserver.contact.domain.entities.ContactGroup;
import com.qfleaf.cosmosimserver.contact.domain.repositories.ContactGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactGroupQueryService {
    private final ContactGroupRepository contactGroupRepo;

    public List<ContactGroup> getAllContactGroups(Long userId) {
        return contactGroupRepo.selectAllByOwnerId(userId);
    }
}
