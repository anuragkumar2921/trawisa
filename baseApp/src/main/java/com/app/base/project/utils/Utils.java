package com.app.base.project.utils;

import com.app.base.project.constant.BaseConstant;
import com.app.base.project.constant.BaseFinalConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;

import static com.app.base.project.constant.BaseConstant.ERROR_MSG_PARAM;

public class Utils {

 /*   public static String checkHeader(HttpServletResponse response, HttpServletRequest httpRequest, String header) {

        String data = httpRequest.getHeader(header);
        if (data == null || data.isEmpty()) {
            // If the header is missing, you can throw an exception or set an error response
            HttpServletResponse httpResponse = response;
            http
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.setContentType(BaseFinalConstant.API_HEADER.Content_TYPE_JSON);
            try {
                httpResponse.getWriter().write(StringUtils.getFormattedString("{ \"error\": true, \"message\": \"Missing %s in header\" }", header));
                //http
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            BaseConstant.APP_LANGUAGE = data;
        }

        return data;
    }*/

   /* public static String checkHeader(HttpServletResponse response, HttpServletRequest httpRequest, String header) throws MissingHeaderException {
        String data = httpRequest.getHeader(header);
        if (data == null || data.isEmpty()) {
            // If the header is missing, throw a custom exception

            try {
                response.setStatus(666);
                response.sendError(666,"Invalid header");
            } catch (IOException e) {
                Print.log("checkHeader IOException "+e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }

//            throw new MissingHeaderException("Missing " + header + " in header");
        } else {
            BaseConstant.APP_LANGUAGE = data;
        }

        return data;
    }*/

    public static void WriteErrorResponse(HttpServletResponse response, String errorMsg) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(StringUtils.getFormattedString(ERROR_MSG_PARAM, errorMsg));
        } catch (IOException e) {
            Print.log("WriteErrorResponse === " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String checkLanguageHeader(HttpServletResponse response, HttpServletRequest httpRequest) {
        String data = httpRequest.getHeader(BaseFinalConstant.API_HEADER.LANGUAGE);
        if (data == null || data.isEmpty()) {
            // If the header is missing, handle the error and set the response
            String errorMessage = "Missing " + BaseFinalConstant.API_HEADER.LANGUAGE + " in header";
            WriteErrorResponse(response, errorMessage);
        } else if (data.equals(BaseFinalConstant.SUPPORTED_LANG.ENGLISH) || data.equals(BaseFinalConstant.SUPPORTED_LANG.GERMAN)) {
            BaseConstant.APP_LANGUAGE = data;
        } else {
            WriteErrorResponse(response, BaseFinalConstant.API_HEADER.LANGUAGE + " Not supported");
        }

        return data;
    }

    public static boolean isNullOrEmpty(String message) {
        return message == null || message.trim().isEmpty() || message.equals("0");

    }

    public static boolean isNN(String str) {
        boolean flag = false;
        if (str != null && str.length() > 0 && !str.equalsIgnoreCase("null")) {
            flag = true;
        }
        return flag;
    }

}
