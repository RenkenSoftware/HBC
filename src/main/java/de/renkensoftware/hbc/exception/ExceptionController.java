package de.renkensoftware.hbc.exception;

import de.renkensoftware.hbc.domain.authentication.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseError> handleUserNotFoundException() {
        return new ResponseEntity<>(new ResponseError(1, "User not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ResponseError> handleInvalidPasswordException() {
        return new ResponseEntity<>(new ResponseError(2, "Invalid password"), HttpStatus.NOT_ACCEPTABLE);
    }
}
