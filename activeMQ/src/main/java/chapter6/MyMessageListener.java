package chapter6;

import javax.jms.*;

/**
 * @author yangz
 * @date 2022/5/12 - 9:40
 * 自定义监听器 (在spring中实现消费者不启动,直接通过监听器完成)
 */
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if (message != null){
            if (message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消息 ==>"+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }else if (message instanceof MapMessage){
                MapMessage mapMessage = (MapMessage) message;
                try {
                    System.out.println("消息 ==> "+mapMessage.getString("s1"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
