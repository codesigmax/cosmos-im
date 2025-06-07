package com.qfleaf.cosmosimserver.user.domain.repositories;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;

public interface UserRepository {
    UserAggregate save(UserAggregate user);

    UserAggregate findUserByAccount(String account);

    void checkUsername(String username);
}
