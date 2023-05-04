package chapter3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author yangz
 * @date 2022/5/11 - 8:57
 * 持久化队列消息生产者
 */
public class JmsProduce {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer messageProducer = session.createProducer(queue);

        /*持久化 DeliveryMode.PERSISTENT(默认)
        *非持久化 DeliveryMode.NON_PERSISTENT
        * */
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 1; i < 4 ; i++) {
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            messageProducer.send(textMessage);
        }

        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("  **** 消息发送到MQ完成 ****");

    }
}
