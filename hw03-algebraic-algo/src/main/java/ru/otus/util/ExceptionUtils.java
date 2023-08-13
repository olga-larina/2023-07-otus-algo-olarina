package ru.otus.util;

public class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static String getMessage(Throwable t) {
        if (t == null) {
            return "";
        } else {
            String prefix = t.getClass().getName() + ": ";
            if (t.getMessage() != null) {
                return prefix + t.getMessage();
            } else if (t.getCause() != null && t.getCause().getMessage() != null) {
                return prefix + t.getCause().getMessage();
            } else {
                return prefix + "No message";
            }
        }
    }
}