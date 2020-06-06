package com.project.wall.utils;

import com.project.wall.data.HttpInfo;
import com.project.wall.data.Message;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.Wall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author MercerJR
 * @Data 2020/5/20 11:30
 */
@Component
@Slf4j
public class ImgUtil {

    public String uploadImg(HttpServletRequest request, MultipartFile img) {
        String dirPath = request.getServletContext().getRealPath(HttpInfo.WALL_IMG_PATH);
        log.info(dirPath);
        File dir = new File(dirPath);
        //文件夹不存在则创建一个
        if (!dir.exists()) {
            dir.mkdir();
        }
        String originalFileName = img.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建文件对象，dir为文件夹，fileName为需要存储的文件
        File dest = new File(dir, fileName);
        //将上传的图片写到服务器上指定的文件
        try {
            img.transferTo(dest);
        } catch (IOException e) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.UPLOAD_IMG_FAIL);
        }
        String resImg = dirPath + fileName;
        return resImg;
    }
}
