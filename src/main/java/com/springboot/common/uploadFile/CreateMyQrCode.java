package com.springboot.common.uploadFile;


import com.springboot.common.CreatrQrCode.QRCodeUtil;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;

/**
 * 生成中心自定义的图片二维码
 * 暂时上传到项目根路径下面
 */
public class CreateMyQrCode {
    public static String createCode(String contents,String fileName){
        //生成二维码
        //String content= "测试二维码生成内容";
     /*   String imgPath="123.jpg";//二维码logo
        String destPath ="log";//生成二维码的地方*/
        //String imgPath="123.jpg";
        String c = System.getProperty("user.dir");
        String destPath = c + Constant.CUST_LOCALHOST_PATH;
       // System.out.println("logo图片地址imgPath=="+fileName);
        //System.out.println("存放二维码图片的地址destPath=="+destPath);
        try {
            fileName = QRCodeUtil.encode(contents, destPath+fileName, destPath, true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return fileName;
    }

    /**
     * 解析图片二维码信息
     */
    public static String deCodePicture(String name){
        //解析二维码
        String path = GetResource.class.getClassLoader().getResource("images/"+name).getPath();
        //解析图片的路径
        String result = "";
        try {
             result = QRCodeUtil.decode(path);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            return path+"不是二维码";
        }
        return result;
    }
}
