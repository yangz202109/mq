package chapter5;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author yangz
 * @date 2022/5/11 - 9:02
 * 队列消息消费者者 签收
 */
public class JmsConsumer {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            TextMessage message = (TextMessage) consumer.receive(4000L);
            if (null != message) {
                System.out.println("****消费者消费 ：" + message.getText());
                message.acknowledge();
            } else {
                break;
            }
        }

        consumer.close();
        session.commit(); //提交事务
        session.close();
        connection.close();
    }
}