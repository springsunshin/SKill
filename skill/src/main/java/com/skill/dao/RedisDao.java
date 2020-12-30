package com.skill.dao;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDao {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取秒杀商品
     * @param key
     * @return
     */
    public Object getSkillItem(String key){
        if (StrUtil.isEmpty(key)){
            return null;
        }
        redisTemplate.setValueSerializer(RedisSerializer.json());
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key,Object value){
        try {
            redisTemplate.setValueSerializer(RedisSerializer.json());
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 设置key的超时时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setex(String key,Object value,long time){
        try {
            if (time>0){
                redisTemplate.setValueSerializer(RedisSerializer.json());
                redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获取秒杀用户
     * @param phone
     * @return
     */
    public Object getSkillUser(String phone){
        if (StrUtil.isEmpty(phone)){
            return null;
        }
        return redisTemplate.opsForValue().get(phone);
    }

    private final static String LUA_SCRIPT;
    private final static String LUA_SCRIPT1;

    /**
     * Lua脚本初始化
     */
    static {
        StringBuilder builder=new StringBuilder();
        builder.append("if (redis.call('exists',KEYS[1])==1) then");
        builder.append("    local stock = tonumber(redis.call('get',KEYS[1]));");
        builder.append("    if (stock==-1) then");
        builder.append("        return -1");
        builder.append("    end;");
        builder.append("    if (stock>0) then");
        builder.append("        redis.call('incrby',KEYS[1],-1);");
        builder.append("        return stock - 1;");
        builder.append("    end;");
        builder.append("    return -1;");
        builder.append("end;");
        builder.append("return -2;");
        LUA_SCRIPT=builder.toString();

        StringBuilder builder1=new StringBuilder();
        builder1.append("if (redis.call('exists',KEYS[1])==1) then");
        builder1.append("    local stock = tonumber(redis.call('get',KEYS[1]));");
        builder1.append("    if (stock==-1) then");
        builder1.append("        redis.call('set',KEYS[1],1)");
        builder1.append("    end;");
        builder1.append("    if (stock==0) then");
        builder1.append("        redis.call('set',KEYS[1],1)");
        builder1.append("    end;");
        builder1.append("    if (stock>0) then");
        builder1.append("        redis.call('incrby',KEYS[1],1);");
        builder1.append("        return stock + 1;");
        builder1.append("    end;");
        builder1.append("end;");
        builder1.append("return -2;");
        LUA_SCRIPT1=builder1.toString();
    }

    /**
     * Lua脚本加库存
     * @param key
     * @return
     */
    public Long luaAddStock(String key){
        final List<String> keys=new ArrayList<>();
        keys.add(key);
        //脚本传入的ARGS参数 未使用 没有set值
        final List<String> args=new ArrayList<>();

        Long result=(Long) redisTemplate.execute(new RedisCallback() {

            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Object nativeConnection=redisConnection.getNativeConnection();

                //redis集群模式执行脚本
                if (nativeConnection instanceof JedisCluster){
                    return (Long)((JedisCluster)nativeConnection).eval(LUA_SCRIPT1,keys,args);
                }
                //redis单机模式执行脚本
                else if(nativeConnection instanceof Jedis){
                    return (Long)((Jedis)nativeConnection).eval(LUA_SCRIPT1,keys,args);
                }
                return null;
            }
        });
        return result;
    }

    /**
     * Lua脚本减库存
     * @param key
     * @return
     */
    public Long exeLua(String key){

        //脚本传入的KEYS参数
        final List<String> keys=new ArrayList<>();
        keys.add(key);
        //脚本传入的ARGS参数 未使用 没有set值
        final List<String> args=new ArrayList<>();

        Long result=(Long) redisTemplate.execute(new RedisCallback() {

            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Object nativeConnection=redisConnection.getNativeConnection();

                //redis集群模式执行脚本
                if (nativeConnection instanceof JedisCluster){
                    return (Long)((JedisCluster)nativeConnection).eval(LUA_SCRIPT,keys,args);
                }
                //redis单机模式执行脚本
                else if(nativeConnection instanceof Jedis){
                    return (Long)((Jedis)nativeConnection).eval(LUA_SCRIPT,keys,args);
                }
                return null;
            }
        });
        return result;
    }

    /**
     * 获取秒杀订单
     * @param key
     * @return
     */
    public Object getSkillOrder(String key){
        redisTemplate.setValueSerializer(RedisSerializer.json());
        if (StrUtil.isEmpty(key)){
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }
}
