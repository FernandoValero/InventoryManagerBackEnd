package ar.com.manager.inventory.exception;

public class ExistsException extends RuntimeException {
    public ExistsException(String message) {
        super(message);
    }
}
