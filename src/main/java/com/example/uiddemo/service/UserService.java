package com.example.uiddemo.service;

import com.example.uiddemo.entity.User;
import com.example.uiddemo.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author ：ccui
 * @date ：Created in 2020/10/18 下午2:38
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class UserService {

    public User selectUser(String userID) {
        return new UserMapper().getUserByID(userID);
    }

}
