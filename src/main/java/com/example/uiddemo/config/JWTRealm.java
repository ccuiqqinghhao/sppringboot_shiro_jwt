package com.example.uiddemo.config;

import com.example.uiddemo.entity.JWTToken;

import com.example.uiddemo.service.UserService;
import com.example.uiddemo.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author ：ccui
 * @date ：Created in 2020/10/18 下午11:29
 * @description：
 * @modified By：
 * @version: $
 */
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

      /*  String userID = JWTUtil.getUserID(principalCollection.toString()); //(String) principalCollection.getPrimaryPrincipal();//
        User user = userService.selectUser(userID);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRoleID());

        //todo 设置用户权限 不确定需不需要写
       *//* Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);*//*

        return simpleAuthorizationInfo;*/
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = (String)authenticationToken.getPrincipal();
        if(jwt == null) throw  new NullPointerException("jwtToken 不允许为空");

        // 判断uu
        JWTUtil jwtUtil = new JWTUtil();
        //if(!jwtUtil.isVerify(jwt)) throw new UnknownAccountException();

        /*解密userID, 是否真实存在*/
        String userID = (String) jwtUtil.decode(jwt).get("userID");//判断数据库中username是否存在
        if (userService.selectUser(userID) == null) {
            // 用户不存在
            System.out.println("用户不存在");
        }

        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
        return new SimpleAuthenticationInfo(jwt, jwt, "JWTRealm");
    }




    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
