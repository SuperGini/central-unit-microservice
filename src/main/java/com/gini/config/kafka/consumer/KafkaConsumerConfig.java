package com.gini.config.kafka.consumer;

import com.gini.error.handler.KafkaConsumerErrorHandler;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;


    @Bean
    public ConsumerFactory<Object, Object> consumerFactory(){
        var properties = kafkaProperties.buildConsumerProperties();
        //cand isi face refresh ---> rebalance
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean(value = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer
    ){

        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory());
        factory.setCommonErrorHandler(new KafkaConsumerErrorHandler());
        factory.setRetryTemplate(createRetryTemplate());
        return factory;
    }

    private RetryTemplate createRetryTemplate(){
        var retryTemplate = new RetryTemplate();
        //face retry de maxim 4 ori
        var retryPolicy = new SimpleRetryPolicy(4);

        retryTemplate.setRetryPolicy(retryPolicy);

        var backOffPolicy = new FixedBackOffPolicy();
        //va face retry la fiecare 60 secunde
        backOffPolicy.setBackOffPeriod(60000);

        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;

    }


}
