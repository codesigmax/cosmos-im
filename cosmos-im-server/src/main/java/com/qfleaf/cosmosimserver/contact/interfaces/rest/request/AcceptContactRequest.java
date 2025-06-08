package com.qfleaf.cosmosimserver.contact.interfaces.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptContactRequest {
    private String eventId;
    private Long groupId;
}
