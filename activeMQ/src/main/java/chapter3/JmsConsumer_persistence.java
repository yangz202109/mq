package chapter3;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.io.IOException;

/**
 * @author yangz
 * @date 2022/5/11 - 9:23
 * 持久化topic 的消息消费者
 */
public class JmsConsumer_persistence {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        /*设置客户端ID。向MQ服务器注册自己的名称*/
        connection.setClientID("t01");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        /*创建一个topic订阅者对象。一参是topic,二参是订阅者名称 */
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark....");

        /*之后再开启连接*/
        connection.start();
        Message message = topicSubscriber.receive();

        while (message != null){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("**收到的持久化topic："+textMessage.getText());
            message = topicSubscriber.receive();
        }

        session.close();
        connection.close();
    }
}
