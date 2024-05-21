package world.pasds.kms.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    NO_MASTERKEY_CACHE_FOUND(500, "캐시에서 cur마스터키를 찾을 수 없습니다."),
    KEY_GENERATE_FAIL(500, "키 생성에 실패했습니다."),
    ENCRYPTION_FAIL(500, "암호화에 실패했습니다."),
    DECRYPTION_FAIL(500, "복호화에 실패했습니다."),
    KEY_EXPIRED(500,"master key version expired" );

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}