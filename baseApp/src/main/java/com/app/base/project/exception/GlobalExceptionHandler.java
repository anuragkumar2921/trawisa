package com.app.base.project.exception;

import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.app.base.project.utils.apiResponse.ApiResponse;
import com.app.base.project.utils.apiResponse.ResponseHandler;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ApiResponse successApiResponse;

    @Autowired
    MultiLangMessage langMessage;

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        // Customize your error response here
        String errorMessage = "Invalid token signature: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationCredentialsNotFoundException ex) {
        // Customize your error response here
        String errorMessage = "Custom message for unauthenticated access: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> methodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        successApiResponse.setResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> noResourceFound(NoResourceFoundException ex) {
        successApiResponse.setResponse(false, ex.getResourcePath() + " " + langMessage.getLocalizeMessage("notFound"), HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleSqlUniqueKeyException(SQLIntegrityConstraintViolationException exception) {
        String errorMessage = exception.getMessage();
        successApiResponse.setResponse(false, errorMessage, HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        String message = exception.getMessage();

        successApiResponse.setErrorResponse(message, HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity<String> handleMissingHeaderException(MissingHeaderException e) {
        // Log the exception
        System.err.println("handleMissingHeaderException ==== " + e.getMessage());

        // Return a response with an error status and message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse> handleRequestParamException(MissingServletRequestPartException e) {
        // Log the exception
        System.err.println("handleMissingHeaderException ==== " + e.getMessage());

        successApiResponse.setErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgsNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> res = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            res.put(fieldName, message);
        });

        String errorMessage = null;
        String errorFieldName = null;

        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        if (!allErrors.isEmpty()) {
            FieldError firstError = (FieldError) allErrors.get(0);
            errorFieldName = firstError.getField();
            errorMessage = firstError.getDefaultMessage();

            Print.log("localization "+ errorMessage);
        }
        successApiResponse.setErrorResponse(errorMessage + " : " + errorFieldName, HttpStatus.BAD_REQUEST.value());
        return ResponseHandler.sendResponse(successApiResponse);
    }

/*
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(SignatureException exception) {
        String message = exception.getMessage();
        Print.log("Resource noit found");
        apiResponse.setErrorResponse(message, HttpStatus.NOT_ACCEPTABLE.value());
        return ResponseHandler.sendResponse(apiResponse);
    }
*/


}