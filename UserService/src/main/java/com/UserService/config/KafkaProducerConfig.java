package com.UserService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public NewTopic createTopic(){
        return new NewTopic("address",3,(short) 1);
    }
    @Bean
    public NewTopic createTopic1(){

        return new NewTopic("addressList",3,(short) 1);
    }
    @Bean
    public NewTopic createTopic2(){

        return new NewTopic("userId",1,(short) 1);
    }
    @Bean
    public NewTopic createTopic3(){
        return new NewTopic("allAddressList",3,(short) 1);
    }
    @Bean
    public NewTopic createTopic4(){
        return new NewTopic("findAllAddress",3,(short) 1);
    }

}
