package delivery.calculation.costComputer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Item Rejected")
public class RejectItemException extends RuntimeException{
    public RejectItemException() {
    }

    public RejectItemException(String message) {
        super(message);
    }

    public RejectItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public RejectItemException(Throwable cause) {
        super(cause);
    }

    public RejectItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
