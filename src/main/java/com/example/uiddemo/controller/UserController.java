package com.example.uiddemo.controller;

import com.baidu.fsg.uid.UidGenerator;
import com.example.uiddemo.entity.JWTToken;
import com.example.uiddemo.entity.User;
import com.example.uiddemo.service.UserService;
import com.example.uiddemo.utils.CustomizedToken;
import com.example.uiddemo.utils.JWTUtil;
import com.example.uiddemo.vo.ResultVO;
import com.example.uiddemo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/18 下午12:55
 * @description：
 * @modified By：
 * @version: $
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UidGenerator uidGenerator;

    @GetMapping("/uid")
    public String uid(){
        long time1 = System.nanoTime();
        long uid = uidGenerator.getUID();
        long time2 = System.nanoTime();
        System.out.println(time2-time1);
        System.out.println(uidGenerator.parseUID(uid));
        System.out.println("-----------------------------");
        long timeMillis = System.currentTimeMillis();
        /*for (int i = 0; i < 10000000 ; i++) {
            uidGenerator.getUID();
        }*/
        long timeMillis2 = System.currentTimeMillis();
        System.out.println("耗时:"+(timeMillis2-timeMillis));
        return String.valueOf(uid);
    }

    @PostMapping("/login")
    public String login(@RequestParam String userID,
                          @RequestParam String password){
        Subject subject = SecurityUtils.getSubject();
        UserVO userVO = new UserVO();
        userVO.setUserID(userID);
        userVO.setPassword(password);
        User user = userService.selectUser(userVO.getUserID());
        if(!user.getPassword().equals(userVO.getPassword())) {
            return "用户名或密码错误";
        }

        JWTUtil jwtUtil = new JWTUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("userID", userID);
        String jwtToken = jwtUtil.encode(userID, 5*60*1000, chaim);
        return jwtToken;

    }

}
