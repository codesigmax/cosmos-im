package com.qfleaf.cosmosimserver.contact.interfaces.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddContactRequest {
    private Long contactId;
    private Long groupId;
}
