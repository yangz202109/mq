package chapter6;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import javax.jms.TextMessage;

/**
 * @author yangz
 * @date 2022/5/11 - 17:09
 * spring整合的消息生产者
 */
@Service
public class SpringMQ_Produce {
    private final JmsTemplate jmsTemplate;

    public SpringMQ_Produce(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Produce produce = context.getBean(SpringMQ_Produce.class);

        produce.jmsTemplate.send(session -> {
            TextMessage textMessage = session.createTextMessage("***spring和activeMQ整合case111...");
            return textMessage;
        });

        System.out.println("*****生产者发送消息完毕！！");
    }
}
