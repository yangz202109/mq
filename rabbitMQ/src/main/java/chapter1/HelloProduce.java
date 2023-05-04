package chapter1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/17 - 11:07
 * 消息生产者
 */
public class HelloProduce {
    //队列的名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        /*1.创建连接工厂并设置连接参数 */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        /*连接的ip*/
        connectionFactory.setHost("192.168.137.128");
        /*连接的端口*/
        connectionFactory.setPort(5672);
        /*连接登录的用户名和密码*/
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        
        /*2.创建连接*/
        Connection connection = connectionFactory.newConnection();

        /*3.获取信道 */
        Channel channel = connection.createChannel();

        /**
         * 生成一个队列
         * 1.队列名称,没有该名称的队列就创建
         * 2.队列里面的消息是否持久化(重启MQ后队列是否存在) , true开启 , false关闭 默认消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费 是否进行共享 true 可以多个消费者消费
         * 4.是否自动删除 最后一个消费者端开连接以后 该队列是否自动删除 true 自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        /*要发送的消息*/
        String msg = "hello world";

        /* 交换机&队列设置
        *  1. 交换机名称(简单队列无交换机)
        *  2. 有交换机就是路由Key . 没有交换机就是队列名称
        *  3. 传递消息的额外设置
        *  4. 消息的具体内容(需要为 Byte类型)
        * */
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("消息发送完毕");

        /*关闭连接*/
        RabbitMQUtil.close(connection,channel);

    }
}
