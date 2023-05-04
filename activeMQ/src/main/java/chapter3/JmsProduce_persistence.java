package chapter3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author yangz
 * @date 2022/5/11 - 8:57
 * 持久化topic 的消息生产者
 */
public class JmsProduce_persistence {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer messageProducer = session.createProducer(topic);

        /*持久化topic*/
        messageProducer.setPriority(DeliveryMode.PERSISTENT);
        /*设置持久化topic之后再，启动连接*/
        connection.start();

        for (int i = 0; i < 3 ; i++) {
            TextMessage textMessage = session.createTextMessage("topic_msg---" + (i+1));

            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("****消息发送到MQ完成****");

    }
}
