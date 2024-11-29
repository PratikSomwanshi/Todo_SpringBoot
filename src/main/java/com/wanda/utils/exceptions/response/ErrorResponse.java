package com.wanda.utils.exceptions.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private Boolean success;
    private String message;
    private Explanation error;
    private List<?> data= new ArrayList<>();

    public ErrorResponse(Boolean success, String message, String explanation) {
        this.success = success;
        this.message = message;
        this.error = new Explanation(explanation);
    }
}


@Getter
@Setter
class Explanation{
    private String explanation;

    public Explanation() {}

    public Explanation(String explanation){
        this.explanation = explanation;
    }
}