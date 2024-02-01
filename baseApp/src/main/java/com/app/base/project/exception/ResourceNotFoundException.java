package com.app.base.project.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    String errorMsg;


    public ResourceNotFoundException(String errMsg, boolean isFormatted) {
//        super(String.format("Invalid data %s not found", errMsg));
        super(String.format("%s not found", errMsg));

    }

    public ResourceNotFoundException(String errorMsg) {
        super(errorMsg);
    }


}
