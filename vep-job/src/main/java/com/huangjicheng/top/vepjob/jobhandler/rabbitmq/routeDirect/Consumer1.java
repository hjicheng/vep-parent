package com.huangjicheng.top.vepjob.jobhandler.rabbitmq.routeDirect;

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

            // 绑定交换机
            channel.exchangeDeclare("logs_direct","direct");

            // 生成临时队列
            String queueName = channel.queueDeclare().getQueue();

            // 绑定交换机和队列 基于路由key
            channel.queueBind(queueName,"logs_direct","error");
            channel.queueBind(queueName,"logs_direct","info");
            channel.queueBind(queueName,"logs_direct","warning");

            // 一次只能消费一条
            channel.basicQos(1);
            // 发布消息
            // 参数1：队列名称
            // 参数2：开启消息确认机制 true 消费者自动确认被消费了，拿到消息就告诉，不管业务有没有处理完 false 不会自动确认
            // 参数3：回调接口
            channel.basicConsume(queueName, false, new DefaultConsumer(channel) {

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
