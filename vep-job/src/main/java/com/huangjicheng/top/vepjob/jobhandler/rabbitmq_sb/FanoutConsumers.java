package com.huangjicheng.top.vepjob.jobhandler.rabbitmq_sb;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumers {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 临时队列
                    exchange = @Exchange(name = "logs",type = "fanout")//指定交换机
            )
    })
    public void receivel1(String msg) {
        System.out.println(msg);
    }



    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 临时队列
                    exchange = @Exchange(name = "logs",type = "fanout")//指定交换机
            )
    })
    public void receivel2(String msg) {
        System.out.println(msg);
    }

}
