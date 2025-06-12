package com.qfleaf.cosmosimserver.contact.infrastructure.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddContactEventDTO {
    private Long senderId;
    private String eventId;
    private Long timestamp;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddContactEventDTO that = (AddContactEventDTO) o;
        return Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventId);
    }
}
