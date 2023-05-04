package chapter10;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/31 - 14:06
 * 优先队列
 */
public class Produce {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        /*设置该队列为优先队列,最大优先数*/
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-max-priority",10);
        channel.queueDeclare("ttt",false,false,false,arguments);

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
        for (int i = 1; i <= 10; i++) {
            String message = "这是一个消息"+i;
            if (i == 5){
                channel.basicPublish("", "ttt", properties, message.getBytes(StandardCharsets.UTF_8));
            }else {
                channel.basicPublish("", "ttt", null, message.getBytes(StandardCharsets.UTF_8));
            }

        }

        System.out.println("生产者消息发送完毕!!");
    }
}
