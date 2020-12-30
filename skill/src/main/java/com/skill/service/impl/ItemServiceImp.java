package com.skill.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.skill.service.SkillOrderTask;
import com.skill.dao.ItemDao;
import com.skill.dao.ItemOrderDao;
import com.skill.dao.RedisDao;
import com.skill.domain.Item;
import com.skill.domain.ItemOrder;
import com.skill.domain.User;
import com.skill.dto.DateUtil;
import com.skill.dto.SkillUrl;
import com.skill.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Autowired
    RedisDao redisDao;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    ItemOrderDao itemOrderDao;

    @Autowired
    SkillOrderTask skillOrderTask;

    @Override
    public List<Item> getAllItems() {
        return itemDao.selectAll();
    }

    @Override
    public Item getOneItem(Integer id) {
        if (ObjectUtil.isEmpty(id)||id<=0){
            return null;
        }
        return itemDao.selectOne(id);
    }

    /**
     * 获取秒杀URL
     * @param id
     * @return
     */
    @Override
    public SkillUrl getSkillUrl(Integer id) {

        Item item= (Item) redisDao.getSkillItem(String.valueOf(id));
        if (ObjectUtil.isEmpty(item)){
            //redis缓存中没有数据
            //从数据库获取，保存到redis
            item=getOneItem(id);
            if (ObjectUtil.isEmpty(item)){
                //数据库中没有id对应的商品
                return new SkillUrl(false,id);
            }
            redisDao.set(String.valueOf(id),item);
            redisDao.set("stock_"+id,item.getNumber());
        }

        Date startTime=dateUtil.parseStringToDate(item.getStartTime());
        Date endTime=dateUtil.parseStringToDate(item.getEndTime());
        Date nowTime=new Date();

        if (nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            //活动没有开始或者已经结束
            return new SkillUrl(false,id,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5Url=skillUrlMd5(id);

        return new SkillUrl(true,md5Url,id,nowTime.getTime(),startTime.getTime(),endTime.getTime());
    }

    /**
     * MD5混淆
     */
    private static final String mixKey="FyAS78*&FH(@";

    private String skillUrlMd5(Integer id){
        String source=id+","+mixKey;
        String md5Url=DigestUtil.md5Hex(source);
        return md5Url;
    }

    /**
     * 验证秒杀MD5
     * @param skillId
     * @param md5
     * @return
     */
    public boolean verifySkillMd5(Integer skillId,String md5){
        SkillUrl skillUrl=this.getSkillUrl(skillId);
        String trueMd5=skillUrl.getMd5();
        if (md5.equals(trueMd5)){
            return true;
        }
        return false;
    }

    /**
     * 执行秒杀下单 redis减库存
     * @param user
     * @param skillId
     * @return
     */
    public boolean startSkill(User user,Integer skillId){
        //只针对同一商品五分钟内不允许再下单
        String key=user.getPhone()+"_"+skillId;
        System.out.println(key);
        Integer value= (Integer) redisDao.getSkillUser(key);
        if (ObjectUtil.isEmpty(value)) {
            redisDao.setex(key, skillId, 60 * 5);
            //减库存 Lua脚本
            String stockKey="stock_"+skillId;
            Long result=  redisDao.exeLua(stockKey);
            System.out.println(result);
            if (ObjectUtil.isEmpty(result)){
                return false;
            }
            if (result==-1){
                return false;
            }
            if (result==-2){
                return false;
            }
            return true;
        }
            return false;
    }

    /**
     * mysql减库存
     * @param user
     * @param skillId
     */
    @Transactional
    public String downOrder(User user,Integer skillId){
        itemDao.updateStock(skillId);
        String simpleUUID=IdUtil.simpleUUID();
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setItemid(skillId);
        itemOrder.setUserid(user.getId());
        //1为下单 未支付状态
        itemOrder.setState(1);
        itemOrder.setCreateTime(new Date());
        itemOrder.setOrderCode(simpleUUID);
        itemOrderDao.insertOrder(itemOrder);

        //订单保存在redis中 设置超时时间
        redisDao.setex("timeout_"+itemOrder.getOrderCode(),itemOrder,60*2);
        return itemOrder.getOrderCode();
    }

}
