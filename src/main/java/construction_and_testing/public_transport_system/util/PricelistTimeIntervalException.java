package construction_and_testing.public_transport_system.util;

import org.springframework.http.HttpStatus;

public class PricelistTimeIntervalException extends RuntimeException {

    private HttpStatus httpStatus;

    public PricelistTimeIntervalException() {
    }

    public PricelistTimeIntervalException(String message, HttpStatus httpStatus) {
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
