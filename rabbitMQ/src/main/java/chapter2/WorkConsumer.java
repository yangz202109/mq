package chapter2;

import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/18 - 10:17
 * 消费者(工作线程)
 */
public class WorkConsumer {
    public static final String QUEUE_NAME = "work";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("2号消费者");
        Channel channel = RabbitMQUtil.getChannel();

        //接送消息
        try {
            channel.basicConsume(QUEUE_NAME, true
                    , (consumerTag, delivery) ->
                            System.out.println("接收到的消息: " + new String(delivery.getBody()))
                    , s ->
                            System.out.println("消息消费被中断")
            );
        } catch (IOException e) {
            System.out.println("读取数据发送异常");
            e.printStackTrace();
        }
    }
}
