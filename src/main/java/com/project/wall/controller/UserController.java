package com.project.wall.controller;

import com.project.wall.data.HttpInfo;
import com.project.wall.data.Message;
import com.project.wall.data.Response;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.User;
import com.project.wall.service.impl.UserServiceImpl;
import com.project.wall.utils.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * @Author MercerJR
 * @Data 2020/4/10 11:30
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private Base64Util base64Util;

    @PostMapping(value = "/register", produces = "application/json")
    public Response register(@NotEmpty(message = Message.USERNAME_NULL)
                             @RequestParam String username,
                             @Size(min = 8, max = 30, message = Message.PASSWORD_LIMIT)
                             @RequestParam String password,
                             @RequestParam String school,
                             @RequestParam String telephone) {
        if (!service.existUser(username) && !service.existPhone(telephone)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(base64Util.encode(password));
            user.setSchool(school);
            user.setTelephone(telephone);
            user.setAccountId(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            service.insertUser(user);
            return new Response().success();
        } else {
            if (service.existUser(username)) {
                throw new CustomException(CustomExceptionType.INPUT_CONFLICT,Message.USER_IS_EXISTED);
            } else if (service.existPhone(telephone)) {
                throw new CustomException(CustomExceptionType.INPUT_CONFLICT,Message.PHONE_IS_EXISTED);
            } else {
                throw new CustomException(CustomExceptionType.UNKNOWN_ERROR,Message.UNKNOWN_ERROR);
            }
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public Response login(@NotEmpty(message = Message.USER_INFO_NULL) @RequestParam String userInfo,
                          @NotEmpty(message = Message.PASSWORD_NULL) @RequestParam String password,
                          HttpServletResponse response, HttpServletRequest request) {
        String encodePassword = base64Util.encode(password);
        service.loginUser(userInfo, encodePassword);
        String accountId = service.selectIdByInfo(userInfo);
        response.addCookie(new Cookie(HttpInfo.USER_ID_COOKIE, accountId));
        //------------------------测试需要-------------------------
        User user = service.findUserById(accountId);
        request.getSession().setAttribute(HttpInfo.USER_SESSION, user);
        log.info(request.getServletContext().getRealPath(""));
        //------------------------测试需要-------------------------
        return new Response().success();
    }

    @PostMapping(value = "/updateInfo", produces = "application/json")
    public Response updateInfo(HttpServletRequest request,
                               @NotEmpty(message = Message.USERNAME_NULL)
                               @RequestParam String username,
                               @RequestParam String telephone, @RequestParam String school) {

        User user = (User) request.getSession().getAttribute(HttpInfo.USER_SESSION);
        if (user == null) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        user.setUsername(username);
        user.setTelephone(telephone);
        user.setSchool(school);
        user.setGmtModified(System.currentTimeMillis());
        service.updateInfo(user);
        request.getSession().setAttribute(HttpInfo.USER_SESSION, service.selectUserByPrimaryKey(user.getId()));
        return new Response().success();
    }

    @ResponseBody
    @PostMapping(value = "/changePassword", produces = "application/json")
    public Response changePassword(HttpServletRequest request,
                                   @NotEmpty(message = Message.INPUT_NULL)
                                   @Size(min = 8, max = 30, message = Message.PASSWORD_LIMIT)
                                   @RequestParam String newPassword,
                                   @NotEmpty(message = Message.INPUT_NULL)
                                   @Size(min = 8, max = 30, message = Message.PASSWORD_LIMIT)
                                   @RequestParam String newPasswordAgain) {
        if (!newPassword.equals(newPasswordAgain)) {
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.PASSWORD_UNEQUAL);
        }
        User user = (User) request.getSession().getAttribute(HttpInfo.USER_SESSION);
        user.setPassword(base64Util.encode(newPassword));
        service.updateInfo(user);
        return new Response().success();
    }

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }
}
