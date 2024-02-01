package com.backend.trawisa.exception;


import com.app.base.project.base_config.LocalizationConfig;
import com.app.base.project.exception.GlobalExceptionHandler;
import com.app.base.project.exception.MissingHeaderException;
import com.app.base.project.utils.LocalizationUtils;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends GlobalExceptionHandler {
    @Autowired
    private ApiResponse successApiResponse;

    @Autowired
    private MultiLangMessage langMessage;

    @Autowired
    private LocalizationUtils localization;


    @ExceptionHandler(ApiValidationException.class)
    public ResponseEntity<ApiResponse> handleCustomApiValidation(ApiValidationException exception) {
        successApiResponse.setErrorResponse(exception.getErrorMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleInvalidRequest(HttpMessageNotReadableException exception) {
        String errorMessage = langMessage.getLocalizeMessage("invalidRequest");

        Print.log("HttpMessageNotReadableException ===== " + exception.getMessage());

        // Extract more specific details from the exception cause, if available
        if (exception.getCause() != null) {
            errorMessage += " Details: " + exception.getCause().getMessage();
        }
        successApiResponse.setErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse> handleJwtExpiredRequest(ExpiredJwtException exception) {

        Print.log("HttpMessageNotReadableException ===== " + exception.getMessage());

        // Extract more specific details from the exception cause, if available
        successApiResponse.setErrorResponse("Jwt Expired", HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity<String> handleMissingHeaderException(MissingHeaderException e) {
        // Log the exception
        System.err.println("handleMissingHeaderException ==== " + e.getMessage());

        // Retrieve the error response from the request attributes
//        String errorResponse = (String) request.getAttribute("errorResponse");

        // Return a response with an error status and message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("errorResponse");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingRequestParameterException(MissingServletRequestParameterException e) {
        // Log the exception
        System.err.println("handleMissingHeaderException ==== " + e.getMessage());

        // Retrieve the error response from the request attributes
//        String errorResponse = (String) request.getAttribute("errorResponse");

        // Return a response with an error status and message
        successApiResponse.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }


}
