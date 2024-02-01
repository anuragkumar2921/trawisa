package com.app.base.project.utils.apiResponse;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ApiDataResponse extends ApiResponse {
    private Object data;
    public ApiDataResponse(String message, Object data, int statusCode) {
        super(true,statusCode, message);
        this.data = data;
    }

    public ApiDataResponse(boolean status, String message, Object data, int statusCode) {
        super(status,statusCode, message);
        this.data = data;
    }



    public void setSuccessResponse(String message, Object data,int statusCode){
        super.setStatus(true);
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.data = data;
    }
    public void setErrorResponseData(String message, Object data,int statusCode){
        super.setStatus(false);
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.data = data;
    }


}
