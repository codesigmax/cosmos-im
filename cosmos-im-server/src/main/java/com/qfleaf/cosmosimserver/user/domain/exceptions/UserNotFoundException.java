package com.qfleaf.cosmosimserver.user.domain.exceptions;

import com.qfleaf.cosmosimserver.shared.exception.BaseException;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND, "用户不存在");
    }
}
