package chapter9;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/24 - 9:48
 */
public class ConsumerC2 {
    private static final String DEAD_QUEUE_NAME = "dead-queue";    //死信队列名称

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        DeliverCallback deliverCallback =(s,deliver)->{
            System.out.println("死信队列中的信息："+ new String(deliver.getBody()));
            channel.basicAck(deliver.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback callback = (s)->{
            System.out.println("死信队列中取消的信息");
        };

        channel.basicConsume(DEAD_QUEUE_NAME,false,deliverCallback,callback);

    }
}
