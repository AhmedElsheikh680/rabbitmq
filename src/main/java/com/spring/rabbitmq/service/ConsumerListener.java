    package com.spring.rabbitmq.service;

    import com.spring.rabbitmq.model.Message;
    import org.springframework.amqp.rabbit.annotation.RabbitListener;
    import org.springframework.stereotype.Component;

    @Component
    public class ConsumerListener {

        @RabbitListener(queues = {"${rabbit.direct.queue1}", "${rabbit.topic.queue1}"}, containerFactory = "simpleRabbitListenerContainerFactory")
        public void receiveMessage(Message message) {

            System.out.println(message.toString());
            throw new RuntimeException();

        }
    }
