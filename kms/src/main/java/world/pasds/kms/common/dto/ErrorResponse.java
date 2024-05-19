package world.pasds.kms.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
	private final HttpStatus status;
	private final String message;

	public ErrorResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
