package ru.stalser.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    private static Properties properties = null;

    public static Properties getPProps() {
        return properties;
    }

    public static String get(String propertyName) {

        if (properties.containsKey(propertyName)) {
            return properties.get(propertyName).toString();
        }
        return null;
    }

    public static String get(String propertyName, String defaultValues) {
        Object returnValue = get(propertyName);
        if (returnValue != null) {
            return returnValue.toString();
        }
        return defaultValues;
    }

    public static String getString(String propertyName) {

        return get(propertyName, "");
    }

    public static Integer getInteger(String propertyName) {
        Object returnValue = get(propertyName);
        if (returnValue != null) {
            return Integer.parseInt(returnValue.toString());
        }
        return 0;
    }

    public static long getLong(String propertyName) {
        Object returnValue = get(propertyName);
        if (returnValue != null) {
            return Long.parseLong(returnValue.toString());
        }
        return 0;
    }

    public static Object getObject(String propertyName) {

        return properties.get(propertyName);
    }

    public static void init() {

        if (properties == null) {
            properties = new Properties();
            try(FileInputStream fis = new FileInputStream("src/test/resources/config/application.properties")) {
                properties.load(fis);
                properties.putAll(System.getProperties());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
