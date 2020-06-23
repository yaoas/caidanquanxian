package com.springboot.controller;


import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.springboot.common.qrCode.QRCodeUtilsMy;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/createQr")
public class CreateQrcodeController {
    @ApiOperation("获取二维码")
    @RequestMapping("/orderQrCode")
    public void orderQrCode(HttpServletResponse response) throws IOException {
        // 设置响应流信息
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream stream = response.getOutputStream();

        //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
        String content = "内容";
        //获取一个二维码图片
        BitMatrix bitMatrix = QRCodeUtilsMy.createCode(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
    }

}
