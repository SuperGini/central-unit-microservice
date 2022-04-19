package com.gini.service.kafka.producer;

import com.gini.avro.data.PartPriceUpdateWithCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdatePartProducerImpl implements UpdatePartProducer {

    private final static String TOPIC_VALIDATION_UNIT = "topic_update.price.validation.unit";
    private final KafkaTemplate<String, PartPriceUpdateWithCurrency> kafkaTemplate;

    @Override
    public void sendMessageToKafka(PartPriceUpdateWithCurrency part){
        kafkaTemplate.send(messageToKafka(part)).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("error sending message to kafka {}, {}", ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, PartPriceUpdateWithCurrency> result) {
                log.info("Message was send to kafka topic: {} -----> with KEY: {} <-->  MESSAGE: {}",
                        result.getProducerRecord().topic(), result.getProducerRecord().key(), part);
            }
        });
    }

    private ProducerRecord<String, PartPriceUpdateWithCurrency> messageToKafka(PartPriceUpdateWithCurrency part){
        return  new ProducerRecord<>(TOPIC_VALIDATION_UNIT, part.getPartId(), part);
    };





}
