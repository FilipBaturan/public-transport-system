package construction_and_testing.public_transport_system.domain.util;

import org.springframework.http.HttpStatus;

/**
 * General error exception
 */
public class ValidationException extends RuntimeException{

    private HttpStatus httpStatus;

    public ValidationException() {
    }

    public ValidationException(String message, HttpStatus httpStatus) {

        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
