package chapter2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author yangz
 * @date 2022/5/11 - 9:23
 * 主题消息消费者者入门案例
 */
public class JmsConsumer_topic {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageConsumer consumer = session.createConsumer(topic);

        /*过监听的方式来消费消息，是异步非阻塞的方式消费消息*/
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println("****topic_消费者消费 ："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }else if (message != null && message instanceof  MapMessage){
                MapMessage mapMessage = (MapMessage) message;
                try {
                    System.out.println("****消费者的map消息："+mapMessage.getString("name"));
                    System.out.println("****消费者的map消息："+mapMessage.getInt("age"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
