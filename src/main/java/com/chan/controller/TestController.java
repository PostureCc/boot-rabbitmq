package com.chan.controller;

import com.chan.domain.Order;
import com.chan.producer.RabbitSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: Chan
 * @Date: 2019/8/22 15:34
 * @Description:
 */
@Log4j2
@RestController
public class TestController {

    @Autowired
    private RabbitSender rabbitSender;

    @GetMapping("/test1")
    public void testSender1() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties);
    }

    @GetMapping("/test2")
    public void testSender2() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Order order = new Order(UUID.randomUUID().toString(), "订单名称");
            rabbitSender.sendOrder(order);
        }
        log.info("send time:{}", System.currentTimeMillis() - start);
    }
}
