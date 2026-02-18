package com.powerfit.utils;

/**
 * Clase auxiliar para parsear parámetros de consulta
 */
public class QueryParser {
    
    public static String getParameter(String query, String name) {
        if (query == null || query.isEmpty()) {
            return null;
        }
        
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(name)) {
                try {
                    return java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                } catch (Exception e) {
                    return keyValue[1];
                }
            }
        }
        
        return null;
    }

    public static int getIntParameter(String query, String name, int defaultValue) {
        String value = getParameter(query, name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}
