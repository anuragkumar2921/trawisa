package com.app.base.project.utils;

import com.app.base.project.constant.BaseConstant;
import com.app.base.project.constant.BaseFinalConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Data
@Component
public class MultiLangMessage {
    public String getLocalizeMessage(String key) {
        return getMultiLangMessage(BaseConstant.APP_LANGUAGE, key);
    }

    private String getMultiLangMessage(String lang, String key) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream jsonStream = null;

            if (lang.equalsIgnoreCase("en")) {
                jsonStream = getClass().getResourceAsStream("/string/string_en.json");
            } else if (lang.equalsIgnoreCase("de")) {
                jsonStream = getClass().getResourceAsStream("/string/string_de.json");
            }

            if (jsonStream != null) {
                JsonNode rootNode = objectMapper.readTree(jsonStream);
                JsonNode messageNode = rootNode.get("message");

                if (messageNode != null && messageNode.has(key)) {
                    return messageNode.get(key).asText();
                } else {
                    return "Invalid message key";
                }
            } else {
                return "Invalid localization language";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "No message found";
    }

}
