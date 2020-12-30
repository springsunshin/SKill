package com.skill.controller;

import cn.hutool.core.util.StrUtil;
import com.skill.domain.User;
import com.skill.dto.UserThreadLocal;
import com.skill.exception.UserException;
import com.skill.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/skill")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserThreadLocal userThreadLocal;

    @RequestMapping(path = "/index")
    public String index(){
        return "login";
    }

    //用户登录
    @RequestMapping(path = "/login" ,method = RequestMethod.POST)
    public String login(String phone, String password, HttpSession session){
            String result = userService.login(phone, password);
            if (result.equals("登录成功")) {
                ThreadLocal threadLocal=userThreadLocal.getUserThreadLocal(phone);
                User user= (User) threadLocal.get();
                session.setAttribute("user", user);
                return "forward:/skill/item";
            } else {
                return "regist";
            }
    }

    //用户注册
    @RequestMapping(path = "/regist",method = RequestMethod.POST)
    public String regist(String phone,String password,String password1){
        if (StrUtil.isEmpty(phone)||phone.length()!=11){
            throw new UserException("手机号不正确");
        }
        if (StrUtil.isEmpty(password)||StrUtil.isEmpty(password1)||!password.equals(password1)){
            throw new UserException("密码不一致");
        }
        userService.regist(phone,password);
        return "login";
    }
}
