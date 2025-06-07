package com.qfleaf.cosmosimserver.user.application.services;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.user.application.commands.LoginByAccountCommand;
import com.qfleaf.cosmosimserver.user.application.commands.RegisterByAccountCommand;
import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.services.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthAppService {
    private final UserDomainService domainService;
//    private final EmailClient emailClient; todo集成邮箱

    @Transactional
    public void registerByAccount(RegisterByAccountCommand command) {
        // 1. 校验验证码
//        if (!emailClient.verifyCode(command.email(), command.verificationCode())) {
//            throw new VerificationCodeMismatchException();
//        }

        // 2. 执行领域逻辑
        UserAggregate user = domainService.register(
                command.username(),
                command.password(),
                command.email(),
                command.nickname(),
                command.avatar()
        );
        // 3. 记录日志
        log.info("user register by account: {}", user);
    }

    public SaTokenInfo loginByAccount(LoginByAccountCommand command) {
        UserAggregate user = domainService.login(command.account(), command.password());
        StpUtil.login(user.getUserId().value());
        return StpUtil.getTokenInfo();
    }
}
