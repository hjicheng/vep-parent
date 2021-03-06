package com.huangjicheng.top.vepjob.jobhandler.rabbitmq.routeDirect;

import com.huangjicheng.top.vepjob.jobhandler.rabbitmq.Util;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * 路由模式：一个生产者，交换机，多个消费者
 */
public class EmsRouteModel {


    // 生产消息
    public static void sendMsg() throws Exception {
        // 创建连接
        Connection connection = Util.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 参数1 交换机名称 参数2 交换机类型 direct 路由模式
        channel.exchangeDeclare("logs_direct", "direct");

        String routeKey = "warning";
        // 发布消息
        // 参数1：交换机名称
        // 参数2：队列名称 不需要
        // 参数3：额外参数 MessageProperties.PERSISTENT_TEXT_PLAIN 持久化
        // 参数4：内容
        channel.basicPublish("logs_direct", routeKey, MessageProperties.PERSISTENT_TEXT_PLAIN, (routeKey + "_logs_direct").getBytes());

        Util.closeConnection(channel, connection);

    }

    public static void main(String[] args) throws Exception {
        sendMsg();
    }
}
