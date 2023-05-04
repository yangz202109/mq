# JMS规范
##消息头
>JMS的消息头有哪些属性：
>JMSDestination：消息目的地
>JMSDeliveryMode：消息持久化模式
>JMSExpiration：消息过期时间
>JMSPriority：消息的优先级
>JMSMessageID：消息的唯一标识符。后面我们会介绍如何解决幂等性。
>说明：	消息的生产者可以set这些属性，消息的消费者可以get这些属性。
>这些属性在send方法里面也可以设置。

##消息体
###封装具体的消息数据
###5种消息格式
>TxtMessage : 普通字符串消息，包含一个String
>MapMessage : 一个Map类型的消息，key为Strng类型，而值为Java基本类型
>BytesMessage :  二进制数组消息，包含一个byte[]
>StreamMessage :  Java数据流消息，用标准流操作来顺序填充和读取
>ObjectMessage : 对象消息，包含一个可序列化的Java对象

##消息属性

