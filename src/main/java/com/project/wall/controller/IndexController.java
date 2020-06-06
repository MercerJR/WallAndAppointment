package com.project.wall.controller;

import com.project.wall.data.HttpInfo;
import com.project.wall.po.User;
import com.project.wall.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author MercerJR
 * @Data 2020/4/12 10:51
 */
@RestController
public class IndexController {

    @Autowired
    private UserServiceImpl service;

    @GetMapping(value = "/")
    public void index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (HttpInfo.USER_ID_COOKIE.equals(cookie.getName())) {
                    String accountId = cookie.getValue();
                    User user = service.findUserById(accountId);
                    if (user != null) {
                        request.getSession().setAttribute(HttpInfo.USER_SESSION, user);
                    }
                    break;
                }
            }
        }
    }
}