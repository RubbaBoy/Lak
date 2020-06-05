package com.uddernetworks.lak.rest;

import java.util.Map;

public class EndpointUtility {

    public static <T> T getParam(String key, Class<T> type, Map<String, Object> data) {
        return getParam(key, type, data, null);
    }

    public static <T> T getParam(String key, Class<T> type, Map<String, Object> data, T def) {
        var obj = data.get(key);
        if (type.isInstance(obj)) {
            return (T) obj;
        }

        return def;
    }

}
