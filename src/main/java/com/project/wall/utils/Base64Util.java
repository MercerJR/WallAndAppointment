package com.project.wall.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author MercerJR
 * @Data 2020/4/10 12:35
 */
@Component
public class Base64Util {

    final static Base64.Encoder encoder = Base64.getEncoder();
    final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * @param text
     * @return 加密后的文本
     */
    public String encode(String text){
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param text
     * @return 解密后的文本
     */
    public String decode(String text){
        return new String(decoder.decode(text),StandardCharsets.UTF_8);
    }

}
