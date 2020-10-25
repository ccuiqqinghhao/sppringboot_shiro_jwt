package com.example.uiddemo.utils;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/25 上午9:25
 * @description：
 * @modified By：
 * @version: $
 */
public class CustomizedToken extends UsernamePasswordToken {

    /**
     * 登录类型
     */
    public String loginType;

    public CustomizedToken(final String username, final String password, final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    @Override
    public String toString(){
        return "loginType="+ loginType +",username=" + super.getUsername()+",password="+ String.valueOf(super.getPassword());
    }
}
