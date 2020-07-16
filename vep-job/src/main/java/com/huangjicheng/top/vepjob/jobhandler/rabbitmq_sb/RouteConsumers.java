package com.huangjicheng.top.vepjob.jobhandler.rabbitmq_sb;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component 
public class RouteConsumers {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 临时队列
                    exchange = @Exchange(name = "directs",type = "direct"),//指定交换机
                    key = {"error"}
            )
    })
    public void receivel1(String msg) {
        System.out.println(msg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 临时队列
                    exchange = @Exchange(name = "directs",type = "direct"),//指定交换机
                    key = {"info","error","debug"}
            )
    })
    public void receivel2(String msg) {
        System.out.println(msg);
    }

}
