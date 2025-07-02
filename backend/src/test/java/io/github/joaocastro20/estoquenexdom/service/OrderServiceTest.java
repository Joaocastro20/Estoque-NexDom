package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.record.OrderRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private KafkaTemplate<String, OrderRecord> kafkaTemplateOrder;

    @InjectMocks
    private OrderService orderService;

    @Test
    void deveEnviarMensagemParaTopicoKafka() {
        OrderRecord order = new OrderRecord(1L, "Produto Teste", "", 100.00);

        orderService.sendMessageOrder(order);

        verify(kafkaTemplateOrder).send("empty-stock", 1, "Teste", order);

    }
}
