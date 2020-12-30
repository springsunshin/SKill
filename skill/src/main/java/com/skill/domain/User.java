package com.skill.domain;

/**
 * 用户
 */

public class User {
    private Integer id;
    private String phone;
    private String password;


    public User() {}

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
