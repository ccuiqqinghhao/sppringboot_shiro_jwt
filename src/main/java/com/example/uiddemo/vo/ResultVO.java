package com.example.uiddemo.vo;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/18 下午2:46
 * @description：
 * @modified By：
 * @version: $
 */
public class ResultVO {
    private String code;
    private String msg;
    private Object obj;

    public ResultVO(String code, String msg, Object obj){
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }
    public static ResultVO success(Object obj) {
        return new ResultVO("1","success",obj);
    }
    public static ResultVO error(Object obj) {
        return new ResultVO("2","error",obj);
    }
}
