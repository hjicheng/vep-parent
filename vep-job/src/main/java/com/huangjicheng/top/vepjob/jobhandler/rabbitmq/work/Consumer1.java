package com.huangjicheng.top.vepjob.jobhandler.rabbitmq.work;

import com.huangjicheng.top.vepjob.jobhandler.rabbitmq.Util;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) {
        try {
            // 获取连接对象
            Connection connection = Util.getConnection();

            // 获取通道
            Channel channel = connection.createChannel();
            // 通道绑定消息队列
            // 参数1：队列名称【会自动创建】
            // 参数2：定义队列是否持久化 true 持久化，false 不持久化，重启后删处
            // 参数3：是否独占队列
            // 参数4：是否在队列完成后，自动删除 true不删除 true
            // 参数5：额外参数
            channel.queueDeclare("work-queue", true, false, false, null);

            // 一次只能消费一条
            channel.basicQos(1);
            // 发布消息
            // 参数1：队列名称
            // 参数2：开启消息确认机制 true 消费者自动确认被消费了，拿到消息就告诉，不管业务有没有处理完 false 不会自动确认
            // 参数3：回调接口
            channel.basicConsume("work-queue", false, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    System.out.println("消费者1—————new String()" + new String(body));

                    // 参数1 确认队列中哪个具体消息 参数2 是否开启多个消息同时确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
