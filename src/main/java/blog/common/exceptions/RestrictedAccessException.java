package blog.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by srinivas.g on 27/11/16.
 */
public class RestrictedAccessException extends RuntimeException {
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String errorMessage = null;

    public RestrictedAccessException(String errorMessage, HttpStatus httpStatus){
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
