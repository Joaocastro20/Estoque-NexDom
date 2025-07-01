package io.github.joaocastro20.estoquenexdom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "empty-stock", groupId = "1")
    public void consumir(String mensagem) {
        String msg = "Mensagem recebida: " + mensagem;
        logger.info(msg);
    }
}
