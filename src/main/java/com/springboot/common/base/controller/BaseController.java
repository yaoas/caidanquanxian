package com.springboot.common.base.controller;


import com.springboot.common.base.entity.InfoEnum;
import com.springboot.common.base.warpper.BaseControllerWarpper;
import com.springboot.common.support.HttpKit;
import com.springboot.common.utils.R;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseController {




    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }



    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }

    public R savePic(MultipartFile file) {
        String path = InfoEnum.FILESAVEPATH.getDesc();
        DateFormat format = new SimpleDateFormat("MMddHHmmss");
        String dateString = format.format(new Date());
        //获取原文件的文件名
        String fileFileName = file.getOriginalFilename();
        //原文件的类型
        String[] fileStrings = StringUtils.split(fileFileName, "\\.");
        fileFileName = fileStrings[0] + dateString + "." + fileStrings[fileStrings.length - 1];
        File targetFile = new File(path, fileFileName);
        try {
            file.transferTo(targetFile);
            //获取图片大小  如果大于500  进行压缩
            long size = file.getSize();
            if (size > 2048000L) {
                Thumbnails.of(targetFile).size(700, 700).outputQuality(0.7f).toFile(targetFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
        String absolutePath = targetFile.getAbsolutePath();
        String x = InfoEnum.SPLIT.getDesc();
        String s = "/file/" + absolutePath.split(x)[1];
        Map map = new HashMap<>();
        map.put("newPath", s);
        map.put("oldPath", absolutePath);
        return R.ok(map);
    }


}
