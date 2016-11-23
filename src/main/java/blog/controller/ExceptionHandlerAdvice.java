package blog.controller;

import blog.common.exceptions.BlogApplicationEx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by srinivas.g on 22/11/16.
 */

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(BlogApplicationEx.class)
    public ResponseEntity handleException(BlogApplicationEx ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorMessage());
    }
}
