package com.wanda.utils.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends  RuntimeException{

    private HttpStatus statusCode;


    public CustomException(String msg, HttpStatus statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public CustomException(String msg){
        super(msg);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
