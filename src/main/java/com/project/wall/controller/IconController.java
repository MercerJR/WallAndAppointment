package com.project.wall.controller;

import com.project.wall.data.HttpInfo;
import com.project.wall.data.Message;
import com.project.wall.data.Response;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.Appointment;
import com.project.wall.po.Wall;
import com.project.wall.service.AppointmentService;
import com.project.wall.service.WallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author MercerJR
 * @Data 2020/4/22 11:06
 */
@RestController
@Slf4j
@RequestMapping("/icon")
public class IconController {

    @Autowired
    private WallService wallService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(value = "/uploadIcon", produces = "application/json")
    public Response uploadIcon(HttpServletRequest request,
                               @RequestParam("postId") String postId,
                               @RequestParam("icon") MultipartFile icon
                               ) throws IOException {
        if (icon.isEmpty()) {
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.ICON_EMPTY);
        }

        //保存图片的文件夹目录
        String dirPath = request.getServletContext().getRealPath(HttpInfo.ICON_PATH);
        log.info(dirPath);
        File dir = new File(dirPath);
        //文件夹不存在则创建一个
        if (!dir.exists()) {
            dir.mkdir();
        }

        String originalFileName = icon.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建文件对象，dir为文件夹，fileName为需要存储的文件
        File dest = new File(dir, fileName);
        //将上传的头像写到服务器上指定的文件
        icon.transferTo(dest);

        String postIcon = dirPath + fileName;
        String prefix = postId.substring(0,1);
        if ("W".equals(prefix)){
            Wall wall = wallService.getWallById(postId);
            String img = wall.getImg();
            String newImg = img == null || "".equals(img) ? postIcon : img + "," + postIcon;
            wall.setImg(newImg);
            wallService.updateWall(wall);
        }else if ("A".equals(prefix)){
            Appointment appointment = appointmentService.getAppointmentById(postId);
            String img = appointment.getImg();
            String newImg = img == null || "".equals(img) ? postIcon : img + "," + postIcon;
            appointment.setImg(newImg);
            appointmentService.updateAppointment(appointment);
        }
        return new Response().success();
    }
}
