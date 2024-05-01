package world.pasds.back.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_UNAUTHORIZED(401, "Unauthorized member control"),

    EMAIL_EXISTS(409, "존재하는 이메일입니다"),
    EMAIL_NOT_FOUND(404, "Email not found"),
    EMAIL_INVALID_FORMAT(400, "Email invalid format"),

    EMAIL_CODE_NOT_SAME(409, "Email code not same"),
    EMAIL_CODE_GENERATION_ERROR(500,"Email code generation error"),

    PASSWORD_SAME(409, "Password is same as before"),
    PASSWORD_INVALID_FORMAT(400, "비밀번호가 유효하지 않습니다"),
    PASSWORD_CONFIRM_INVALID(400, "비밀번호 확인이 일치하지 않습니다"),

    NICKNAME_INVALID_FORMAT(400, "닉네임이 유효하지 않습니다"),

    ORGANIZATION_NOT_FOUND(404, "Organization not found"),
    ORGANIZATION_UNAUTHORIZED(401, "Unauthorized organization control"),
    ORGANIZATION_MEMBER_NOT_FOUND(404, "Member not found in organization"),

    TEAM_NOT_FOUND(404, "Team not found"),
    TEAM_UNAUTHORIZED(401, "Unauthorized team control"),

    TEAM_MEMBER_NOT_FOUND(404, "Member not found in team"),
    TEAM_MEMBER_EXISTS(409, "Member already exists in team"),

    PRIVATE_DATA_UNAUTHORIZED(401, "Unauthorized PrivateData control"),
    PRIVATE_DATA_NOT_FOUND(404, "PrivateData not found"),


    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(503, "Forbidden"),

    TEAM_NAME_CONFLICT(403, "Team name already exists"),

    KEY_ERROR(500,"key error"),

    TOTP_CODE_GENERATION_ERROR(500,"Totp code generation error"),

    TOTP_CODE_NOT_SAME(400, "TOTP 코드를 잘못 입력했습니다\n" +
            "입력하신 내용을 다시 확인해주세요"),

    NOTIFICATION_NOT_FOUND(404, "Notification not found"),

    ROLE_NOT_FOUND(404, "Role not found"),
    ROLE_EXISTS(409, "Role exists"),

    INTERNAL_SERVER_ERROR(500,"Something wrong in server"),

    FIRST_LOGIN_AUTHENTICAT_FAIL(400,"이메일 또는 비밀번호를 잘못 입력했습니다\n" +
                                         "입력하신 내용을 다시 확인해주세요"),
    TEMPORARY_TOKEN_EXPIRED(401,"temporaryToken이 유효하지 않습니다"),
    REFRESH_TOKEN_EXPIRED(401,"refreshToken이 유효하지 않습니다");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}