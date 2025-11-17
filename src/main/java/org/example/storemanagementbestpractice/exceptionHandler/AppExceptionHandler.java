package org.example.storemanagementbestpractice.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.util.GenerateErrorMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {
    private final GenerateErrorMessageUtil generateErrorMessageUtil;

    public AppExceptionHandler(GenerateErrorMessageUtil generateErrorMessageUtil) {
        this.generateErrorMessageUtil = generateErrorMessageUtil;
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        log.error("Authentication Exception: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        log.error("UsernameNotFoundException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception) {
        log.error("NoSuchElementException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("ValidationException: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        generateErrorMessageUtil
                                .getBindingErrorMessages(e.getBindingResult()));
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception exception) {
        log.error("Exception: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(exception.getMessage());
    }
}
