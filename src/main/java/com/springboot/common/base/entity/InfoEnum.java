package com.springboot.common.base.entity;

public enum InfoEnum {
    /**
     *  文件存储路径
     *  过期时间 小时为单位
     *  自取0  外卖1  桌号2
     *  后台用户1  小程序为2 System.getProperty("file.separator")+"usr"+System.getProperty("file.separator")+"local"+System.getProperty("file.separator")+"zhongguancun"+System.getProperty("file.separator")
     *
     **/
    //SPLIT("picSaveFile"+"\\\\"),FILESAVEPATH("D:\\picSaveFile"),EXPIRATION("2"),SELFACCESS("0"),TAKEOUT("1"),TABLE("2"),BACKUSER("1"),LITTLEAPPUSER("2"),LOCATION("file:D:/picSaveFile/"),AUTHLOCATION("C:\\Users\\Administrator\\Desktop\\apiclient_cert.p12");
    SPLIT(System.getProperty("file.separator")+"usr"+System.getProperty("file.separator")+"local"+System.getProperty("file.separator")+"zhongguancun"+System.getProperty("file.separator")), FILESAVEPATH(System.getProperty("file.separator")+"usr"+System.getProperty("file.separator")+"local"+System.getProperty("file.separator")+"zhongguancun"+System.getProperty("file.separator")), EXPIRATION("2"),SELFACCESS("0"),TAKEOUT("1"),TABLE("2"),BACKUSER("1"),LITTLEAPPUSER("2"),LOCATION("file:/usr/local/zhongguancun/"),AUTHLOCATION(System.getProperty("file.separator")+"usr"+System.getProperty("file.separator")+"local"+System.getProperty("file.separator")+"zhongguancun"+System.getProperty("file.separator")+"apiclient_cert.p12");

    //文字描述
    private String desc;

    InfoEnum(String desc){
        this.desc=desc;
    }

    public String getDesc(){
        return desc;
    }


}

