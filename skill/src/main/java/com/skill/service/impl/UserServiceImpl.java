package com.skill.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.skill.dao.UserDao;
import com.skill.domain.User;
import com.skill.dto.UserThreadLocal;
import com.skill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    UserThreadLocal userThreadLocal;


    @Override
    public void regist(String phone, String password) {
        User user=new User();
        user.setPhone(phone);
        user.setPassword(DigestUtil.md5Hex(password));
        userDao.insert(user);
    }

    @Override
    public String login(String phone, String password) {
        User user=userDao.selectOne(phone);
        if (user!=null&&user.getPassword().equals(DigestUtil.md5Hex(password))){

            ThreadLocal threadLocal=userThreadLocal.getUserThreadLocal(phone);
            threadLocal.set(user);
            return "登录成功";
        }else {
            return "用户名或密码错误";
        }
    }
}
