package kr.onthelive.basic.ws.exception;

public class BasicException extends RuntimeException {
    private ErrorCode code;
    
    public BasicException(ErrorCode code, String message) {
        super(message);
        
        this.code = code;
    }
    
    public ErrorCode getErrorCode() {
        return this.code;
    }
}