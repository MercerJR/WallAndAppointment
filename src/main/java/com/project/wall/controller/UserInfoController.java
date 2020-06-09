package com.project.wall.controller;

import com.project.wall.data.HttpInfo;
import com.project.wall.data.Message;
import com.project.wall.data.Response;
import com.project.wall.data.UserInfoResponse;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.UserInfo;
import com.project.wall.service.AppointmentService;
import com.project.wall.service.UserService;
import com.project.wall.service.WallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/9 12:31
 */

@RestController
@Slf4j
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserService service;

    @Autowired
    private WallService wallService;

    @Autowired
    private AppointmentService appointmentService;

    @PutMapping(value = "/updateInfo",produces = "application/json")
    public Response updateInfo(HttpServletRequest request, @RequestBody UserInfo userInfo){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        userInfo.setAccountId(accountId);
        service.updateUserInfo(userInfo);
        return new Response().success();
    }

    @GetMapping(value = "/showWallPublish",produces = "application/json")
    public Response showWallPublish(HttpServletRequest request){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        List wallList = wallService.getWallListByUser(accountId);
        return new Response().success(wallList);
    }

    @GetMapping(value = "/showAppointmentPublish",produces = "application/json")
    public Response showAppointmentPublish(HttpServletRequest request) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        List appointmentList = appointmentService.getAppointmentListByUser(accountId);
        return new Response().success(appointmentList);
    }

    @GetMapping(value = "/showUserInfo",produces = "application/json")
    public Response showUserInfo(HttpServletRequest request){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        UserInfo userInfo = service.getUserInfoById(accountId);
        Integer wallNum = wallService.getWallNumByUser(accountId);
        Integer appointmentNum = appointmentService.getAppointmentNumByUser(accountId);
        UserInfoResponse infoResponse = new UserInfoResponse(userInfo,wallNum,appointmentNum);
        return new Response().success(infoResponse);
    }
}
