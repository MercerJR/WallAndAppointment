package com.project.wall.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wall.data.HttpInfo;
import com.project.wall.data.WxParams;
import com.project.wall.data.WxUserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author MercerJR
 * @Data 2020/5/9 11:19
 */
@Component
@Slf4j
public class CodeUtil {

    public WxUserToken getUserToken(WxParams params){
        RestTemplate restTemplate = new RestTemplate();
        String param = "appid=" + params.getAppId() +
                "&secret=" + params.getSecret() +
                "&js_code=" + params.getCode() +
                "&grant_type=" + params.getGrantType();
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + param;
        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        String responseBody = (String) response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        WxUserToken userToken = null;
        try {
            userToken = objectMapper.readValue(responseBody,WxUserToken.class);
            log.info(userToken.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userToken;
    }


}
