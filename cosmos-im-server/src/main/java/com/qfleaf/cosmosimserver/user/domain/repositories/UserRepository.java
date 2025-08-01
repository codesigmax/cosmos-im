package com.qfleaf.cosmosimserver.user.domain.repositories;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;

public interface UserRepository {
    UserAggregate save(String username, String rawPassword, String email, String nickname, String avatar);

    UserAggregate findUserByAccount(String account);

    void checkUsername(String username);

    void checkEmail(String email);

    UserAggregate findUserById(Long userId);

    UserAggregate save(UserAggregate user);
}
