package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对象转json
     */
    public static String toJSON(Object target){
        String result = null;
        try {
            result = MAPPER.writeValueAsString(target);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * json转对象
     */
    public static <T> T toObject(String json,Class<T> targetClass){
        T object = null;
        try {
            object = MAPPER.readValue(json,targetClass);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return object;
    }

}
