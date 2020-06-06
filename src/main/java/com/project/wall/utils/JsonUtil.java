package com.project.wall.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    public Object toObject(String jsonString, Class cls) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, cls);
    }

}
