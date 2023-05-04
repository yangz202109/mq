package chapter1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author yangz
 * @date 2022/5/11 - 9:02
 * 队列消息消费者者入门案例
 */
public class JmsConsumer {
    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.137.128:61616";
    // 目的地的名称
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        // 1 按照给定的url创建连接工厂，这个构造器采用默认的用户名密码。该类的其他构造方法可以指定用户名和密码。
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2 通过连接工厂，获得连接 connection 并启动访问。
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 3 创建会话session 。第一参数是是否开启事务， 第二参数是消息签收的方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4 创建目的地（两种 ：队列/主题）。Destination是Queue和Topic的父类
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5 创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);

        // 6 通过messageProducer 生产 3 条 消息发送到消息队列中
       /* while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                System.out.println("****消费者消费 ：" + message.getText());
            } else {
                break;
            }
        }*/
       /*过监听的方式来消费消息，是异步非阻塞的方式消费消息*/
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println("****消费者消费 ："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        /* 让主线程不要结束。因为一旦主线程结束了，其他的线程（如此处的监听消息的线程）也都会被迫结束。
         实际开发中，我们的程序会一直运行，这句代码都会省略。*/
        System.in.read();
        // 9 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}