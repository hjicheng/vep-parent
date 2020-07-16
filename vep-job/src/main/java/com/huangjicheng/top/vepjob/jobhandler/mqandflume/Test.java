package com.huangjicheng.top.vepjob.jobhandler.mqandflume;

import com.huangjicheng.top.vepjob.jobhandler.rabbitmq.Util;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class Test {

    // 生产消息 直连模式 rabbitmq 发送消息到flume 写入hdfs
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
        channel.queueDeclare("user_log", true, false, false, null);

        // 发布消息
        // 参数1：交换机名称
        // 参数2：队列名称
        // 参数3：额外参数 MessageProperties.PERSISTENT_TEXT_PLAIN 持久化
        // 参数4：内容
        for (int i = 0; i < 10000000; i++) {
            channel.basicPublish("", "user_log", MessageProperties.PERSISTENT_TEXT_PLAIN, ("用户" + i + "登录成功").getBytes());
        }

        Util.closeConnection(channel, connection);

    }

    public static void main(String[] args) {

        try {
            sendMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
