package com.github.behooked;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BehookedKafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(BehookedKafkaConsumer.class);

	private Consumer<String,String> consumer;
	private KafkaRecordProcessor processor;
	private boolean keepConsuming = true;

	public BehookedKafkaConsumer( Consumer<String, String> consumer,
			KafkaRecordProcessor processor) {
		this.consumer =consumer;
		this.processor = processor;
	}


	public void consume(final String topicName) {
		try {
			consumer.subscribe(Collections.singleton(topicName)); 
			logger.info("Subscribed to topic {}", topicName);

			while (keepConsuming) { 
				final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));  
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

		finally {
			consumer.close(); 
		} 
	}

	 public void shutdown() {
		    keepConsuming = false;
		  }
}
