package io.github.joaocastro20.estoquenexdom.config;

import io.github.joaocastro20.estoquenexdom.record.OrderRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = KafkaProducerConfig.class)
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=localhost:9092")
class KafkaProducerConfigTest {

    @Autowired
    private KafkaTemplate<String, OrderRecord> kafkaTemplate;

    @Autowired
    private ProducerFactory<String, OrderRecord> producerFactory;

    @Test
    void deveCarregarKafkaTemplate() {
        assertNotNull(kafkaTemplate);
    }

    @Test
    void deveCarregarProducerFactory() {
        assertNotNull(producerFactory);
    }
}
