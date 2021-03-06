package com.project.wall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wall.data.*;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.HotWall;
import com.project.wall.po.WComment;
import com.project.wall.po.WCommentReply;
import com.project.wall.po.Wall;
import com.project.wall.service.RedisService;
import com.project.wall.service.UserService;
import com.project.wall.service.WallService;
import com.project.wall.utils.DateFormatUtil;
import com.project.wall.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @Author MercerJR
 * @Data 2020/4/20 14:45
 */
@RestController
@Slf4j
@RequestMapping("/wall")
@Validated
public class WallController {

    @Autowired
    private WallService service;

    @Autowired
    private RedisService redisService;

    @Autowired
    private DateFormatUtil dateFormatUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private IdUtils idUtils;

    @PostMapping(value = "/publish", produces = "application/json")
    public Response publish(HttpServletRequest request,
                            @RequestBody Wall wall) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (wall.getTitle() == null || "".equals(wall.getTitle()) ||
                wall.getContent() == null || "".equals(wall.getContent())){
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.TITLE_OR_CONTENT_EMPTY);
        }
        wall.setWallId("W" + idUtils.getPrimaryKey());
        wall.setAccountId(accountId);
        wall.setUsername(userService.selectUsernameById(accountId));
        wall.setGmtCreate(dateFormatUtil.getDateByMiles(System.currentTimeMillis()));
        service.publishWall(wall);
        return new Response().success();
    }

    @PutMapping(value = "/update", produces = "application/json")
    public Response update(HttpServletRequest request, @RequestBody Wall wall) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(wall.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        if (wall.getTitle() == null || "".equals(wall.getTitle()) ||
                wall.getContent() == null || "".equals(wall.getContent())){
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.TITLE_OR_CONTENT_EMPTY);
        }
        wall.setUsername(userService.selectUsernameById(accountId));
        service.updateWall(wall);
        return new Response().success();
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public Response delete(HttpServletRequest request, @RequestBody Wall wall) throws JsonProcessingException {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(wall.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        service.deleteWallById(wall.getWallId(),accountId);
        redisService.deleteWallLikeInfo(wall,accountId);
        redisService.deleteReplyNum(wall.getWallId());
        return new Response().success();
    }

    @GetMapping(value = "/defaultShow", produces = "application/json")
    public Response defaultShow(HttpServletRequest request) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        List wallList = service.getDefaultWallList(HttpInfo.DEFAULT_WALL_NUM);
        Set likeSet = redisService.getLikeSetByUser(accountId);
        redisService.getWallReplyCount(wallList);
        redisService.getWallLikeCount(wallList);
        WallResponse wallResponse = new WallResponse();
        wallResponse.setWallList(wallList);
        wallResponse.setLikeSet(likeSet);
        wallResponse.setHotList(getHotWall());
        return new Response().success(wallResponse);
    }

    @GetMapping(value = "/selectiveShow", produces = "application/json")
    public Response selectiveShow(HttpServletRequest request,
                                  @NotNull @RequestParam("year") Integer year,
                                  @NotNull @RequestParam("month") Integer month,
                                  @NotNull @RequestParam("day") Integer day) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        Long startTime = dateFormatUtil.getDateStr(year, month, day);
        Long endTime = dateFormatUtil.getDateStr(year, month, day + 1);
        List wallList = service.getWallListByTime(startTime, endTime);
        Set likeSet = redisService.getLikeSetByUser(accountId);
        redisService.getWallReplyCount(wallList);
        redisService.getWallLikeCount(wallList);
        WallResponse wallResponse = new WallResponse();
        wallResponse.setWallList(wallList);
        wallResponse.setLikeSet(likeSet);
        return new Response().success(wallResponse);
    }

    @GetMapping(value = "/showOne",produces = "application/json")
    public Response showOne(HttpServletRequest request,
                            @NotNull @RequestParam("wallId")String wallId){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        Wall wall = service.getWallById(wallId);
        List<CommentResponse<WComment,WCommentReply>> commentResponseList =
                service.getCommentListInWall(wallId);
        redisService.getWallLikeCount(wall);
        redisService.getWallReplyCount(wall);
        boolean like = redisService.isUserLike(wallId,accountId);
        service.insertNum(wall.getLikeNum(),wall.getReplyNum(),wallId);
        OneWallResponse wallResponse = new OneWallResponse(wall,commentResponseList,like);
        return new Response().success(wallResponse);
    }

    @GetMapping(value = "/showUserInfo",produces = "application/json")
    public Response showUserInfo(@RequestParam("userId")String userId){
        return new Response().success(userService.getUserInfoById(userId));
    }

    @PostMapping(value = "/like", produces = "application/json")
    public Response like(HttpServletRequest request,
                         @RequestBody String jsonData) throws JsonProcessingException {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION,Message.NOT_LOGIN);
        }
        ObjectMapper mapper = new ObjectMapper();
        Wall wall = mapper.readValue(jsonData,Wall.class);
        return new Response().success(redisService.likeWall(wall.getWallId(),
                dateFormatUtil.getMilesByDate(wall.getGmtCreate()), accountId));
    }

    @PostMapping(value = "/commentPublish",produces = "application/json")
    public Response commentPublish(HttpServletRequest request,
                                   @RequestBody WComment comment){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION,Message.NOT_LOGIN);
        }
        comment.setCommentId("WC" + idUtils.getPrimaryKey());
        comment.setAccountId(accountId);
        comment.setUsername(userService.selectUsernameById(accountId));
        comment.setIcon(userService.selectIconById(accountId));
        comment.setGmtCreate(dateFormatUtil.getDateByMiles(System.currentTimeMillis()));
        service.publishComment(comment);
        redisService.increaseReplyNum(comment.getWallId());
        return new Response().success();
    }

    @DeleteMapping(value = "/commentDelete",produces = "application/json")
    public Response commentDelete(HttpServletRequest request, @RequestBody WComment comment) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(comment.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        service.deleteComment(comment.getCommentId(), accountId);
        redisService.decreaseReplyNum(comment.getWallId(),service.deleteReplyInComment(comment.getCommentId()) + 1);
        return new Response().success();
    }

    @PostMapping(value = "/commentReplyPublish",produces = "application/json")
    public Response commentReplyPublish(HttpServletRequest request,
                                        @RequestBody WCommentReply reply){
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION,Message.NOT_LOGIN);
        }
        reply.setReplyId("WCR" + idUtils.getPrimaryKey());
        reply.setAccountId(accountId);
        reply.setUsername(userService.selectUsernameById(accountId));
        reply.setReplyUsername(userService.selectUsernameById(reply.getReplyUserId()));
        reply.setGmtCreate(dateFormatUtil.getDateByMiles(System.currentTimeMillis()));
        service.publishReply(reply);
        redisService.increaseReplyNum(service.getWallIdByComment(reply.getCommentId()));
        return new Response().success();
    }

    @DeleteMapping(value = "/commentReplyDelete",produces = "application/json")
    public Response commentReplyDelete(HttpServletRequest request,
                                       @RequestBody WCommentReply reply) {
        String accountId = request.getHeader(HttpInfo.TOKEN_HEADER);
        if (accountId == null || "".equals(accountId)) {
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NOT_LOGIN);
        }
        if (!accountId.equals(reply.getAccountId())){
            throw new CustomException(CustomExceptionType.NOT_AUTHENTICATION, Message.NO_PERMISSION);
        }
        service.deleteReply(reply.getReplyId(),accountId);
        redisService.decreaseReplyNum(service.getWallIdByComment(reply.getCommentId()),1);
        return new Response().success();
    }

    private List getHotWall() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH) - 1;
        //测试的时候day + 1
        String keyPrefix = HttpInfo.WALL_LIKE + dateFormatUtil.getWallLikeKeyByDate(year, month, day);
        String hotKey = HttpInfo.WALL_HOT + ":" +
                dateFormatUtil.getWallLikeKeyByDate(year, month, day + 1);
        if (redisService.existKey(hotKey)) {
            List hotList = redisService.getList(hotKey);
            List<HotWall> hotWallList = new ArrayList<>();
            for (int i = 0;i < hotList.size();i++){
                hotWallList.add(new HotWall((String) hotList.get(i),HttpInfo.HOT_IMG[i]));
            }
            return hotWallList;
        }

        String yesterdayKey = HttpInfo.WALL_HOT + ":" +
                dateFormatUtil.getWallLikeKeyByDate(year, month, day);
        Map map = redisService.getDateKeys(keyPrefix);
        sortDescend(map);
        Set set = map.keySet();
        Object[] arr = set.toArray();

        if (arr.length == 0 && !redisService.existKey(yesterdayKey)){
            return null;
        }

        List hotList = new ArrayList<>();
        List<HotWall> hotWallList = new ArrayList<>();
        if (arr.length < HttpInfo.HOT_STANDARD) {
            if (!redisService.existKey(yesterdayKey)){
                for (int i = 0; i < arr.length; i++) {
                    String key = (String) arr[i];
                    String wallId = key.split(keyPrefix + ":")[1];
                    hotWallList.add(new HotWall(wallId,HttpInfo.HOT_IMG[i]));
//                    hotList.add(wallId);
                    redisService.leftInsertList(hotKey,wallId);
                }
            }else {
                int size = redisService.getListSize(yesterdayKey);
                if (size + arr.length > HttpInfo.HOT_STANDARD){
                    int popSize = size + arr.length - HttpInfo.HOT_STANDARD;
                    redisService.leftPopInList(yesterdayKey, popSize);
                }
                for (int i = 0; i < arr.length; i++) {
                    String key = (String) arr[i];
                    String wallId = key.split(keyPrefix + ":")[1];
                    redisService.leftInsertList(yesterdayKey,wallId);
                }
                redisService.moveListToAnother(yesterdayKey,hotKey);
                hotList = redisService.getList(hotKey);
                for (int i = 0;i < hotList.size();i++){
                    hotWallList.add(new HotWall((String) hotList.get(i),HttpInfo.HOT_IMG[i]));
                }
            }
        } else {
            int imgIndex = 0;
            System.out.println(arr.length - 1);
            System.out.println(arr.length - HttpInfo.HOT_STANDARD);
            for (int i = arr.length - 1; i >= arr.length - HttpInfo.HOT_STANDARD; i--) {
                String key = (String) arr[i];
                String wallId = key.split(keyPrefix + ":")[1];
                HotWall hotWall = new HotWall(wallId,HttpInfo.HOT_IMG[imgIndex++]);
//                hotList.add(wallId);
                redisService.leftInsertList(hotKey,wallId);
                hotWallList.add(hotWall);
            }
            redisService.removeKey(yesterdayKey);
        }
        return hotWallList;
    }

    private  <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });
        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }

}
