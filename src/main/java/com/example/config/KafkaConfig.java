package com.example.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        configs.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);

        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }
}

//    @Bean
//    public DefaultKafkaProducerFactoryCustomizer defaultKafkaProducerFactoryCustomizer(DefaultKafkaProducerFactory kafkaProducerFactory) {
//        return producerFactory -> producerFactory.updateConfigs(
//                Map.of(
//                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName(),
//                        JsonSerializer.ADD_TYPE_INFO_HEADERS, true
//                )
//        );

//        return producerFactory -> {
//            Map<String, Object> configurationProperties = new HashMap<>();
//            configurationProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getCanonicalName());
//            configurationProperties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
//            producerFactory.copyWithConfigurationOverride(configurationProperties);
//        };

