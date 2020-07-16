package com.huangjicheng.top.vepjob.jobhandler.rabbitmq.work;

import com.huangjicheng.top.vepjob.jobhandler.rabbitmq.Util;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * 工作模式：一个生产者，多个消费者
 */
public class EmsWorkModel {


    // 生产消息
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
        channel.queueDeclare("work-queue", true, false, false, null);

        // 发布消息
        // 参数1：交换机名称
        // 参数2：队列名称
        // 参数3：额外参数 MessageProperties.PERSISTENT_TEXT_PLAIN 持久化
        // 参数4：内容
        for (int i = 0; i < 100; i++) {
            channel.basicPublish("", "work-queue", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + "_work-queue").getBytes());
        }

        Util.closeConnection(channel, connection);

    }

    public static void main(String[] args) throws Exception {
        sendMsg();
    }
}
