package chapter7;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.nio.charset.StandardCharsets;

/*Direct模式 生产者代码*/
public class Provider {

    private static final String EXCHANGE_NAME = "DirectExchange";          //交换机名称

    /*routing key*/
    private static final String VipKey = "Vip";                 //普通VIP
    private static final String NormalKey = "Normal";           //普通用户
    private static final String SuperVipKey = "SuperVip";       //超级VIP

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtil.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);            //声明交换机

        /*模拟不同会员等级接收到的不同消息内容*/
        for (int i = 0; i < 9; i++) {
            String message = "当前是待发送的消息,序号: " + i;
            if (i % 3 == 0) {
                /*将消息发送给指定交换机的指定routingKey*/
                channel.basicPublish(EXCHANGE_NAME, SuperVipKey, null, message.getBytes(StandardCharsets.UTF_8));  //发送超级VIP消息
            }
            if (i % 3 == 1) {
                channel.basicPublish(EXCHANGE_NAME, VipKey, null, message.getBytes(StandardCharsets.UTF_8));      //发送VIP消息
            }
            if (i % 3 == 2) {
                channel.basicPublish(EXCHANGE_NAME, NormalKey, null, message.getBytes(StandardCharsets.UTF_8));   //发送通用消息
            }
        }
        System.out.println("消息-发送-完毕");
    }


}