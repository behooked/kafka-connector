package com.github.behooked;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaRecordProcessor {

	public void processRecord(ConsumerRecord<String, String> record) throws Exception;
}
