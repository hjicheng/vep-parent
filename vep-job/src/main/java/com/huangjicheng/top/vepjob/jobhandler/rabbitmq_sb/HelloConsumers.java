package com.huangjicheng.top.vepjob.jobhandler.rabbitmq_sb;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue("hello"))  // 默认持久化 非独占 不自动删除
public class HelloConsumers {

    @RabbitHandler
    public void receivel(String msg) {
        System.out.println(msg);
    }

}
