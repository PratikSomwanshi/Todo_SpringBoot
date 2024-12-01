package com.wanda.utils.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends  RuntimeException{

    private HttpStatus statusCode;
    private String code;


    public CustomException(String msg, HttpStatus statusCode, String code) {
        super(msg);
        this.statusCode = statusCode;
        this.code = code;
    }

    public CustomException(String msg){
        super(msg);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
