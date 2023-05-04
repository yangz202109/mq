package chapter6;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import utils.RabbitMQUtil;
import java.nio.charset.StandardCharsets;

/*Fanout模式(将消息发送到所有绑定该交换机的队列) 生产者案例*/
public class Provider {

    private static final String EXCHANGE_NAME = "FanoutExchange";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtil.getChannel();

        /*创建交换机
         * 参数一：交换机名称
         * 参数二：交换机类型
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);


        for (int i = 1; i <= 3; i++) {
            String message = "FanoutExchange message"+i;

            /*  交换机&队列设置（指定消息使用的交换机和队列）
             * 参数1： exchange交换机名称（简单队列无交换机）
             * 参数2： 有交换机就是路由key。没有交换机就是队列名称
             * 参数3： 传递消息的额外设置 (设置消息是否持久化）  MessageProperties.PERSISTENT_TEXT_PLAIN设置消息持久化
             * 参数4： 消息具体内容（要为 Byte类型）
             */
            channel.basicPublish(EXCHANGE_NAME, ""
                    , MessageProperties.PERSISTENT_TEXT_PLAIN
                    , message.getBytes(StandardCharsets.UTF_8));
        }

        System.out.println("消息 - 发送 - 完成");

    }


}