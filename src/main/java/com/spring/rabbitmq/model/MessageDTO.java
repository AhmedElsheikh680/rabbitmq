package com.spring.rabbitmq.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {

    private String status;
    private String dateTime;

    @Override
    public String toString() {
        return "Message{" +
                "status='" + status + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
