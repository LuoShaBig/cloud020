package com.atguigu.springcloud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用结果类，使用泛型来存储不同类型的数据。
 * 例如，当存储 Payment 类型的数据时，可作为 Payment 结果对象；
 * 当存储 Order 类型的数据时，可作为 Order 结果对象。
 * @param <T> 存储的数据类型
 *
 * 注意事项：
 *      使用 Lombok 注解需要在项目中添加 Lombok 依赖，并且开发环境（如 IDE）需要安装 Lombok 插件才能正常识别和处理这些注解。
 *      当手动定义了与 Lombok 自动生成的方法冲突的方法时，手动定义的方法会覆盖 Lombok 生成的方法。例如，如果手动定义了 toString 方法，Lombok 就不会再为该类生成 toString 方法。
 *      @NoArgsConstructor 注解会让 Lombok 为类生成一个无参构造方法
 */
@Data
//
@NoArgsConstructor
public class CommonResult<T> {  //泛型：如果装的payment 返回payment,装的order 返回order

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message){
        this(code,message,null);
    }

    public CommonResult(Integer code, String message, Object o) {
        this.code = code;
        this.message = message;
        this.data = (T) o;
    }
}
