package com.project.wall.controller;

import com.project.wall.data.*;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.UserToken;
import com.project.wall.service.RedisService;
import com.project.wall.service.UserService;
import com.project.wall.utils.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author MercerJR
 * @Data 2020/5/9 11:07
 */
@Slf4j
@RestController
@Validated
public class WxLoginController {

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/wxLogin", produces = "application/json")
    public Response wxLogin(@RequestBody WxParams params , HttpServletRequest request) {
        //如果tokenKey存在，就返回
        //先查redis，再查mysql
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        accountId = accountId == null ? "" : accountId;
        if (redisService.existHashKey(HttpInfo.USER_TOKEN,accountId)){
            WxTokenInfo tokenInfo = redisService.selectHashValByKey(HttpInfo.USER_TOKEN,accountId);
            UserToken userToken = new UserToken(accountId,tokenInfo.getOpenId(),tokenInfo.getSessionKey());
            request.getSession().setAttribute(HttpInfo.USER_SESSION,userToken);
            return new Response().success(accountId);
        }else {
            UserToken userToken = userService.getUserToken(accountId);
            if (userToken != null){
                WxTokenInfo tokenInfo = new WxTokenInfo(userToken.getSessionKey(),userToken.getOpenId());
                redisService.insertHash(HttpInfo.USER_TOKEN,accountId,tokenInfo);
                request.getSession().setAttribute(HttpInfo.USER_SESSION,userToken);
                return new Response().success(accountId);
            }
        }
        //若没有查到，就访问微信的接口
        WxUserToken wxUserToken = codeUtil.getUserToken(params);
        if (wxUserToken.getErrcode() != 0) {
            throw new CustomException(CustomExceptionType.WX_CODE_ERROR, wxUserToken.getErrmsg());
        }
        String key = UUID.randomUUID().toString();
        UserToken userToken = new UserToken(key,wxUserToken.getOpenid(),wxUserToken.getSession_key());
        WxTokenInfo tokenInfo = new WxTokenInfo(wxUserToken.getSession_key(), wxUserToken.getOpenid());
        if (userService.getUserToken(key) != null) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
        userService.insertUserToken(userToken);
        redisService.insertHash(HttpInfo.USER_TOKEN,key,tokenInfo);
        return new Response().success(key);
    }
}
