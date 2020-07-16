package com.huangjicheng.top.vepjob.jobhandler.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 直连模式
 *
 */
public class EmsOne {


    // 生产消息 直连模式
    public static void sendMsg() throws Exception {
        // 创建连接
        Connection connection = Util.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();
        // 通道绑定消息队列
        // 参数1：队列名称【会自动创建】
        // 参数2：定义队列是否持久化 true 持久化，false 不持久化
        // 参数3：是否独占队列,其他的channel/connect不能使用,一般false
        // 参数4：是否在消费完成后，自动删除队列【当消费者完全断开链接】 true不删除 false
        // 参数5：额外参数
        channel.queueDeclare("hello", true, false, false, null);

        // 发布消息
        // 参数1：交换机名称
        // 参数2：队列名称
        // 参数3：额外参数 MessageProperties.PERSISTENT_TEXT_PLAIN 持久化
        // 参数4：内容
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello".getBytes());

        Util.closeConnection(channel,connection);

    }

    // 消费消息 直连模式
    public static void consumerMsg() throws Exception {
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
        channel.queueDeclare("hello", true, false, false, null);

        // 发布消息
        // 参数1：队列名称
        // 参数2：开启消息确认机制
        // 参数3：回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("new String()" + new String(body));
            }
        });


//        Util.closeConnection(channel,connection);
    }

    public static void main(String[] args) throws Exception {
        sendMsg();
//        consumerMsg();
    }
}
