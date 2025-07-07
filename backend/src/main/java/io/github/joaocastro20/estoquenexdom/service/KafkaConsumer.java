package io.github.joaocastro20.estoquenexdom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.joaocastro20.estoquenexdom.domain.Mensagem;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final MensagemService mensagemService;

    @KafkaListener(topics = "empty-stock", groupId = "1")
    public void consumir(String mensagem) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(mensagem);
        System.out.println(jsonNode.get("id").asInt());
        Mensagem mensagemEnviar = new Mensagem();
        mensagemEnviar.setId(jsonNode.get("id").asLong());
        mensagemEnviar.setName(jsonNode.get("name").asText());
        mensagemEnviar.setDescription(jsonNode.get("description").asText());
        mensagemEnviar.setAmount(jsonNode.get("amount").decimalValue());
        mensagemService.adicionarMensagem(mensagemEnviar);
        String msg = "Mensagem recebida: " + mensagem;
        logger.info(msg);
    }
}
