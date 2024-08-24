package likelion.sku_sku.exception;

public class JwtValidationException extends RuntimeException {

    // jwt 오류
    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
