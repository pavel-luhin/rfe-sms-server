package by.bsu.rfe.smsservice.util;

import by.bsu.rfe.smsservice.common.annotation.SMSProperty;
import by.bsu.rfe.smsservice.common.sms.base.BaseSMS;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pluhin on 3/20/16.
 */
public class MessageTemplateUtils {
    public static Map<String, String> getParametersFromSMS(BaseSMS sms) {
        Map<String, String> parameters = new HashMap<>();

        Class clazz = sms.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(SMSProperty.class)) {
                String templateParam = field.getAnnotation(SMSProperty.class).templateField();
                field.setAccessible(true);
                Object templateValue = null;
                try {
                    if (field.getType().equals(Date.class)) {
                        templateValue = convertDate((Date) field.get(sms));
                    } else {
                        templateValue = field.get(sms);
                    }
                } catch (IllegalAccessException e) {
                    //do nothing
                }
                if (templateValue != null) {
                    parameters.put(templateParam, (String) templateValue);
                }
            }
        }

        return parameters;
    }

    private static String convertDate(Date date) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
        String convertedDate = df.format(date);
        return convertedDate;
    }
}
