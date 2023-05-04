package chapter7;

import com.rabbitmq.client.*;
import utils.RabbitMQUtil;

/*Direct模式 消费者代码*/
public class NormalConsumer {

    private static final String EXCHANGE_NAME = "DirectExchange";          //交换机名称
    private static final String NormalKey = "Normal";           //普通用户

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtil.getChannel();

        String queue = channel.queueDeclare().getQueue();                //创建临时队列
        channel.queueBind(queue, EXCHANGE_NAME, NormalKey);                //绑定队列和交换机

        /*消费者成功消费回调逻辑*/
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("普通用户接收到的信息为:" + new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);     //手动消息应答
        };

        /*消费者取消消费回调逻辑*/
        CancelCallback cancelCallback = a -> System.out.println("普通用户进行取消消费操作!");

        channel.basicConsume(queue, false, deliverCallback, cancelCallback);

    }


}