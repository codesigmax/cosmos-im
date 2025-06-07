package com.qfleaf.cosmosimserver.user.domain.services;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    // 包含核心业务逻辑
    public UserAggregate saveUser(String username, String rawPassword, String email, String nickname, String avatar) {
        userRepository.checkUsername(username);
        return userRepository.save(
                username,
                rawPassword,
                email,
                nickname,
                avatar
        );
    }

    public UserAggregate getUser(String account) {
        return userRepository.findUserByAccount(account);
    }

    public UserAggregate getUser(Long userId) {
        return userRepository.findUserById(userId);
    }
}
