package world.pasds.back.totp.exception;

import world.pasds.back.common.exception.NotFoundException;

public class TotpKeyNotExistsException extends NotFoundException {

	public TotpKeyNotExistsException(String msg) {
		super(msg);
	}
}
