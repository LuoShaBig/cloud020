package com.atguigu.springcloud.dto;

import org.springframework.stereotype.Component;

@Component
public class UserHolder {
    private static final ThreadLocal<LoginInfoDto> tl = new ThreadLocal<>();

    public static void saveUser(LoginInfoDto user){
        tl.set(user);
    }

    public static LoginInfoDto getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
