package com.springboot.admin.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.springboot.admin.sys.dto.LoginForm;
import com.springboot.admin.sys.entity.User;
import com.springboot.admin.sys.service.IUserService;
import com.springboot.common.RedisConfig.RedisUtil;
import com.springboot.common.comment.CommonOkResponseEntity;
import com.springboot.common.utils.R;
import com.springboot.jwtShrio.JwtUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value="LoginController",tags={"登录接口"})
public class LoginController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (!"tom".equals(username) || !"123".equals(password)) {
            map.put("msg", "用户名密码错误");
            return ResponseEntity.ok(map);
        }
        //判断redis中是否有同一个账号登录
        //有的话 直接返回
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", username);
        String jwtToken = jwtUtil.encode(username, 5 * 60 * 1000, chaim);
        String returnData = username + "_" +jwtToken;
        redisUtil.set(returnData,jwtToken,20*60);
        map.put("msg", "登录成功");
        map.put("token", returnData);
        return R.ok(map);
    }
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    @ApiOperation(value="登陆方法")
//    public Object login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
//        return R.ok();
//    }


    /**
     * 退出
     */
    @ApiOperation(value="退出登陆方法")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Object logout() {
        return R.ok("退出成功");
    }



    @RequestMapping(value = "/phoneSec",method = RequestMethod.POST)
    @ApiOperation(value="获取用户手机号解密")
    @ApiOperationSupport(params = @DynamicParameters(name = "CreateOrderModel",properties = {
            @DynamicParameter(name = "iv",value = " e.detail.iv",example = "dsfasd545",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "encryptedData",value = "e.detail.encryptedData",example = "dsfasd545",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "openId",value = "openId",example = "dsfasd54542116345",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "sessionKey",value = "sessionKey",example = "dsfasd54542116345",required = true,dataTypeClass = String.class),
    }))
    public R phoneSec(@RequestBody Map<String,String> map1, HttpServletRequest request) throws RuntimeException, IOException, URISyntaxException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String iv = map1.get("iv");
        String encrypted = map1.get("encryptedData");
        String sessionKey = map1.get("sessionKey");
        String openId = map1.get("openId");
            if (StringUtils.isNotBlank(iv) && StringUtils.isNotBlank(encrypted)) {
                byte[] encrypData = Base64Utils.decodeFromString(encrypted);
                byte[] ivData = Base64Utils.decodeFromString(iv);
                byte[] sessionKey1 = Base64Utils.decodeFromString(sessionKey);
                AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
                //java中自带的是PKCS5Padding填充，通过添加BouncyCastle组件来支持PKCS7Padding填充。
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                SecretKeySpec keySpec = new SecretKeySpec(sessionKey1, "AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                String resultString = new String(cipher.doFinal(encrypData), "UTF-8");
                JSONObject object = JSONObject.parseObject(resultString);
                boolean flag=false;
                try {
                }catch (Exception e){
                    flag=!flag;
                }
                // 拿到手机号码
                if (flag){
                    //登录

                }
            }
            return R.ok();
    }


    /**
     * @return com.pm.background.common.utils.R
     * @author Larry
     * @date 2020/5/14 0014 19:58
     * @description 用户修改昵称和手机号
     **/
    @PostMapping(value = "/changeLittleUser")
    @ApiOperation(value = "微信用户登录后传回昵称和图片的方法", response = CommonOkResponseEntity.class,tags = {"小程序"})
    public R changeLittleUser(@RequestBody User user) throws UnsupportedEncodingException {
        user.setName(URLEncoder.encode(user.getName(), "utf-8"));//将微信昵称用utf-8编码后储存);
        boolean update = iUserService.update(user,new UpdateWrapper<User>().eq("openid",user.getOpenId()));
        if (update) {
            return R.ok("修改成功");
        } else {
            return R.fail("修改失败");
        }
    }




}
