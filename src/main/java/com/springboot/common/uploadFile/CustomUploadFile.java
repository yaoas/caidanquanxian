package com.springboot.common.uploadFile;


import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 自定义文件上传
 */
public class CustomUploadFile {
    /**
     * 上传到服务器某路径下
     * @param file
     * @return
     */
    public static String  uploadToServiceFile(MultipartFile file){
        if (file.isEmpty()) return "上传失败，请选择文件";
        //上传到服务器
        //需要在服务器上用nginx把imagess/映射到/opt/lvyouimages/imagess/下面
        String filePath = Constant.CUST_PATH;
        //生成唯一的文件名
        //生成文件在服务器存放的名字
        String fileSuffix =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+fileSuffix;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String returnPath = Constant.CUST_IP_PATH+fileName;
        return returnPath;
    }

    /**
     * 自定义文件上传到项目某路径下
     * @param file
     * @return
     */
    public static String  uploadToHostlocal(MultipartFile file){
        if (file.isEmpty()) return "上传失败，请选择文件";
        //上传到项目某路径下
        //public final static String CUST_LOCALHOST_PATH = "\\src\\main\\resources\\images\\";
        String c = System.getProperty("user.dir");
        String filePath = c + Constant.CUST_LOCALHOST_PATH;
        //生成唯一的文件名
        //生成文件在服务器存放的名字
        String fileSuffix =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+fileSuffix;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
    /**
     * 自定义文件下载
     * @return
     */
    public static boolean  downToHostlocal(String fileName,HttpServletResponse response)throws Exception{
        //  String c = System.getProperty("user.dir");
        // String filePath = c+"\\src\\main\\resources\\images\\";
        String path = GetResource.class.getClassLoader().getResource("images/"+fileName).getPath();
        File file = new File(path);

        if (!file.exists()) {
            return false;
        }
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return true;
    }

}
