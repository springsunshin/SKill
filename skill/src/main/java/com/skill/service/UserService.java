package com.skill.service;

public interface UserService {

    public void regist(String phone,String password);

    public String login(String phone,String password);
}
