package com.qfleaf.cosmosimserver.user.infrastructure.persistence;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.entities.UserEntity;
import com.qfleaf.cosmosimserver.shared.exception.InvalidArgsException;
import com.qfleaf.cosmosimserver.user.domain.exceptions.UserNotFoundException;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserReadRepository;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserRepository;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserWriteRepository writeRepo;
    private final UserReadRepository readRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAggregate save(String username, String rawPassword, String email, String nickname, String avatar) {
        UserAggregate user = UserAggregate.createUser(
                username,
                rawPassword,
                email,
                nickname,
                avatar,
                passwordEncoder
        );
        UserEntity entity = user.toEntity();
        int insert = writeRepo.insert(entity);
        log.info("insert user entity: {}, effect rows: {} ", entity, insert);
        return user;
    }

    @Override
    public UserAggregate findUserByAccount(String account) {
        return readRepo.findUserByAccount(account)
                .map(entity -> new UserAggregate(entity, passwordEncoder))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void checkUsername(String username) {
        readRepo.findUserByAccount(username).ifPresent(user -> {
            throw new InvalidArgsException("用户名不可用：" + username);
        });
    }

    @Override
    public void checkEmail(String email) {
        readRepo.findUserByEmail(email).ifPresent(user -> {
            throw new InvalidArgsException("邮箱不可用" + email);
        });
    }

    @Override
    public UserAggregate findUserById(Long userId) {
        return readRepo.findDetailById(userId)
                .map(entity -> new UserAggregate(entity, passwordEncoder))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserAggregate save(UserAggregate user) {
        UserEntity entity = user.toEntity();
        int update = writeRepo.update(entity);
        log.info("update user entity: {}, effect rows: {}", entity, update);
        return user;
    }
}
