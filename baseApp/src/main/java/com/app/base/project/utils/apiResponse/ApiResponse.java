package com.app.base.project.utils.apiResponse;


import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Primary
@Component
public class ApiResponse {
    private boolean status;
    private int statusCode;
    private String message;

    public ApiResponse() {

    }

    public ApiResponse(boolean status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }


    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public void setErrorResponse(String message, int statusCode){
        this.status = false;
        this.statusCode = statusCode;
        this.message = message;
    }

    public void setMessageResponse( String message, int statusCode){
        this.status = true;
        this.statusCode = statusCode;
        this.message = message;
    }

    public void setResponse(Boolean status,String message, int statusCode){
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

}
