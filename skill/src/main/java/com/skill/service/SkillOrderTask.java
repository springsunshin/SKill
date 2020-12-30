package com.skill.service;

import cn.hutool.core.util.ObjectUtil;
import com.skill.dao.ItemDao;
import com.skill.dao.ItemOrderDao;
import com.skill.dao.RedisDao;
import com.skill.domain.ItemOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SkillOrderTask {

    @Autowired
    ItemOrderDao itemOrderDao;

    @Autowired
    RedisDao redisDao;

    @Autowired
    ItemDao itemDao;

    /**
     * 定时查询订单是否在十五分钟内未支付 未支付恢复数据库内存
     * @param
     */
    @Scheduled(fixedDelay = 1000 * 1)
    @Transactional
    public void test(){
        //获取数据库中的未支付订单
        List<ItemOrder> list=itemOrderDao.getAllNotPaid();
        if (ObjectUtil.isEmpty(list) || list.size()==0){
            return;
        }
        for (ItemOrder itemOrder:list){
            Object existOrder=redisDao.getSkillOrder("timeout_"+itemOrder.getOrderCode());
            if (ObjectUtil.isEmpty(existOrder)){
                //说明订单超时 不可以再支付 mysql商品库存恢复
                itemOrderDao.updateOrderTimeout(itemOrder);
                itemDao.restoreStock(itemOrder.getItemid());
                redisDao.luaAddStock("stock_"+itemOrder.getItemid());
            }
        }
    }

}
