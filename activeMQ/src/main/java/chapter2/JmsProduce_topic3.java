package chapter2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author yangz
 * @date 2022/5/11 - 9:21
 * 设置消息属性
 */
public class JmsProduce_topic3 {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 1; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage("topic_msg---" + i);

            /*调用Message的set*Property()方法，就能设置消息属性。根据value的数据类型的不同，有相应的API。*/
            textMessage.setStringProperty("From", "www.123.com");
            textMessage.setByteProperty("Spec", (byte) 1);
            textMessage.setBooleanProperty("Invalide", true);
            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("  **** 消息发送到MQ完成 ****");

    }
}
