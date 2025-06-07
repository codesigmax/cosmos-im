package com.qfleaf.cosmosimserver.user.domain.exceptions;

import com.qfleaf.cosmosimserver.shared.exception.BaseException;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;

import java.time.Instant;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException() {
        super(ErrorCode.INVALID_CREDENTIAL, "原密码不匹配");
    }

    public PasswordMismatchException(String message) {
        super(ErrorCode.INVALID_CREDENTIAL, message);
    }
}
