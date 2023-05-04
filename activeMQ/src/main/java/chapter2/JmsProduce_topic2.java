package chapter2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author yangz
 * @date 2022/5/11 - 9:21
 * 设置消息体
 */
public class JmsProduce_topic2 {
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
            /* 发送TextMessage消息体*/
            TextMessage textMessage = session.createTextMessage("topic_msg---" + i);
            messageProducer.send(textMessage);

            /*发送MapMessage  消息体。set方法: 添加，get方式：获取*/
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("name","王五"+i);
            mapMessage.setInt("age",19);
            messageProducer.send(mapMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("  **** 消息发送到MQ完成 ****");

    }
}
