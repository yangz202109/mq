package chapter6;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yangz
 * @date 2022/5/11 - 17:09
 * spring整合的消息消费者
 */
@Service
public class SpringMQ_Consumer {
    private final JmsTemplate jmsTemplate;

    public SpringMQ_Consumer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Consumer consumer = context.getBean(SpringMQ_Consumer.class);

        String retValue =(String)consumer.jmsTemplate.receiveAndConvert();

        System.out.println("*****消费者收到的消息 :" + retValue);
    }
}
