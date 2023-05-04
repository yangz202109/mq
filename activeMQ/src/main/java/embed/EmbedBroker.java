package embed;

import org.apache.activemq.broker.BrokerService;

/**
 * @author yangz
 * @date 2022/5/11 - 16:44
 */
public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        //ActiveMQ也支持在vm中通信基于嵌入的broker
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
        System.in.read(); /*activemq-all版本过高,程序会自动关闭*/

    }

}
