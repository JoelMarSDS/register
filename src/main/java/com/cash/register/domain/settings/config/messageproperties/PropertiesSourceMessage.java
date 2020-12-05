package com.cash.register.domain.settings.config.messageproperties;

import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
public class PropertiesSourceMessage {

    private static final ResourceBundle MSG_PROPERTIES = ResourceBundle.getBundle("messages");

    public static String getMessageSource(String message) {
        if (MSG_PROPERTIES.containsKey(message)) {
            return MSG_PROPERTIES.getString(message);
        }
        return "";
    }
}
