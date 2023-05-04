package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/18 - 10:12
 * 获取channel的工具类
 */
public class RabbitMQUtil {

    /*1.创建连接工厂*/
    private static  ConnectionFactory connectionFactory = null;

    public static Channel getChannel() throws IOException, TimeoutException {
        /*1.创建连接工厂*/
        connectionFactory = new ConnectionFactory();
        /*连接的ip*/
        connectionFactory.setHost("192.168.137.128");
        /*连接登录的用户名和密码*/
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");

        /*2.创建连接*/
        Connection connection = connectionFactory.newConnection();
        /*3.获取信道*/
        Channel channel = connection.createChannel();


        return channel;
    }

    public static void close(Connection connection,Channel channel) throws IOException, TimeoutException {
        if (null != channel){
            channel.close();
        }
        if (null != connection){
            connection.close();
        }
    }


}
