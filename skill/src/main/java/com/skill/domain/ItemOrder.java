package com.skill.domain;

import java.util.Date;

/**
 * 秒杀订单
 */

public class ItemOrder {

    private Integer id;
    private Integer itemid;
    private Integer userid;
    // 1 下单成功 未支付
    // 2 下单成功 已支付
    // 4 超时
    private Integer state;
    private Date createTime;
    private String orderCode;

    public ItemOrder() {}

    public ItemOrder(Integer id, Integer itemid, Integer userid, Integer state, Date createTime) {
        this.id = id;
        this.itemid = itemid;
        this.userid = userid;
        this.state = state;
        this.createTime = createTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
