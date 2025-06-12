package com.qfleaf.cosmosimserver.core.domain.base;

import com.qfleaf.cosmosimserver.core.domain.ValueObject;

import java.util.Objects;

public abstract class BaseValueObject implements ValueObject {
    @Override
    public abstract boolean equals(Object o);
    
    @Override
    public abstract int hashCode();
    
    protected boolean equalValues(Object a, Object b) {
        return Objects.equals(a, b);
    }
}