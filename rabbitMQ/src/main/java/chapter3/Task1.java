package chapter3;

import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/18 - 10:17
 * 消费者手动应答
 * 默认消息采用的是自动应答，所以我们要想实现消息消费过程中不丢失，需要把自动应答改
 * 为手动应答,放入队列重新消费
 */
public class Task1 {
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("1号消费者-正常消费");
        Channel channel = RabbitMQUtil.getChannel();

        try {                                  //手动应答
            channel.basicConsume(QUEUE_NAME, false
                    , (consumerTag, delivery) -> {

                        System.out.println("接收到的消息: " + new String(delivery.getBody(), StandardCharsets.UTF_8));

                        /*
                         * 手动应答消息
                         *  参数1: 消息标记tag
                         *  参数2: 是否批量消费消息(true为应答改对列中所有的消息,false为只应答接受到的消息)
                         * */
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    }
                    , s ->
                            System.out.println("消息消费被中断")
            );
        } catch (IOException e) {
            System.out.println("读取数据发送异常");
            e.printStackTrace();
        }
    }
}
