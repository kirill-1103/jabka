package sovcombank.jabka.userservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sovcombank.jabka.userservice.exception.BadRequestException;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage().isBlank() ? "Bad request!" : e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
