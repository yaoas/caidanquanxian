package com.springboot.controller;

import com.springboot.common.CreatrQrCode.QRCodeUtil;
import com.springboot.common.uploadFile.Constant;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;

public class QRTest {

    public static void main(String[] args) {
        String content= "测试二维码生成内容";
     /*   String imgPath="123.jpg";//二维码logo
        String destPath ="log";//生成二维码的地方*/
        //String imgPath="123.jpg";
        String c = System.getProperty("user.dir");
        String destPath = c + Constant.CUST_LOCALHOST_PATH;
        // System.out.println("logo图片地址imgPath=="+fileName);
        //System.out.println("存放二维码图片的地址destPath=="+destPath);
        try {
            String fileName = QRCodeUtil.encode(content, destPath+"123.jpg", destPath, true);
            System.out.println("logo图片地址imgPath=="+fileName);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //解析二维码

        String path = GetResource.class.getClassLoader().getResource("images/cf7998c3-5369-475d-976f-0d72c266c11b.jpg").getPath();
        //解析图片的路径
        try {
            String result = QRCodeUtil.decode(path);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(destPath+"不是二维码");
        }
    }
}
