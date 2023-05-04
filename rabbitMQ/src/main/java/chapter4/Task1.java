package chapter4;

import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/18 - 10:17
 * 不公平发送和预取值
 */
public class Task1 {
    public static final String QUEUE_NAME = "no_dispassion";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("1号消费者-效率高");
        Channel channel = RabbitMQUtil.getChannel();

        /*设置预取值*/
        channel.basicQos(4);

        try {
            channel.basicConsume(QUEUE_NAME, false
                    , (consumerTag, delivery) -> {

                        /*睡眠1秒*/
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("接收到的消息: " + new String(delivery.getBody()));

                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                    }
                    , s ->
                            System.out.println("消息消费被中断")
            );
        } catch (IOException e) {
            System.out.println("读取数据读取异常");
            e.printStackTrace();
        }
    }
}
