package com.qfleaf.cosmosimserver.core.domain.base;

import com.qfleaf.cosmosimserver.core.domain.AggregateRoot;
import com.qfleaf.cosmosimserver.core.domain.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseAggregateRoot implements AggregateRoot {
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }
    
    @Override
    public void clearEvents() {
        domainEvents.clear();
    }

}