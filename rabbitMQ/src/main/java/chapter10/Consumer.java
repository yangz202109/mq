package chapter10;

import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/31 - 14:06
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        channel.basicConsume("ttt", true
                , (consumerTag, delivery) ->
                        System.out.println("接收到的消息 ：" + new String(delivery.getBody()))
                , s ->
                        System.out.println("消息消费被中断")
        );
    }
}
