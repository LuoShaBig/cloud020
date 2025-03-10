package com.atguigu.springcloud.mapper;

import com.atguigu.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {
    Integer create(@Param("payment") Payment payment);

    List<Payment> getPaymentById(@Param("id") String id);
}
