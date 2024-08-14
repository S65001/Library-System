package maids.cc.library_management_system.exception;

import lombok.Data;

@Data
public class ErrorDetails
{

    private Integer code;
    private long timeStamp;
    private Object details;
}
