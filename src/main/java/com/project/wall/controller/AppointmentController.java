package com.project.wall.controller;

import com.project.wall.data.*;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.AComment;
import com.project.wall.po.ACommentReply;
import com.project.wall.po.Appointment;
import com.project.wall.service.AppointmentService;
import com.project.wall.service.RedisService;
import com.project.wall.service.UserService;
import com.project.wall.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author MercerJR
 * @Data 2020/5/20 10:44
 */
@RestController
@Slf4j
@RequestMapping("/appointment")
@Validated
public class AppointmentController {

    @Autowired
    private IdUtils idUtils;

    @Autowired
    private AppointmentService service;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/publish", produces = "application/json")
    public Response publish(HttpServletRequest request, @RequestBody Appointment appointment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (appointment.getContent() == null || "".equals(appointment.getContent()) ||
                appointment.getType() == null){
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.CONTENT_OR_TYPE_EMPTY);
        }
        appointment.setAppointmentId("A" + idUtils.getPrimaryKey());
        appointment.setAccountId(accountId);
        appointment.setUsername(userService.selectUsernameById(accountId));
        appointment.setGmtCreate(System.currentTimeMillis());
        appointment.setExceed(0);
        if (System.currentTimeMillis() > appointment.getTime()) {
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR, Message.TIME_SET_ERROR);
        }
        service.publishAppointment(appointment);
        return new Response().success();
    }

    @PutMapping(value = "/update", produces = "application/json")
    public Response update(HttpServletRequest request, @RequestBody Appointment appointment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        if (!accountId.equals(appointment.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        if (appointment.getContent() == null || "".equals(appointment.getContent())){
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.CONTENT_OR_TYPE_EMPTY);
        }
        appointment.setUsername(userService.selectUsernameById(accountId));
        service.updateAppointment(appointment);
        return new Response().success();
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public Response delete(HttpServletRequest request, @RequestBody Appointment appointment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(appointment.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        service.deleteAppointmentById(appointment.getAppointmentId(), accountId);
        redisService.deleteAppointmentJoinInfo(appointment,accountId);
        redisService.deleteReplyNum(appointment.getAppointmentId());
        return new Response().success();
    }

    @PostMapping(value = "/join", produces = "application/json")
    public Response join(HttpServletRequest request, @RequestBody Appointment appointment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        return new Response().success(redisService.joinAppointment(appointment.getAppointmentId(),
                accountId));
    }

    @GetMapping(value = "/show", produces = "application/json")
    public Response show(HttpServletRequest request,@RequestParam("type") Integer type) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        List appointmentList = service.getAppointmentList(type);
        Set joinSet = redisService.getJoinSetByUser(accountId);
        redisService.getAppointmentReplyCount(appointmentList);
        redisService.getAppointmentJoinCount(appointmentList);
        AppointmentResponse appointmentResponse = new AppointmentResponse();
        appointmentResponse.setAppointmentList(appointmentList);
        appointmentResponse.setJoinSet(joinSet);
        return new Response().success(appointmentResponse);
    }

    @GetMapping(value = "/showOne",produces = "application/json")
    public Response showOne(HttpServletRequest request,
                            @NotNull @RequestParam("appointmentId") String appointmentId){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        Appointment appointment = service.getAppointmentById(appointmentId);
        List<CommentResponse<AComment,ACommentReply>> commentResponseList =
                service.getCommentListInAppointment(appointmentId);
        Integer joinCount = redisService.getAppointmentJoinCount(appointmentId);
        boolean join = redisService.isUserJoin(appointmentId,accountId);
        OneAppointmentResponse appointmentResponse = new OneAppointmentResponse(
                appointment,commentResponseList,joinCount,join);
        return new Response().success(appointmentResponse);
    }

    @PostMapping(value = "/commentPublish", produces = "application/json")
    public Response commentPublish(HttpServletRequest request,
                                   @RequestBody AComment comment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION,Message.NOT_LOGIN);
        }
        comment.setCommentId("AC" + idUtils.getPrimaryKey());
        comment.setAccountId(accountId);
        comment.setUsername(userService.selectUsernameById(accountId));
        comment.setIcon(userService.selectIconById(accountId));
        comment.setGmtCreate(System.currentTimeMillis());
        service.publishComment(comment);
        redisService.increaseReplyNum(comment.getAppointmentId());
        return new Response().success();
    }

    @DeleteMapping(value = "/commentDelete", produces = "application/json")
    public Response commentDelete(HttpServletRequest request,@RequestBody AComment comment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(comment.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        service.deleteComment(comment.getCommentId(), accountId);
        service.deleteReplyInComment(comment.getCommentId());
        redisService.decreaseReplyNum(comment.getAppointmentId(),
                service.deleteReplyInComment(comment.getCommentId()) + 1);
        return new Response().success();
    }

    @PostMapping(value = "/commentReplyPublish", produces = "application/json")
    public Response commentReplyPublish(HttpServletRequest request,
                                        @RequestBody ACommentReply reply) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        reply.setReplyId("ACR" + idUtils.getPrimaryKey());
        reply.setAccountId(accountId);
        reply.setUsername(userService.selectUsernameById(accountId));
        reply.setReplyUsername(userService.selectUsernameById(accountId));
        reply.setGmtCreate(System.currentTimeMillis());
        service.publishReply(reply);
        redisService.increaseReplyNum(service.getAppointmentIdByComment(reply.getCommentId()));
        return new Response().success();
    }

    @DeleteMapping(value = "/commentReplyDelete", produces = "application/json")
    public Response commentReplyDelete(HttpServletRequest request,
                                       @RequestBody ACommentReply reply) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(reply.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        service.deleteReply(reply.getReplyId(),accountId);
        redisService.decreaseReplyNum(service.getAppointmentIdByComment(reply.getCommentId()),1);
        return new Response().success();
    }
}
