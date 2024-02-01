package com.app.base.project.utils;


import com.app.base.project.constant.BaseConstant;
import com.app.base.project.constant.BaseFinalConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;


import static com.app.base.project.utils.Utils.checkLanguageHeader;


@Data
@Component
public class InvalidUrlFilter implements Filter {

    @Autowired
    MultiLangMessage multiLangMessage;

    @Autowired
    public InvalidUrlFilter(MultiLangMessage multiLangMessage) {
        this.multiLangMessage = multiLangMessage;
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        if (!(request instanceof HttpServletRequest httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String requestURI = httpRequest.getRequestURI();
        String language = checkLanguageHeader((HttpServletResponse) response, httpRequest);
        if (language != null) {
            BaseConstant.APP_LANGUAGE = language;
        }

        // Check for invalid URLs here and throw a specific exception

        if (!isValidUrl(requestURI)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            httpResponse.setContentType(BaseFinalConstant.API_HEADER.Content_TYPE_JSON);
//            httpResponse.getWriter().write(StringUtils.getFormattedString(ERROR_MSG_PARAM, multiLangMessage.getLocalizeMessage( "invalidUrl")));
            return;
        }

        chain.doFilter(request, response);
    }


    private boolean isValidUrl(String url) {
        return url.startsWith("/api/v1");
    }


}