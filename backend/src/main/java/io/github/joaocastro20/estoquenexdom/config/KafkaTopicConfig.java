package io.github.joaocastro20.estoquenexdom.config;

import org.springframework.beans.factory.annotation.Value;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAdress;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAdress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicOrderProcessed() {
        return new NewTopic("empty-stock", 2, (short) 1);
    }

}
