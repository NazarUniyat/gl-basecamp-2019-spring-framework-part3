package owu.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import owu.exceptions.DatabaseException;
import owu.wire.Message;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Message> recordNotFoundException(DatabaseException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Message(HttpStatus.NOT_FOUND.toString(),e.getMessage()));
    }


    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<String> handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(strBuilder.toString());
    }
}
