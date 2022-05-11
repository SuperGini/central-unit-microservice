package com.gini.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.List;

@Slf4j
public class KafkaConsumerErrorHandler implements CommonErrorHandler {

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        log.error("kafka consumer error with exception: ", thrownException);
        CommonErrorHandler.super.handleOtherException(thrownException, consumer, container, batchListener);
    }

    @Override
    public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        log.error("kafka consumer error on key: {} and value: {}", record.key(), record.value(), thrownException);
        //  CommonErrorHandler.super.handleRecord(thrownException, record, consumer, container);
    }

    @Override
    public void handleRemaining(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        log.error("kafka consumer error on key: {} and value: {} ", records.stream().toList(), records.stream().toList(), thrownException);
        CommonErrorHandler.super.handleRemaining(thrownException, records, consumer, container);
    }

    @Override
    public void handleBatch(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container, Runnable invokeListener) {
        log.error("consumer error: ", thrownException);
        CommonErrorHandler.super.handleBatch(thrownException, data, consumer, container, invokeListener);
    }
}
