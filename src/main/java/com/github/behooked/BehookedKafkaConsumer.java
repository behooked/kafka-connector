package com.github.behooked;

import java.time.Duration;
import java.util.Collections;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BehookedKafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(BehookedKafkaConsumer.class);

	private Properties properties;

	public BehookedKafkaConsumer(Properties properties) {
		this.properties = properties;
	}

	public void consume(String topicName, KafkaRecordProcessor processor) {

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
			consumer.subscribe(Collections.singleton(topicName));
			logger.info("Subscribed to topic {}", topicName);


			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
				if (records.isEmpty()) {
					logger.debug("No records consumed");
				}

				for (ConsumerRecord<String, String> record : records) {
					try {
						processor.processRecord(record);
								
					} catch (Exception e) {
						logger.error("Could not process record", e);
					}
				}
			}
		}
	}

}
