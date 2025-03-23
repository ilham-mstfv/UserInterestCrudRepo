package com.example.userinterestcrudrepo.exceptionHandlers;

import com.example.userinterestcrudrepo.exceptions.InsertUserException;
import com.example.userinterestcrudrepo.exceptions.NoUsersFoundException;
import com.example.userinterestcrudrepo.models.ApiResponse;
import com.example.userinterestcrudrepo.models.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // TODO: Добавить обработку ошибок БД

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ApiResponse<String>> buildResponseEntity(StatusCode error, Exception exception){
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(ApiResponse.error(
                        error.getCode(),
                        error.getMessage(),
                        exception.getMessage()
                                + Optional.ofNullable(exception.getCause())
                                            .map(cause -> "; Exception message: " + cause.getMessage())
                                            .orElse("")
                ));
    }


    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoUsersFoundException(NoUsersFoundException exception){
        StatusCode error = StatusCode.SERVER_ERROR;
        logger.error(exception.getMessage());

        return this.buildResponseEntity(error, exception);
    }

    @ExceptionHandler(InsertUserException.class)
    public ResponseEntity<ApiResponse<String>> handleInsertUserException(InsertUserException exception){
        StatusCode error = StatusCode.SERVER_ERROR;
        logger.error(exception.getMessage());

        return this.buildResponseEntity(error, exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StatusCode error = StatusCode.VALIDATION_ERROR;
        logger.warn(error.getMessage(), exception);

        return this.buildResponseEntity(error, exception);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleJsonParseException(HttpMessageNotReadableException exception) {
        StatusCode error = StatusCode.JSON_VALIDATION_ERROR;
        logger.warn(error.getMessage(), exception);

        return this.buildResponseEntity(error, exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception exception) {
        StatusCode error = StatusCode.SERVER_ERROR;
        logger.error(error.getMessage(), exception);

        return this.buildResponseEntity(error, exception);
    }
}
