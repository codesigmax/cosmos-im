package com.qfleaf.cosmosimserver.user.application.services;

import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.user.application.commands.ChangePasswordCommand;
import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.services.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOpsService {
    private final UserDomainService domainService;

    public void changePassword(ChangePasswordCommand changePasswordCommand) {
        long loginId = StpUtil.getLoginIdAsLong();
        UserAggregate user = domainService.getUser(loginId);
        user.changePassword(changePasswordCommand.originalPassword(), changePasswordCommand.newPassword());
        domainService.saveUser(user);
    }
}
