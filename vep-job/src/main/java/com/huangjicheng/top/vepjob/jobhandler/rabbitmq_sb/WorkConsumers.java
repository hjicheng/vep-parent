package com.huangjicheng.top.vepjob.jobhandler.rabbitmq_sb;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component 
public class WorkConsumers {

    @RabbitListener(queuesToDeclare = @Queue("work"))  // 默认持久化 非独占 不自动删除
    public void receivel1(String msg) {
        System.out.println(msg);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))  // 默认持久化 非独占 不自动删除
    public void receivel2(String msg) {
        System.out.println(msg);
    }

}
