package com.project.wall;

import com.project.wall.data.Message;
import com.project.wall.data.WxTokenInfo;
import com.project.wall.data.WxUserToken;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.User;
import com.project.wall.po.Wall;
import com.project.wall.service.impl.RedisServiceImpl;
import com.project.wall.service.impl.UserServiceImpl;
import com.project.wall.utils.Base64Util;
import jdk.nashorn.internal.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class WallApplicationTests {
//    @Autowired
//    Base64Util base64Util;
//
//    @Autowired
//    UserServiceImpl service;
//
//    @Autowired
//    RedisServiceImpl redisService;
//
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void testEncoder(){
//        String text = "Justmyself957";
//        String res1 = base64Util.encode(text);
//        String res2 = base64Util.decode(res1);
//        System.out.println(res1+","+res2);
//    }
//
//    @Test
//    void testIsExisted(){
//        String name = "Mercer";
//        service.existUser(name);
//    }
//
////    @Test
////    void loginUser(){
////        String username = "13883735549";
////        String password = "SnVzdG15c2VsZjk1Nw==";
////        System.out.println(service.loginUser(username,password));
////    }
//
//    @Test
//    void testRedis(){
//        String key = "lalala";
//        String val = "balala";
//        redisService.insert(key,val);
//        redisService.existKey(key);
//        System.out.println(redisService.selectStrValByKey(key));
//    }
//
//    @Test
//    void redisHash(){
//        String hashKey = "hashkey";
//        String key = "key";
//        WxTokenInfo token = new WxTokenInfo("lalala","hahaha");
////        redisService.insertHash(hashKey,key,token);
//        System.out.println(redisService.existHashKey(hashKey,key));
//    }
//
//    @Test
//    void testTime(){
//        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time="2020-05-13 11:20:20";
//        String time2="2020-05-13 19:20:20";
//
//        Date date = null;
//        Date date2 = null;
//        try {
//            date = format.parse(time);
//            date2 = format.parse(time2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        //日期转时间戳（毫秒）
//        long t=date.getTime();
//        long t2 = date2.getTime();
//        System.out.print("Format To times:"+t);
//        System.out.print("Format To times:"+t2);
//
//    }
//
////    @Test
////    void redisIncrTest(){
////        System.out.println(redisService.likeWall((long) 2,"mercerjr"));
////    }
//
//    @Test
//    void dateTest(){
//        Calendar now = Calendar.getInstance();
//        System.out.println("年: " + now.get(Calendar.YEAR));
//        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
//        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
//        System.out.println(now.get(Calendar.DATE));
//    }
//
//    @Test
//    void keyPattern(){
//        System.out.println(redisTemplate.keys("*:14"));
//    }
//
//    @Test
//    void test(){
//        redisTemplate.opsForSet().add("test","null");
//        redisTemplate.opsForSet().add("test","lalala");
//
//    }
//
//    @Test
//    void testMap(){
//        Map<Long,String >map = new HashMap<>();
//        map.put((long) 5,"hello5");
//        map.put((long) 6,"hello6");
//        map.put((long) 4,"hello4");
//        map.put((long) 8,"hello8");
//        Set set = map.keySet();
//        Object[] arr = set.toArray();
//        Arrays.sort(arr);
//        for(Object key:arr){
//            System.out.println(key+": "+map.get(key));
//        }
//    }
//
//    @Test
//    void getHash(){
//        List list = redisService.getHash("user");
//        System.out.println(list);
////        redisTemplate.opsForHash().get("user",9)
//    }

//    @Test
//    void testHashMove(){
//        redisService.moveHashToAnother("another","wallHot:202006:05");
//    }

//    @Test
//    void testListMove(){
//        redisService.moveListToAnother("wallHot:202006:05","another");
//        redisService.leftPopInList("another", 3);
//    }

//    @Test
//    void testMilesToDate(){
//        Long time = Long.valueOf("1591716690000") ;//+ 5000 *1000
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        Date date = new Date();
//        date.setTime(time);
//        System.out.println(simpleDateFormat.format(date));
//    }
//
//    @Test
//    void testDateToMiles(){
//        String date = "2020-06-09 23-31-30";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        long time = 0;
//        try {
//            time = simpleDateFormat.parse(date).getTime();
//        } catch (ParseException e) {
//            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
//        }
//        System.out.println(time);
//    }
    @Test
    void test(){
        for (int i = 0;i<6;i++){
            redisTemplate.opsForList().rightPopAndLeftPush("wallHot:202006:14","newKey");
        }

    }

}
