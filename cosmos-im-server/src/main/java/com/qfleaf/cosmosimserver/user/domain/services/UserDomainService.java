package com.qfleaf.cosmosimserver.user.domain.services;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.exceptions.PasswordMismatchException;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 包含核心业务逻辑
    public UserAggregate register(String username, String rawPassword, String email, String nickname, String avatar) {
        userRepository.checkUsername(username);
        UserAggregate user = UserAggregate.createUser(
                username,
                rawPassword,
                email,
                nickname,
                avatar,
                passwordEncoder
        );
        return userRepository.save(user);
    }

    public UserAggregate login(String account, String password) {
        UserAggregate user = userRepository.findUserByAccount(account);
        boolean matches = user.getPassword().matches(password, passwordEncoder);
        if (!matches) {
            throw new PasswordMismatchException("用户名或密码错误");
        }
        return user;
    }
}
