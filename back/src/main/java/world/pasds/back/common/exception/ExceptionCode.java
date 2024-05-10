package world.pasds.back.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    AUTHORITY_NOT_FOUND(404, "권한을 찾을수 없습니다."),
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    MEMBER_EXISTS(409, "이미 존재하는 회원입니다."),
    MEMBER_UNAUTHORIZED(401, "권한이 없는 회원입니다."),

    EMAIL_EXISTS(409, "존재하는 이메일입니다"),
    EMAIL_NOT_FOUND(404, "이메일을 찾을수 없습니다."),
    EMAIL_INVALID_FORMAT(400, "잘못된 이메일 양식입니다."),
    EMAIL_IS_NOT_SAME(401,"인증받은 이메일과 다릅니다."),
    EMAIL_SENDER_ERROR(500,"이메일 센더 에러입니다. 개발자에게 연락해주세요"),

    EMAIL_CODE_NOT_SAME(409, "이메일 인증코드가 다릅니다."),
    EMAIL_CODE_GENERATION_ERROR(500, "이메일 인증코드 발급에 문제가 발생했습니다."),
    EMAIL_CODE_EXPIRED(400,"이메일 코드가 만료되었습니다."),
    EMAIL_AUTHENTICATION_ALREADY(400,"이미 인증된 이메일입니다."),

    PASSWORD_SAME(409, "비밀번호가 이전 비밀번호와 같습니다."),
    PASSWORD_INVALID_FORMAT(400, "비밀번호가 유효하지 않습니다"),
    PASSWORD_CONFIRM_INVALID(400, "비밀번호 확인이 일치하지 않습니다"),
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),

    NICKNAME_INVALID_FORMAT(400, "닉네임이 유효하지 않습니다"),

    ORGANIZATION_NOT_FOUND(404, "조직을 찾을 수 없습니다."),
    ORGANIZATION_UNAUTHORIZED(401, "권한이 없는 조직입니다."),
    ORGANIZATION_MEMBER_NOT_FOUND(404, "조직에서 회원을 찾을 수 없습니다."),

    TEAM_NOT_FOUND(404, "팀을 찾을 수 없습니다."),
    TEAM_UNAUTHORIZED(401, "권한이 없는 팀입니다."),

    TEAM_MEMBER_NOT_FOUND(404, "팀에서 회원을 찾을수 없습니다."),
    TEAM_MEMBER_EXISTS(409, "팀에 회원이 이미 존재합니다."),

    PRIVATE_DATA_UNAUTHORIZED(401, "권한이 없는 프라이빗 데이터입니다."),
    PRIVATE_DATA_NOT_FOUND(404, "프라이빗 데이터를 찾을 수 없습니다."),

    BAD_REQUEST(400, "잘못된 요청입니다."),
    FORBIDDEN(503, "금지된 접근입니다."),

    TEAM_NAME_CONFLICT(403, "팀 명이 이미 존재합니다."),

    AUTHORITY_NAME_CONFLICT(403, "권한 이름이 이미 존재합니다."),

    KEY_ERROR(500,"키 관련해서 문제가 발생했습니다."),

    TOTP_CODE_GENERATION_ERROR(500, "TOTP 코드 발급시 문제가 발생했습니다."),

    TOTP_CODE_NOT_SAME(400, "TOTP 코드를 잘못 입력했습니다\n" +
            "입력하신 내용을 다시 확인해주세요"),

    NOTIFICATION_NOT_FOUND(404, "알림을 찾을 수 없습니다."),

    ROLE_NOT_FOUND(404, "역할을 찾을 수 없습니다."),
    ROLE_EXISTS(409, "역할이 이미 존재합니다."),
    ROLE_UNAUTHORIZED(401, "해당 역할은 변경이 불가능합니다."),
    ROLE_MEMBER_EXISTS(403, "해당 역할을 가진 팀원이 존재합니다.\n" +
            "해당 역할을 가진 팀원이 존재하지 않는 경우만 역할 삭제가 가능합니다."),

    INTERNAL_SERVER_ERROR(500, "서버에 문제가 발생했습니다."),

    FIRST_LOGIN_AUTHENTICATION_FAIL(400, "이메일 또는 비밀번호를 잘못 입력했습니다\n" +
            "입력하신 내용을 다시 확인해주세요"),


    // JWT 공간입니다
    INVALID_SIGNATURE(401, "유효하지 않은 서명입니다."),
    EMAIL_INVALID_SIGNATURE(401, "유효하지 않은 이메일 토큰 서명입니다."),
    TEMPORARY_INVALID_SIGNATURE(401, "유효하지않은 임시 서명입니다."),
    ACCESS_INVALID_SIGNATURE(401, "엑세스토큰의 서명이 유효하지 않습니다."),
    REFRESH_INVALID_SIGNATURE(401, "재발급토큰의 서명이 유효하지 않습니다."),

    TOKEN_EXPIRED(401, "토큰이 만료됐습니다."),
    EMAIL_TOKEN_EXPIRED(401, "이메일 토큰이 만료됐습니다."),
    TEMPORARY_TOKEN_EXPIRED(401, "임시토큰이 만료됐습니다."),
//    ACCESS_TOKEN_EXPIRED(401, "ACCESS_TOKEN_EXPIRED"),
    REFRESH_TOKEN_EXPIRED(401, "재발급토큰이 만료됐습니다."),

    TOKEN_NOT_FOUND(401, "토큰을 찾을 수 없습니다."),
    EMAIL_TOKEN_NOT_FOUND(401, "이메일 토큰을 찾을 수 없습니다."),
    TEMPORARY_TOKEN_NOT_FOUND(401, "임시 토큰을 찾을 수 없습니다."),
    ACCESS_TOKEN_NOT_FOUND(401, "엑세스토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "재발급토큰을 찾을 수 없습니다."),

    TOKEN_MISMATCH(401, "토큰이 맞지 않습니다."),
    EMAIL_TOKEN_MISMATCH(401, "이메일 토큰이 맞지 않습니다."),
    TEMPORARY_TOKEN_MISMATCH(401, "임시 토큰이 맞지 않습니다."),
    ACCESS_TOKEN_MISMATCH(401, "엑세스 토큰이 맞지 않습니다."),
    REFRESH_TOKEN_MISMATCH(401, "재발급 토큰이 맞지 않습니다."),

    EMAIL_COOKIE_NOT_FOUND(401,"이메일 쿠키를 찾을 수 없습니다."),
    TEMPORARY_COOKIE_NOT_FOUND(401,"임시 쿠키를 찾을 수 없습니다."),
    ACCESS_COOKIE_NOT_FOUND(401,"엑세스 쿠키를 찾을 수 없습니다."),
    REFRESH_COOKIE_NOT_FOUND(401,"재발급 쿠키를 찾을 수 없습니다."),
    MY_ORGANIZATION_INVITATION(400, "MY_ORGANIZATION에는 조직원을 초대할 수 없습니다."),
    NO_HEADER(400, "조직장 역할은 위임 외에 설정할 수 없습니다."),
    ALREADY_ORGANIZATION_MEMBER(400, "이미 우리 조직입니다."),

    TEAM_DASHBOARD_NOT_FOUNT(401, "팀 대시보드를 찾을 수 없습니다."),
    ORGANIZATION_DASHBOARD_NOT_FOUNT(401, "조직 대시보드를 찾을 수 없습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}