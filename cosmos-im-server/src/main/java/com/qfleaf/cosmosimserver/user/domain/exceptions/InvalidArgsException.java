package com.qfleaf.cosmosimserver.user.domain.exceptions;

import com.qfleaf.cosmosimserver.shared.exception.BaseException;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;

public class InvalidArgsException extends BaseException {
    public InvalidArgsException(String message) {
        super(ErrorCode.INVALID_ARGS_ERROR, message);
    }
}
