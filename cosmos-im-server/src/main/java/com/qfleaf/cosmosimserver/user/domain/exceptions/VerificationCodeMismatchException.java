package com.qfleaf.cosmosimserver.user.domain.exceptions;

import com.qfleaf.cosmosimserver.shared.exception.BaseException;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;

public class VerificationCodeMismatchException extends BaseException {
    public VerificationCodeMismatchException() {
        super(ErrorCode.INVALID_CREDENTIAL, "验证码不匹配");
    }
}
