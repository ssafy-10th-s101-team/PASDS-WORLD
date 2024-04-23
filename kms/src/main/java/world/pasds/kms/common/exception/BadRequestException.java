package world.pasds.kms.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {

	public BadRequestException(final String message) {
		super(message);
	}

	@Override
	public HttpStatus status() {
		return HttpStatus.BAD_REQUEST;
	}
}
