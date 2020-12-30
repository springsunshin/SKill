package com.skill;

import com.skill.dao.ItemDao;
import com.skill.dao.ItemOrderDao;
import com.skill.dao.RedisDao;
import com.skill.domain.Item;
import com.skill.domain.ItemOrder;
import com.skill.domain.User;
import com.skill.service.impl.ItemServiceImp;
import com.skill.service.impl.UserServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
public class Test {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    UserServiceImpl userService;

    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemServiceImp serviceImp;

    @Autowired
    ItemOrderDao itemOrderDao;

    @Autowired
    RedisDao redisDao;

    @org.junit.Test
    public void test3(){
        String value= (String) redisTemplate.opsForValue().get("name");
        System.out.println(value);
        User user=new User("125361","3123131");
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.opsForValue().set("usr",user);
        User user1= (User) redisTemplate.opsForValue().get("usr");
        System.out.println(user1.getPhone()+user1.getPassword());
        redisTemplate.opsForValue().set("sex","nvnvnvn");
//        String value= (String) redisTemplate.opsForValue().get("sex");
        System.out.println(value);
    }

    //商品查询测试
    @org.junit.Test
    public void test(){
        List<Item> list=itemDao.selectAll();
        for (Item item:list){
            System.out.println(item);
        }
    }


    //登录测试
    @org.junit.Test
    public void test1(){
        String value=userService.login("12345678889","987654");
        System.out.println(value);
    }


    @org.junit.Test
    public void test2(){
        long time=System.currentTimeMillis();
        Date date=new Date();
        System.out.println(date);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value=format.format(date);
        System.out.println(value);
        System.out.println(date.getTime());
        System.out.println(time);
    }

    @org.junit.Test
    public void test4(){
        System.out.println(serviceImp.getSkillUrl(1));
    }


    @org.junit.Test
    public void test5(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value=format.format(1608375960000L);
        System.out.println(value);
    }

    @org.junit.Test
    public void test6(){
        redisTemplate.opsForValue().set("name","meimei");
    }

    @org.junit.Test
    public void test7(){
        ItemOrder itemOrder=new ItemOrder(1,2,1,1,new Date());
        itemOrderDao.insertOrder(itemOrder);
    }

    @org.junit.Test
    public void test8(){
        redisDao.exeLua("stock_1");
    }

}
