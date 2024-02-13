package orci.or.tz.overtime.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    Logger log = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),404);
        return new ResponseEntity<ApiError>(apiError,new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler(OperationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleOperationFailedException(OperationFailedException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),400);
        return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleEmailExistsException(EmailExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),400);
        return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleLoginFailedException(LoginFailedException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),400);
        return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(InActiveUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleInactiveUserException(InActiveUserException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),400);
        return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),400);
        return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
