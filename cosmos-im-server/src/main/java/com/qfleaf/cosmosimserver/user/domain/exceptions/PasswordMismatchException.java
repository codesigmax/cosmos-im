package com.qfleaf.cosmosimserver.user.domain.exceptions;

import com.qfleaf.cosmosimserver.shared.exception.BaseException;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;

public class PasswordMismatchException extends BaseException {

    public PasswordMismatchException(String message) {
        super(ErrorCode.INVALID_CREDENTIAL, message.isBlank() ? "密码不匹配" : message);
    }
}
