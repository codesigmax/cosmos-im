package com.qfleaf.cosmosimserver.user.domain.valueobjects;

import com.qfleaf.cosmosimserver.core.domain.ValueObject;
import com.qfleaf.cosmosimserver.core.domain.id.SnowflakeIdGenerator;

public record UserId(Long value) implements ValueObject {
    public static UserId newId() {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        return new UserId(snowflakeIdGenerator.nextId());
    }
}
