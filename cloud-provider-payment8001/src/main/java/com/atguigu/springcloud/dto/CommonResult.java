package com.atguigu.springcloud.dto;

import lombok.Data;

//Json封装体CommentResult,传给前端，判断编码是否成功，成功才显示
@Data
public class CommonResult<T> {  //泛型：如果装的payment 返回payment,装的order 返回order

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code,String message){
        this(code,message,null);
    }

    public CommonResult(Integer code, String message, Object o) {
        this.code = code;
        this.message = message;
        this.data = (T) o;
    }
}
