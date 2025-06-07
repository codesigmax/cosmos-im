package com.qfleaf.cosmosimserver.user.infrastructure.persistence;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.entities.UserEntity;
import com.qfleaf.cosmosimserver.user.domain.exceptions.InvalidArgsException;
import com.qfleaf.cosmosimserver.user.domain.exceptions.UserNotFoundException;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserReadRepository;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserRepository;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserWriteRepository writeRepo;
    private final UserReadRepository readRepo;

    @Override
    public UserAggregate save(UserAggregate user) {
        UserEntity entity = user.toEntity();
        int insert = writeRepo.insert(entity);
        log.info("save user entity: {}, effect rows: {} ", entity, insert);
        // todo 记录日志
        // todo 发布事件
        return user;
    }

    @Override
    public UserAggregate findUserByAccount(String account) {
        return readRepo.findUserByAccount(account)
                .map(UserAggregate::new)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void checkUsername(String username) {
        readRepo.findUserByAccount(username).ifPresent(user -> {
            throw new InvalidArgsException("用户名不可用：" + username);
        });
    }
}
