package chapter5;

import com.rabbitmq.client.Channel;
import utils.RabbitMQUtil;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author yangz
 * @date 2022/5/20 - 15:28
 * 发布确认
 * : 生产者发送的信息到达队列后,会通知生产者
 */
public class ConfirmMessage {

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        //ConfirmMessage.publishMessageIndividually();
        //ConfirmMessage.publishMessageBatch();
    }

    /*单个确认
     * 生产者发一条消息，就等待确认,才会发下一条
     * */
    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtil.getChannel();

        String queueName = UUID.randomUUID().toString().substring(0, 4);
        channel.queueDeclare(queueName, false, false, false, null);

        /*开启发布确认*/
        channel.confirmSelect();

        for (int i = 0; i < 10; i++) {
            String msg = i + " ";
            channel.basicPublish("", queueName, null, msg.getBytes());

            /*等待确认*/
            boolean b = channel.waitForConfirms();
            if (b) {
                System.out.println("生产者收到确认-->消息发送成功");
            }
        }

    }

    /*批量发布确认*/
    public static void publishMessageBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtil.getChannel();

        String queueName = UUID.randomUUID().toString().substring(0, 4);
        channel.queueDeclare(queueName, false, false, false, null);

        /*开启发布确认*/
        channel.confirmSelect();

        for (int i = 0; i < 10; i++) {
            String msg = i + " ";
            channel.basicPublish("", queueName, null, msg.getBytes());

        }

        /*等待确认*/
        boolean b = channel.waitForConfirms();
        if (b) {
            System.out.println("生产者收到确认-->消息发送成功");
        }

    }

    /*异步确认*/
    public static void publishMessageAsync() throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        String queueName = UUID.randomUUID().toString().substring(0, 4);
        channel.queueDeclare(queueName, false, false, false, null);

        /*开启发布确认*/
        channel.confirmSelect();

        /*
         *线程安全且有序的一个哈希表
         * */
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();


        /*准备监听器
         * 参数1: 消息确认成功 回调函数
         * 参数2：消息确认失败 回调函数
         * */
        channel.addConfirmListener(
                (l, b) -> {
                    if (b) {
                        /*2.删除已经确认的消息,剩下的就是未确认的消息*/
                        ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(l);
                        confirmed.clear();
                    }
                    System.out.println("确认的消息 ：" + l);
                }
                , (l, b) -> System.out.println("未确认的消息 ：" + l)
        );

        for (int i = 0; i < 10; i++) {
            String msg = i + " ";
            channel.basicPublish("", queueName, null, msg.getBytes());
            /*1.记录下所有要发送的消息*/
            outstandingConfirms.put(channel.getNextPublishSeqNo(), msg);
        }


    }
}
