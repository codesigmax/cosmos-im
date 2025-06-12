package com.qfleaf.cosmosimserver.user.application.services;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.user.application.commands.LoginByAccountCommand;
import com.qfleaf.cosmosimserver.user.application.commands.RegisterByAccountCommand;
import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.services.UserDomainService;
import com.qfleaf.cosmosimserver.user.infrastructure.external.EmailClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserDomainService domainService;
    private final EmailClient emailClient;

    @Transactional
    public void registerByAccount(RegisterByAccountCommand command) {

        // 执行领域逻辑
        UserAggregate user = domainService.saveUser(
                command.username(),
                command.password(),
                command.email(),
                command.nickname(),
                command.avatar()
        );
        // 记录日志
        log.info("user register by account: {}", user);

        // 发送欢迎邮件
        emailClient.sendWelcomeEmail(command.email());
    }

    public SaTokenInfo loginByAccount(LoginByAccountCommand command) {
        UserAggregate user = domainService.getUser(command.account());
        user.matchesPassword(command.password(), "用户名或密码错误");
        StpUtil.login(user.getUserId().value());
        return StpUtil.getTokenInfo();
    }
}
