package world.pasds.back.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    EMAIL_EXISTS(409, "Email exists"),
    EMAIL_NOT_FOUND(404, "Email not found"),
    SAME_PASSWORD(409, "Password is same as before"),
    ORGANIZATION_NOT_FOUND(404,"Organization not found"),
    ORGANIZATION_UNAUTHORIZED(401,"Unauthorized organization control"),
    TEAM_NOT_FOUND(404,"Team not found"),
    TEAM_UNAUTHORIZED(401,"Unauthorized team control"),
    PRIVATE_DATA_UNAUTHORIZED(401,"Unauthorized PrivateData control"),
    MEMBER_UNAUTHORIZED(401,"Unauthorized member control"),

    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(503,"Forbidden");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}