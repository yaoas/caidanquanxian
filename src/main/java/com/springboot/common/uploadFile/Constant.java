package com.springboot.common.uploadFile;

public class Constant {
    // 默认日期格式
    public static final String SYS_DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    //ftp地址
    public static final String FTP_ADDRESS = "127.0.0.1";
    //ftp端口
    public static final int FTP_PORT = 21;
    //ftp用户名
    public static final String FTP_USERNAME = "ftp";
    //ftp密码
    public static final String FTP_PASSWORD = "123";
    // FTP状态码
    public final static int FTP_REPLY_SUCCESS = 230;
    // FTP存放根路径
    public final static String FTP_PATH = "/webapps/shunping/upload";
    // 自定义文件上传到服务器路径
    public final static String CUST_PATH = "/opt/test/image/";
    // 自定义文件服务器ip
    public final static String CUST_IP_PATH = "http://39.97.237.246/image/";
    // 自定义文件上传到项目某路径
    public final static String CUST_LOCALHOST_PATH = "\\src\\main\\resources\\images\\";
    //fastdfs的https地址
    public final static String FASTDFS_PATH = "https://fdfs.baodingjl.com/";



    //小程序获取界面接口
    public static  final String XIAO_IP = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";
    //获取小程序token接口
    public static  final String GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    //小程序appid
    public static  final String APPID = "wx5e671b1a3fd259a1";
    //小程序密匙
    public static  final String APPSECRET = "93a948dd3014c5d124db69c913f626f8";
    //小程序界面测试
    //public static  final String MODEVIEW = "pages/index/index?id=";
    //生产
    public static  final String MODEVIEW = "pages/logs/logs?type=1&id=";
}
