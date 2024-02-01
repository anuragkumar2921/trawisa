package com.app.base.project.base_config;


import com.app.base.project.constant.BaseFinalConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocalizationConfig implements LocaleResolver {

    public static final List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("de")
    );


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader(BaseFinalConstant.API_HEADER.LANGUAGE);
        if (language == null || language.isEmpty()){
            return Locale.forLanguageTag("en");
        }

        Locale locale = Locale.forLanguageTag(language);

        if (LOCALES.contains(locale)){
            return locale;
        }
        return Locale.forLanguageTag("en");// default lang
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
