package com.skill.dto;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserThreadLocal {

    private static HashMap<String,ThreadLocal> userThreadLocalMap=new HashMap<>();

    public ThreadLocal getUserThreadLocal(String phone){
        ThreadLocal threadLocal=userThreadLocalMap.get(phone);
        if (ObjectUtil.isEmpty(threadLocal)){
            threadLocal=new ThreadLocal();
            userThreadLocalMap.put(phone,threadLocal);
        }
        return threadLocal;
    }
}
