package com.gini.service.kafka.consumer;

import com.gini.avro.data.PartPriceUpdate;
import com.gini.avro.data.PartPriceUpdateWithCurrency;
import com.gini.convertor.Convertor;
import com.gini.service.kafka.producer.UpdatePartProducer;
import com.gini.service.services.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdatePriceConsumerImpl {

    private final PartService partService;
    private final UpdatePartProducer producer;

    @KafkaListener(topics = "topic_update.price") // warehouse -> kafka -> central-unit
    public String getMessageFromKafka(ConsumerRecord<String, PartPriceUpdate> message){
        log.info("receiving message form kafka topic: {} with KEY--->: {} and, message-->: {}", message.topic(), message.key(), message.value());

        var part = partService.findPartByPartNumber(message.value().getPartNumber());
        PartPriceUpdateWithCurrency partUpdate = Convertor.convertToKafkaMessage(part);
        producer.sendMessageToKafka(partUpdate);

        return message.key();
    }
}

