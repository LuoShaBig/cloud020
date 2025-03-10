package com.atguigu.springcloud.service;
import com.atguigu.springcloud.entity.Payment;
import com.atguigu.springcloud.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PaymentService {
    @Resource
    private PaymentMapper paymentMapper;
    @Transactional(rollbackFor = Exception.class)
    public Integer create(Payment payment){
        return paymentMapper.create(payment);
    }
    public List<Payment> getPaymentById(String id){
        return paymentMapper.getPaymentById(id);
    }
}
