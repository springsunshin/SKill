package com.skill.dao;

import com.skill.domain.User;

public interface UserDao {


    public void insert(User user);

    public User selectOne(String phone);
}
