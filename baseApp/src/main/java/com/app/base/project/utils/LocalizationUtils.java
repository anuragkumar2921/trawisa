package com.app.base.project.utils;

import com.app.base.project.base_config.LocalizationConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocalizationUtils {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocalizationConfig localization;


    public String getMessage(String key, HttpServletRequest request) throws NoSuchMessageException {
        return messageSource.getMessage(key, null, localization.resolveLocale(request));
    }


    public String getMessage(String key) throws NoSuchMessageException {
        Locale locale = LocaleContextHolder.getLocale();
        Print.log("Current Locale ==> " + locale);
        return messageSource.getMessage(key, null, locale);
    }

    public String getMessage(String key, @Nullable Object[] args, HttpServletRequest request) throws NoSuchMessageException {
        return messageSource.getMessage(key, args, localization.resolveLocale(request));
    }

}
