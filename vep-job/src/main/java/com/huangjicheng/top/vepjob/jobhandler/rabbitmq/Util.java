package com.huangjicheng.top.vepjob.jobhandler.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Util {

    private static ConnectionFactory connectionFactory;
    // 类加载执行一次
    static {
        connectionFactory = new ConnectionFactory();
    }
    // 消费消息 直连模式
    public static Connection getConnection() {
        // 创建连接

        Connection connection = null;
        try {
        connectionFactory.setHost("192.168.1.13");
//            connectionFactory.setHost("172.20.10.13");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/ems");
            connectionFactory.setUsername("ems");
            connectionFactory.setPassword("ems");
            // 获取连接对象
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
        }

        return connection;
    }


    // 消费消息 直连模式
    public static void closeConnection(Channel channel, Connection connection) {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
