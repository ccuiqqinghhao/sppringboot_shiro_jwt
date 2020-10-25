package com.example.uiddemo.mapper;

import com.example.uiddemo.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/18 下午1:50
 * @description：
 * @modified By：
 * @version: $
 */
public class UserMapper {
    public static List<User> userList = new ArrayList<>();
    static{

        User user1 = new User();
        user1.setUserID("18916224520");
        user1.setPassword("CQHcqh123");
        user1.setRoleID("1");

        userList.add(user1);

    }

    public User getUserByID(String userID) {
        return userList.get(0);
    }
}
