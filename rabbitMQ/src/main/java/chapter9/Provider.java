package chapter9;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.nio.charset.StandardCharsets;

/* 死信队列TTL案例 生产者 */
public class Provider {

    private static final String EXCHANGE_NAME = "normal_exchange";              //正常交换机名称
    private static final String KEY = "zhangsan";        //普通队列 RoutingKey

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtil.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT);  //声明交换机

        /*死信队列 设置TTL消息过期时间 单位毫秒*/
     /*   AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder()
                .expiration("10000")
                .build();*/

        /*模拟消息循环发送*/
        for(int i = 1; i <= 10; i++) {
            String message = "INFO " + i;
            channel.basicPublish(EXCHANGE_NAME,KEY,null,message.getBytes(StandardCharsets.UTF_8));
        }

        System.out.println("生产者发送消息完毕");
    }


}