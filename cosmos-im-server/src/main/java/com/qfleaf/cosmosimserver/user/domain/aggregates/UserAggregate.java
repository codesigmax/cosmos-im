package com.qfleaf.cosmosimserver.user.domain.aggregates;

import com.qfleaf.cosmosimserver.core.domain.DomainEvent;
import com.qfleaf.cosmosimserver.core.domain.base.BaseAggregateRoot;
import com.qfleaf.cosmosimserver.core.domain.enums.AccountStatus;
import com.qfleaf.cosmosimserver.user.domain.entities.UserEntity;
import com.qfleaf.cosmosimserver.user.domain.event.UserPasswordChangedEvent;
import com.qfleaf.cosmosimserver.user.domain.event.UserProfileUpdatedEvent;
import com.qfleaf.cosmosimserver.user.domain.event.UserRegisteredEvent;
import com.qfleaf.cosmosimserver.user.domain.exceptions.PasswordMismatchException;
import com.qfleaf.cosmosimserver.user.domain.valueobjects.Email;
import com.qfleaf.cosmosimserver.user.domain.valueobjects.EncryptedPassword;
import com.qfleaf.cosmosimserver.user.domain.valueobjects.UserId;
import com.qfleaf.cosmosimserver.user.domain.valueobjects.Username;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collection;

@Getter
public class UserAggregate extends BaseAggregateRoot {
    // 使用自定义ID类型增强类型安全
    private UserId userId;

    // 值对象包装核心属性
    private Username username;
    private EncryptedPassword password;
    private Email email;

    // 普通属性
    private String nickname;
    private String avatarUrl;
    private AccountStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private UserAggregate() {
    }

    public UserAggregate(UserEntity entity) {
        userId = new UserId(entity.getId());
        username = new Username(entity.getUsername());
        password = new EncryptedPassword(entity.getPassword());
        email = new Email(entity.getEmail());
        nickname = entity.getNickname();
        avatarUrl = entity.getAvatar();
        status = entity.getStatus();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }

    // 领域行为方法
    public void changePassword(String original, String newPassword, PasswordEncoder encoder) {
        if (!this.password.matches(original, encoder)) {
            throw new PasswordMismatchException();
        }
        this.password = new EncryptedPassword(encoder.encode(newPassword));
        registerEvent(new UserPasswordChangedEvent(userId));
    }

    public void updateProfile(String nickname, String avatarUrl) {
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        registerEvent(new UserProfileUpdatedEvent(userId));
    }

    // 工厂方法
    public static UserAggregate createUser(
            String username,
            String plainPassword,
            String email,
            String nickname,
            String avatar,
            PasswordEncoder encoder) {
        UserAggregate user = new UserAggregate();
        user.userId = UserId.newId();
        user.username = new Username(username);
        user.password = new EncryptedPassword(encoder.encode(plainPassword));
        user.email = new Email(email);
        user.nickname = nickname;
        user.avatarUrl = avatar;
        user.status = AccountStatus.ACTIVE;
        user.createdAt = Instant.now();

        // 注册领域事件
        user.registerEvent(new UserRegisteredEvent(user.getUserId().value(), user.getUsername().value()));
        return user;
    }

    @Override
    public Collection<DomainEvent> domainEvents() {
        return super.getDomainEvents();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(userId.value())
                .username(username.value())
                .password(password.getValue())
                .email(email.value())
                .nickname(nickname)
                .avatar(avatarUrl)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
