package chapter7;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*Direct模式 消费者代码*/
public class VipConsumer {
    private static final String EXCHANGE_NAME = "DirectExchange";          //交换机名称
    private static final String VipKey = "Vip";      //普通VIP

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        /*创建临时队列*/
        String queue = channel.queueDeclare().getQueue();
        /*绑定交换机 队列名,交换机名,routingKey*/
        channel.queueBind(queue, EXCHANGE_NAME, VipKey);

        /*接受消息成功的回调函数*/
        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println("vip用户接受的消息：" + new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); //手动应答
        };

        /*消费者取消消费的回调*/
        CancelCallback callback = consumerTag -> System.out.println("vip用户取消的消息");

        channel.basicConsume(queue, false, deliverCallback, callback);

    }
}
