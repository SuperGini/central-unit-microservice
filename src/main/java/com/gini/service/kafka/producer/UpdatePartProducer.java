package com.gini.service.kafka.producer;

import com.gini.avro.data.PartPriceUpdateWithCurrency;

public interface UpdatePartProducer {
    void sendMessageToKafka(PartPriceUpdateWithCurrency part);
}
