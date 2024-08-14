package maids.cc.library_management_system.exception;

import lombok.Data;

@Data
public class RuntimeErrorCodedException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;
    public RuntimeErrorCodedException(ErrorCode errorCode,String message)
    {
        super();
        this.errorCode = errorCode;
        this.message=message;
    }
}
