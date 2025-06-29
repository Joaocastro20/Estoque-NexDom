package io.github.joaocastro20.estoqueNexdom.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "empty-stock", groupId = "1")
    public void consumir(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
    }
}
