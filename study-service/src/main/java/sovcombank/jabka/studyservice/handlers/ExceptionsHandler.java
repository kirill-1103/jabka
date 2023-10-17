package sovcombank.jabka.studyservice.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sovcombank.jabka.studyservice.exceptions.BadRequestException;
import sovcombank.jabka.studyservice.exceptions.ForbiddenException;
import sovcombank.jabka.studyservice.exceptions.NotFoundException;
import sovcombank.jabka.studyservice.exceptions.StateException;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage().isBlank() ? "Bad request!" : e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StateException.class)
    public ResponseEntity<?> handleStateException(StateException e) {
        return new ResponseEntity<>(e.getMessage().isBlank() ? "Bad request!" : e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handlerForibiddenException(ForbiddenException e) {
        return new ResponseEntity<>(e.getMessage().isBlank() ? "Foribidden error" : e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage().isBlank() ? "Not Fount Error" : e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
