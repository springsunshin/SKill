package com.skill.dao;

import com.skill.domain.ItemOrder;

import java.util.List;

public interface ItemOrderDao {

    public void insertOrder(ItemOrder itemOrder);

    public List<ItemOrder> getAllNotPaid();

    public void updateOrderTimeout(ItemOrder itemOrder);
}
