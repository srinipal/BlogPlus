package blog.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by srinivas.g on 22/11/16.
 */
public class BadRequestException extends RuntimeException{

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String errorMessage = null;

    public BadRequestException(String errorMessage, HttpStatus httpStatus){
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
