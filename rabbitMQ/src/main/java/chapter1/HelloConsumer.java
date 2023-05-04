package chapter1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/17 - 11:07
 * 消息消费者
 */
public class HelloConsumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        /*1.创建连接工厂*/
        ConnectionFactory connectionFactory = new ConnectionFactory();
        /*连接的ip*/
        connectionFactory.setHost("192.168.137.128");
        /*连接登录的用户名和密码*/
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");

        /*2.创建连接*/
        Connection connection = connectionFactory.newConnection();

        /*3.获取信道*/
        Channel channel = connection.createChannel();

        /**
         * 消费者.消费消息
         * 1.消费队列的名称
         * 2.消费的自动确认机制(一获取消息就通知MQ 该消息已经被消费) true 代表自动应答 false 手动应答
         * 3.消费者成功消费的回调接口
         * 4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true
                , (consumerTag, delivery) ->
                        System.out.println("接收到的消息 ：" + new String(delivery.getBody()))
                , s ->
                        System.out.println("消息消费被中断")
        );

    }
}
