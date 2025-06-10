package com.qfleaf.cosmosimserver.user.application.services;

import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;
import com.qfleaf.cosmosimserver.shared.exception.SystemException;
import com.qfleaf.cosmosimserver.user.application.commands.ChangePasswordCommand;
import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import com.qfleaf.cosmosimserver.user.domain.services.UserDomainService;
import com.qfleaf.cosmosimserver.user.infrastructure.external.FileClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserOpsService {
    private final UserDomainService domainService;
    private final FileClient fileClient;

    public void changePassword(ChangePasswordCommand changePasswordCommand) {
        long loginId = StpUtil.getLoginIdAsLong();
        UserAggregate user = domainService.getUser(loginId);
        user.changePassword(changePasswordCommand.originalPassword(), changePasswordCommand.newPassword());
        domainService.saveUser(user);
    }

    public String uploadAvatar(MultipartFile file) {
        try {
            return fileClient.uploadFile(file);
        } catch (Exception e) {
            throw new SystemException(ErrorCode.INTERNAL_ERROR, "MinIO", "上传文件", e);
        }
    }
}
