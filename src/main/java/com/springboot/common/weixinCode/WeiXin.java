package com.springboot.common.weixinCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.common.uploadFile.Constant;
import com.springboot.common.uploadFile.FtpUtils;
import com.springboot.common.uploadFile.Utils;
import com.springboot.common.weiPayMoney.WeixinUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

public class WeiXin {
    public static String saveToImgByInputStream(InputStream instreams) {
        JSONObject result = new JSONObject();
        String c = System.getProperty("user.dir");
        String filePath = c+ Constant.CUST_LOCALHOST_PATH;
        String fileName = UUID.randomUUID().toString() + ".jpg";
        if (instreams != null) {
            try {
                File file = new File(filePath, fileName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
        return fileName;
    }
    public static HttpResponse doPostOfQRCode(String url, String outStr) throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();//获取DefaultHttpClient请求
        HttpPost httpost = new HttpPost(url);//HttpPost将使用Get方式发送请求URL
        JSONObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr, "UTF-8"));//使用setEntity方法，将我们传进来的参数放入请求中
        HttpResponse response = client.execute(httpost);//使用HttpResponse接收client执行httpost的结果
        return response;
    }

    /**
     * 参数说明
     * pagesPath：小程序界面
     * tToken:获取小程序全局唯一后台接口调用凭据
     *
     * @param pagesPath
     * @return
     */
    public  static String returnChenXuCode(String pagesPath) throws Exception {
        String urls = Constant.GET_TOKEN;
        String requestMethod ="GET";
        String outputStr = "grant_type=client_credential&appid="+Constant.APPID+"&secret="+Constant.APPSECRET;
        String result = WeixinUtil.httpsRequest(urls,requestMethod,outputStr);
        Map mapTypes = JSON.parseObject(result);
        String tToken = mapTypes.get("access_token").toString();
        //String pagesPath = "pages/index/index";
        // 生成二维码下载到服务器的路径
        String QRCodePath = "";
        // 接口调用凭证（access_token）
        //String tToken = "28_FVJ2LW1KVtvCgDjaydp1ow2ydFwaV7pWMo43ZfKQANniydvfxAcVqprm04PJmeGecXldGBmvIG2wkVvkXIvMv36f9T5WJYHdy4_0s7cBd1rKgDGZpV3nx1CElurW0bhtkOS7pIbiP9dQhK8kWHMgAIAITY";
            JSONObject json = new JSONObject();
            String requstPath = Constant.XIAO_IP;
            String url = requstPath.replace("ACCESS_TOKEN", tToken);
            json.put("path", pagesPath);
            json.put("width", "180");
            // 请求微信，获取回执数据
            HttpResponse response = doPostOfQRCode(url, json.toString());
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    InputStream instreams = resEntity.getContent();
                    QRCodePath = saveToImgByInputStream(instreams);
                }
            }

        String c = System.getProperty("user.dir");
        String filePath = c + Constant.CUST_LOCALHOST_PATH+QRCodePath;
        File f = new File(filePath);
        //  boolean connect = FtpUtils.connect(Constant.FTP_PATH, Constant.FTP_ADDRESS, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
        String fileName = UUID.randomUUID().toString()+".jpg";
        String path = "";
        boolean connect = FtpUtils.connect(Constant.FTP_PATH, Constant.FTP_ADDRESS, Constant.FTP_PORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
        if(connect){
            path = FtpUtils.upload(f,fileName);
        }
        f.delete();
        return path ;
    }
}
