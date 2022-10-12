package com.github.behooked;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingRecordProcessor implements KafkaRecordProcessor {
	
    private static final Logger logger = LoggerFactory.getLogger(LoggingRecordProcessor.class);

    @Override
    public void processRecord(ConsumerRecord<String, String> record, String topicName) throws Exception
    {
        logger.info("Consumed record {} from topic {}", record, topicName);
    }

}
