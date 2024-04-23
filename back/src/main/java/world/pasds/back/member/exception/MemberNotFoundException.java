package world.pasds.back.member.exception;

import world.pasds.back.common.exception.UnauthorizedException;

public class MemberNotFoundException extends UnauthorizedException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
