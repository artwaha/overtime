package orci.or.tz.overtime.exceptions;


import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiError {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private HttpStatus status;
    private String message;


    public ApiError(HttpStatus status, String message, Integer code) {
        super();
        status = status;
        message = message;
        code  = code;

    }



    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
