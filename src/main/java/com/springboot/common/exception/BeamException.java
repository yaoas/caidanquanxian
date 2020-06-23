package com.springboot.common.exception;

import com.springboot.common.enumeration.RetEnum;

/**
 * 封装guns的异常
 *
 * @author fengshuonan
 * @Date 2017/12/28 下午10:32
 */
public class BeamException extends RuntimeException {

    private Integer code;

    private String message;

    public BeamException(RetEnum retEnum) {
        this.code = retEnum.getRet();
        this.message = retEnum.getMsg();
    }

    public BeamException(String msg) {
        super(msg);
        this.message = msg;
    }

    public BeamException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public BeamException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
