package com.springboot.jwtShrio;

import com.springboot.common.RedisConfig.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.springboot.common.utils.SpringContextHolder.getBean;

/*
 * 自定义一个Filter，用来拦截所有的请求判断是否携带Token
 * isAccessAllowed()判断是否携带了有效的JwtToken
 * onAccessDenied()是没有携带JwtToken的时候进行账号密码登录，登录成功允许访问，登录失败拒绝访问
 * */
@Slf4j
public class JwtFilter extends AccessControlFilter {
    /*
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.warn("isAccessAllowed 方法被调用");
        //这里先让它始终返回false来使用onAccessDenied()方法
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        RedisUtil bean = getBean(RedisUtil.class);
        log.warn("onAccessDenied 方法被调用");
        //这个地方和前端约定，要求前端将jwtToken放在请求的Header部分
        //所以以后发起请求的时候就需要在Header中放一个Authorization，值就是对应的Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwtArrays = request.getHeader("Authorization");
        //从缓存里面获取token是否过期,如果过期直接返回false
        Object o = bean.get(jwtArrays);
        if(o==null || "".equals(o)){
            onLoginFail(servletResponse);
            return false;
        }
        String stringToken = String.valueOf(o);

        //如果没有过期，直接拿出token
        String[] s = jwtArrays.split("_");
        String jwtName =  s[0];
        String jwt = stringToken;
        String newToken = "";
        //判断token过期
        JwtUtil jwtUtil = new JwtUtil();
        Date expiration = jwtUtil.decode(jwt).getExpiration();
        boolean before = expiration.before(new Date());
        // true:过期   false:没过期
        if(before){

            //刷新token
            newToken = jwtUtil.createToken(jwtName);
            //更新缓存对应的值
            bean.set(jwtArrays,newToken,20*60);
        }else {
            newToken=stringToken;
        }

        log.info("请求的 Header 中藏有 jwtToken {}", jwt);
        JwtToken jwtToken = new JwtToken(newToken);
        /*
         * 下面就是固定写法
         * */
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
            //也就是subject.login(token)
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(servletResponse);
            //调用下面的方法向客户端返回错误信息
            return false;
        }

        return true;
        //执行方法中没有抛出异常就表示登录成功
    }

    //登录失败时默认返回 401 状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
