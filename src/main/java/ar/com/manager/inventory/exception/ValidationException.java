package ar.com.manager.inventory.exception;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ValidationException(String message) {
        super(message);
    }
}
