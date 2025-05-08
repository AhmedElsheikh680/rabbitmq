    package com.spring.rabbitmq.service;

    import com.spring.rabbitmq.model.Message;
    import org.springframework.amqp.rabbit.annotation.RabbitListener;
    import org.springframework.stereotype.Component;

    @Component
    public class ConsumerListener {

        @RabbitListener(queues = {"${rabbit.direct.queue1}", "${rabbit.topic.queue1}"}, containerFactory = "rabbitListenerContainerFactory")
        public void receiveMessage(Message message) {

//            System.out.println(message.toString());
//            throw new RuntimeException();
            try {
                System.out.println("Received message: " + message.toString());
                            throw new RuntimeException();

                // Process the message here (e.g., save to database, perform business logic)
            } catch (Exception e) {
                // Log the error and handle it appropriately
                System.err.println("Error processing message: " + e.getMessage());
                throw new RuntimeException("Failed to process message", e); // Optionally rethrow with more context
            }
        }
    }
