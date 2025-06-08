package com.qfleaf.cosmosimserver.contact.domain.entities;

import com.qfleaf.cosmosimserver.core.domain.enums.ContactRelation;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {
    private Long id;
    private Long ownerId;
    private Long contactId;
    private ContactRelation relation;
    private String remarkName;
    private Long groupId;
    //    private ContactGroup group;
    private Instant createdAt;
    private Instant updatedAt;
}
