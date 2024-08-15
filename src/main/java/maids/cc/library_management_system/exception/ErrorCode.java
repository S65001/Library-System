package maids.cc.library_management_system.exception;


public enum ErrorCode {
    USER_NOT_FOUND_EXCEPTION(1),
    EMAIL_ALREADY_EXISTS(2),
    INVALID_AUTHENTICATION_TOKEN(3),
    INVALID_REQUEST_BODY(4),
    AUTHENTICATION_EXCEPTION(5),
    BOOK_NOT_FOUND(6),
    PATRON_NOT_FOUND(7),
    RECORD_NOT_FOUND(8),
    LOGICAL_ERROR(9),
    UNKNOWN_SERVER_ERROR(100),
    DEACTIVATED_ACCOUNT(10);

    private final int code;
    ErrorCode(int code)
    {
        this.code = code;

    }

    public int getCode()
    {
        return code;
    }



}

