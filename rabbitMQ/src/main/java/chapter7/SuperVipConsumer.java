package chapter7;


import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*Direct模式 消费者代码*/
public class SuperVipConsumer {
    private static final String EXCHANGE_NAME = "DirectExchange";          //交换机名称
    private static final String SuperVipKey = "SuperVip";       //超级VIP

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        /*创建队列 绑定交换机*/
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, EXCHANGE_NAME, SuperVipKey);

        /*接受消息成功的回调函数*/
        DeliverCallback callback = (s, deliver) -> {
            System.out.println("超级vip接受的消息：" + new String(deliver.getBody()));
            channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
        };

        /*消费者取消消费的回调*/
        CancelCallback cancelCallback = consumerTag -> System.out.println("创建vip用户取消的消息");

        channel.basicConsume(queue, false, callback, cancelCallback);

    }

}

