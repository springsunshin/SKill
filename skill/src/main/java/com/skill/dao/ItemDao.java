package com.skill.dao;

import com.skill.domain.Item;

import java.util.List;

public interface ItemDao {

    public List<Item> selectAll();

    public Item selectOne(Integer id);

    public void updateStock(Integer id);

    public void restoreStock(Integer id);
}
