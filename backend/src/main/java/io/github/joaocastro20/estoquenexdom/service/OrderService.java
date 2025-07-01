package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.record.OrderRecord;
import lombok.NonNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final KafkaTemplate<String, OrderRecord> kafkaTemplateOrder;

    public OrderService(KafkaTemplate<String, OrderRecord> kafkaTemplateOrder) {
        this.kafkaTemplateOrder = kafkaTemplateOrder;
    }

    public void sendMessageOrder(@NonNull OrderRecord order) {
        kafkaTemplateOrder.send("empty-stock",1, "Teste", order);
    }
}
