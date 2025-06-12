package com.qfleaf.cosmosimserver.contact.domain.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactGroup {
    private Long id;
    private Long ownerId;
    private String groupName;
}
