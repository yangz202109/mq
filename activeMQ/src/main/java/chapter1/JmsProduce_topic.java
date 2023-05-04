package chapter1;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * @author yangz
 * @date 2022/5/11 - 9:21
 * 主题消息生产者入门案例
 */
public class JmsProduce_topic {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {

        // 1 按照给定的url创建连接工厂，这个构造器采用默认的用户名密码。该类的其他构造方法可以指定用户名和密码。
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2 通过连接工厂，获得连接 connection 并启动访问。
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 3 创建会话session 。第一参数是是否开启事务， 第二参数是消息签收的方式
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        // 4 创建目的地（两种 ：队列/主题）。Destination是Queue和Topic的父类
        Topic topic = session.createTopic(TOPIC_NAME);

        // 5 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);

        // 6 通过messageProducer 生产 3 条 消息发送到消息队列中
        for (int i = 1; i < 4 ; i++) {
            // 7  创建消息
            TextMessage textMessage = session.createTextMessage("topic_msg---" + i);
            // 8  通过messageProducer发送给mq
            messageProducer.send(textMessage);
        }

        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("  **** 消息发送到MQ完成 ****");

    }
}
