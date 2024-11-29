package com.wanda.utils.exceptions.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class SuccessResponse<T> {
    private Boolean success;
    private String message;
    private Explanation explanation;
    private List<T> data= new ArrayList<>();

    public SuccessResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.explanation = new Explanation();
        this.data = Collections.singletonList(data);
    }

    public SuccessResponse(Boolean success, String message, List<T> data) {
        this.success = success;
        this.message = message;
        this.explanation = new Explanation();
        this.data = data;
    }

}
