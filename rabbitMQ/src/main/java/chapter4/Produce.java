package chapter4;

import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/18 - 10:40
 */
public class Produce {
    public static final String QUEUE_NAME = "no_dispassion";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 1; i <= 6; i++) {
            String message = "no_dispassion-消息 "+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("消息发送完毕");
    }
}
