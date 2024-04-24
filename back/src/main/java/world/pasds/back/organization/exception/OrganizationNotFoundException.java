package world.pasds.back.organization.exception;

import world.pasds.back.common.exception.UnauthorizedException;

public class OrganizationNotFoundException extends UnauthorizedException {
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
