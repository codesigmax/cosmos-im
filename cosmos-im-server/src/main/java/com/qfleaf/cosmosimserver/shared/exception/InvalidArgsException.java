package com.qfleaf.cosmosimserver.shared.exception;

public class InvalidArgsException extends BaseException {
    public InvalidArgsException(String message) {
        super(ErrorCode.INVALID_ARGS_ERROR, message);
    }
}
