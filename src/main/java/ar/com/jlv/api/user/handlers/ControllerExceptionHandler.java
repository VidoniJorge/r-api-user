package ar.com.jlv.api.user.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ar.com.jlv.api.user.dtos.ErrorDTO;
import ar.com.jlv.api.user.exceptions.ExistedEmailException;
import ar.com.jlv.api.user.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorDTO> handleException(final Exception ex) {
		return buildResponseEntity(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<ErrorDTO> handleExceptionNotFound(final UserNotFoundException ex) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ExistedEmailException.class)
	protected ResponseEntity<ErrorDTO> handleExceptionNotFound(final ExistedEmailException ex) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<ErrorDTO> buildResponseEntity(final String msg, final HttpStatus status) {
		log.error(msg);
		return new ResponseEntity<>(ErrorDTO.builder().message(msg).build(), status);
	}
}
