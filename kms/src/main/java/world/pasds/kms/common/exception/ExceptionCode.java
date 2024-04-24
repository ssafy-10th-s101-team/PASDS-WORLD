package world.pasds.kms.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    INTERNAL_SERVER_ERROR(500, "internal server error");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}