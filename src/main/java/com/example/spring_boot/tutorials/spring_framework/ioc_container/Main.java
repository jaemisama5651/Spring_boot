package com.example.spring_boot.tutorials.spring_framework.ioc_container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Appconfig.class);
        Message message = context.getBean(Message.class);
        System.out.println(message.getMessage());
    }
}

@Configuration
class Appconfig{
    @Bean
    public Message message(){
        return new MorningMessage();
    }
}

interface Message{
    String getMessage();
}

class MorningMessage implements Message{

    @Override
    public String getMessage() {
        return "좋은 아침입니다.";
    }
}