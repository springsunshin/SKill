package com.skill.service;

import com.skill.domain.Item;
import com.skill.domain.User;
import com.skill.dto.SkillUrl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemService {

    public List<Item> getAllItems();

    public Item getOneItem(Integer id);

    public SkillUrl getSkillUrl(Integer id);

    public boolean verifySkillMd5(Integer skillId,String md5);

    public boolean startSkill(User user, Integer skillId);

    public String downOrder(User user,Integer skillId);
}
